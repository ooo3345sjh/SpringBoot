
// 변수 선언
const btnRemove      = document.querySelector('.btnRemove');      // 게시글 삭제 버튼
const btnComplete    = document.getElementById('btnComplete');    // 댓글 작성완료 버튼
const btnCancel      = document.getElementById('btnCancel');     // 댓글 작성완료 버튼
const btnModCancel   = document.getElementById('btnModCancel');   // 댓글 수정 취소 버튼
const btnModComplete = document.getElementById('btnModComplete'); // 댓글 수정 완료 버튼
const btnReplyComplete = document.getElementById('btnReplyComplete'); // 답변 완료 버튼

const commentDiv  = document.querySelector('.commentList > div'); // 댓글 articleTag가 들어갈 div 태그
const empty       = document.querySelector('.empty');             // 댓글이 비었을 경우 출력하는 태그
const cModifyForm = document.getElementById("cModifyForm");      // 댓글 수정창


const urlParams = new URL(location.href).searchParams;
const no = urlParams.get('no');
const group = urlParams.get('group');
const cate = urlParams.get('cate');
const page = urlParams.get('page');
const searchField = urlParams.get('searchField');
const searchWord = urlParams.get('searchWord');



// 댓글을 가져오는 함수
let showComment = function(){
    ajaxAPI("comment/" + no, null, "get").then((response) => {

        if(response != null){
            if(response.comments.length != 0){
                // commentDiv.insertAdjacentHTML('beforeend', toHtml(response.comments, response.startOfToday));
                commentDiv.innerHTML = toHtml(response.comments, response.startOfToday);
                empty.style.display = "none";

                commentRemove(); // 삭제 이벤트
                commentModify(); // 수정 이벤트
                commentReply(); // 답변 이벤트
            }

            else{
                empty.style.display = "block";
                commentDiv.innerHTML = "";
            }
        }

        else {
            alert('댓글을 불러오는데 실패 했습니다.')
            empty.style.display = "block";
        }

    }).catch((errorMsg) => {
        console.log(errorMsg)
    }); // ajax end

}

// 오늘 가장 빠른 정시와 비교해서 날짜 포맷을 달리하는 함수
let convertData = function (rdate, startOfToday){
    let date = new Date(rdate);

    if(date.getTime() > startOfToday)
        return  String(date.getHours()).padStart(2, "0") + ":" + String(date.getMinutes()).padStart(2, "0");

    else
        return [date.getFullYear(), String(date.getMonth()+1).padStart(2, '0'), String(date.getDate()).padStart(2, '0')].join("-");
}

// 등록 날짜와 업데이트 날짜 비교 함수
const isUpdate = function (rdate, up_date){
    return new Date(rdate).getTime() < new Date(up_date).getTime();
}

// 댓글 리스트를 html로 변환하는 함수
let toHtml = function (comments, startOfToday){
    let tmp = "";

    comments.forEach(function (comment){
        tmp += "<article data-no='" + comment.parent;
        tmp += "' data-pcno='" + comment.pcno;
        tmp += "' data-cno='" + comment.cno + "'";
        tmp += comment.cno != comment.pcno ? "class='reply'>":'>' // 답글일때 출력
        tmp += " <span class='nick'>" + comment.nick + "</span>";
        tmp += " <span class='date'>" + convertData(comment.rdate, startOfToday);
        tmp += (isUpdate(comment.rdate, comment.up_rdate) ? ' <span class="modifyComment">&middot;수정됨</span>' : '') + "</span>";
        tmp += " <p class='content'>" + comment.comment + "</p>";

        // 로그인 유저와 댓글 작성자와 아이디가 동일시 출력
        tmp += " <div>";
        if(uid === comment.uid){
            tmp += " <a href='#' class='cBtnRemove'>삭제</a>";
            tmp += " <a href='#' class='cBtnModify'>수정</a>";
        }
        tmp += " <a href='#' class='cBtnReply'>답글</a>";
        tmp += " </div>";
        tmp += "</article>";
    });

    return tmp;
};

// textarea 자동 높이 조절 이벤트 + (enter + shift)키 이벤트
let textareaAutoSize = function (){
    let textareaList = document.querySelectorAll('textarea');
    textareaList.forEach((textarea) => {

        textarea.addEventListener('keydown', function (e){

            textarea.style.height = "auto";
            textarea.style.height = textarea.scrollHeight + "px";


            let isCmodifyForm = textarea.parentElement.getAttribute("id"); // 수정 및 답변 폼인지 아이디 값을 가져온다.
            let modityBtn = textarea.nextElementSibling.children[1]; // 수정 완료 버튼
            let replyBtn = textarea.nextElementSibling.children[2];  // 답변 완료 버튼

            // Enter 키만 입력시 작성완료가 됨
            if(e.key === 'Enter'){
                if(!e.shiftKey) {

                    // 댓글 수정, 답변 폼
                    if(isCmodifyForm == "cModifyForm")
                        modityBtn.style.display != 'none' ? modityBtn.click() : replyBtn.click();

                    // 댓글 작성 폼
                    else
                        textarea.nextElementSibling.children[1].click();
                }
            }
        }) // addEvent end
    }) // forEach end
}


/** 댓글 수정  start **/

// 댓글 수정창 보이기 이벤트
let articleTag; // 수정중에 숨겨둔 댓글
let commentModify = function (){

    const cBtnModifyList = document.querySelectorAll('.cBtnModify');   // 댓글 수정 버튼

    cBtnModifyList.forEach((cBtnModify) => {
        cBtnModify.addEventListener('click', function(e) {
            e.preventDefault();

            if(articleTag != null)  articleTag.style.display = "block"; // 수정중에 숨겨둔 댓글 보이기

            articleTag  = this.parentNode.parentNode;                   // 댓글 Article
            let cno     = articleTag.getAttribute("data-cno"); // 댓글 번호
            let nick    = articleTag.children[0].innerHTML;             // 작성자 닉네임
            let comment = articleTag.children[2].innerHTML;             // 댓글 내용
            comment     = comment.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n'); // <br> 줄바꿈 \r\n로 바꾸기

            articleTag.style.display = "none";  // 수정할 댓글 숨기기
            articleTag.after(cModifyForm);      // 수정할 댓글 밑에 댓글 수정창 삽입

            cModifyForm.setAttribute("data-cno", cno);   // 댓글 수정창에 수정할 댓글 번호 속성 추가
            cModifyForm.children[0].textContent = nick;    // 댓글 수정창에 작성자의 닉네임 삽입
            cModifyForm.children[1].value  = comment; // 댓글 수정창에 기존에 작성된 댓글 삽입
            btnModComplete.style.display = "inline-block";  // 댓글 수정 버튼 보이기
            btnReplyComplete.style.display = "none";  // 댓글 답변 버튼 숨기기
            cModifyForm.style.display = "block";      // 댓글 수정창 보이기
        });

    });

    // 댓글 수정 취소 버튼
    btnModCancel.addEventListener("click", function (e){
        e.preventDefault();

        cModifyForm.style.display = "none"; // 댓글 수정창 숨기기
        if(articleTag != null)  articleTag.style.display = "block";
    });

}

// 댓글 수정완료 버튼
btnModComplete.addEventListener("click", function (e){
    e.preventDefault();
    let cno = cModifyForm.getAttribute("data-cno"); // 댓글 번호
    let comment = cModifyForm.children[1].value.trim()

    if(comment == "") {
        alert("내용을 입력 해주세요.");
        cModifyForm.children[1].focus();
        return;
    }

    comment = comment.replace(/(\n|\r\n)/g, '<br>'); // 댓글 수정 내용

    let jsonData = {cno:cno, comment:comment, uid:uid};

    ajaxAPI("comment/" + cno, jsonData, "PATCH").then((response) => {

        if(response.result == 0)
            alert('댓글 수정에 실패했습니다.');

        else {
            articleTag.style.display = "block";
            cModifyForm.style.display = "none";
            showComment();

        }
    }).catch((errorMsg) => {
        console.log(errorMsg)
    }); // ajax end
});

/** 댓글 수정  end **/


// 댓글 삭제 이벤트
let commentRemove = function (){

    const cBtnRemoveList = document.querySelectorAll('.cBtnRemove');   // 댓글 삭제 버튼

    cBtnRemoveList.forEach((cBtnRemove) => {
        cBtnRemove.addEventListener('click', function(e) {
            e.preventDefault();
            if(!confirm("정말 삭제 하시겠습니까?")) return;

            let cno = this.parentNode.parentNode.getAttribute("data-cno"); // 댓글 번호
            let pcno = this.parentNode.parentNode.getAttribute("data-pcno"); // 부모 댓글 번호
            let no = this.parentNode.parentNode.getAttribute("data-no");  // 게시글 번호

            let jsonData = {parent:no, cno:cno, pcno:pcno, uid:uid};
            ajaxAPI("comment/" + cno, jsonData, "DELETE").then((response) => {

                if(response.result =! 0)
                    showComment();

                else
                    alert('삭제에 실패했습니다.');

            }).catch((errorMsg) => {
                console.log(errorMsg)
            }); // ajax and
        });
    }); // forEach end
}

// 댓글 답변 이벤트
let commentReply = function (){

    const cBtnReplyList = document.querySelectorAll('.cBtnReply');   // 댓글 답변 버튼

    cBtnReplyList.forEach((cBtnReply) => {
        cBtnReply.addEventListener('click', function(e) {
            e.preventDefault();

            if(uid === 'anonymousUser') {
                if(confirm('로그인 후에 댓글 작성 가능합니다.\n로그인 페이지로 이동하시겠습니까?')){
                    location.href = contextPath + "user/login";
                }
            }

            // 수정중에 숨겨둔 댓글 보이기
            if(articleTag != null)  articleTag.style.display = "block";

            // 답변 버튼을 클릭한 article tag
            articleTag = this.parentElement.parentElement;

            // 답변할 댓글의 부모 댓글 번호
            let pcno = articleTag.getAttribute("data-pcno");

            // 수정할 댓글 밑에 댓글 수정창 삽입
            articleTag.after(cModifyForm);

            // 답변 폼 초기화
            cModifyForm.setAttribute("data-pcno", pcno); // pcno 속성 부여
            cModifyForm.children[0].textContent = nick; // 답변 작성자 닉네임 삽입
            cModifyForm.children[1].value = "";         // 내용 입력 값 초기화
            btnModComplete.style.display = "none";      // 댓글 답변 버튼 숨기기
            btnReplyComplete.style.display = "inline-block";  // 댓글 수정 버튼 보이기
            cModifyForm.style.display = "block";        // 댓글 수정창 보이기
        });
    });
}

// 답변 작성 완료 이벤트
btnReplyComplete.addEventListener("click", function (){
    let comment = cModifyForm.children[1].value.trim()
    let pcno = cModifyForm.getAttribute("data-pcno");
    let jsonData = {parent:no, pcno:pcno, comment:comment};

    ajaxAPI("comment", jsonData, "post").then((response) => {

        if(response.result == 0)
            alert('댓글 등록에 실패했습니다.');

        else {
            showComment();
            cModifyForm.style.display = "none";
        }
    }).catch((errorMsg) => {
        console.log(errorMsg)
    }); // ajax end
});


window.onload = function() {

    // 로딩 시 댓글 출력
    showComment();

    // textarea 자동 높이 조절
    textareaAutoSize();

    const inputComment = document.querySelector('textarea[name=commentWrite]');

    // 게시물 삭제
    if(btnRemove != null){
        btnRemove.addEventListener("click", function(e){
            e.preventDefault();
            if(!confirm('정말 삭제 하시겠습니까?')) return;

            let jsonData = {"no":no};
            ajaxAPI("gnb/delete", jsonData, "post").then((response) => {

                if(response.result =! 0)
                    location.href = this.href;

                else
                    alert('삭제에 실패했습니다.');

            }).catch((errorMsg) => {
                console.log(errorMsg)
            }); // ajax end
        });
    }

    // 댓글 취소 이벤트
    btnCancel.addEventListener("click", function (e){
        e.preventDefault();
        let textarea = this.parentElement.previousElementSibling;
        textarea.value = "";
    });

    // 댓글 작성 이벤트
    btnComplete.addEventListener("click", function(){

        if(uid === 'anonymousUser') {
            if(confirm('로그인 후에 댓글 작성 가능합니다.\n로그인 페이지로 이동하시겠습니까?')){
                location.href = contextPath + "user/login";
                return;
            }
        }

        // 댓글 내용
        let comment = inputComment.value;

        if(comment.trim() == "") {
            alert("내용을 입력 해주세요.")
            return;
        }

        let jsonData = {parent:no, comment:comment};

        ajaxAPI("comment", jsonData, "post").then((response) => {

            if(response.result == 0)
                alert('댓글 등록에 실패했습니다.');

            else {
                showComment();
                inputComment.value = "";
            }
        }).catch((errorMsg) => {
            console.log(errorMsg)
        }); // ajax end
    });
};