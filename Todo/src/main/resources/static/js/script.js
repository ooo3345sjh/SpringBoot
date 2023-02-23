$(function(){
    $('article').sortable({
            connectWith: "article",
            scroll: false,
            helper: "clone",
            receive: function(e, ui){
                let no = $(ui.item).attr('data-no');
                let value = $(this).attr('data-status');

                let jsonData = {
                    "no":no,
                    "status":value
                }
                console.log(jsonData);

                ajaxAPI(no, jsonData, "PATCH").then((response) => {
                    console.log(response);
                    if(response.result == 1){
                        //sorting($(this));
                    } else {
                        alert("수정에 실패했습니다. 다시 시도해주세요.");
                    }


                }).catch((errorMsg) => {
                    console.log(errorMsg)
                });
            },
            update: function(e, ui){
                let no = $(ui.item).attr('data-no');
                let value = $(this).attr('data-status');

                let jsonData = {
                    "no":no,
                    "status":value
                }
                console.log(jsonData);

                ajaxAPI(no, jsonData, "PATCH").then((response) => {
                    console.log(response);
                    if(response.result == 1){

                    } else {
                        alert("수정에 실패했습니다. 다시 시도해주세요.");
                    }


                }).catch((errorMsg) => {
                    console.log(errorMsg)
                });
            }
    });

    $('#btnAdd').click(function(){
        let value = $('input[name=todo]').val();

        if(value.trim() == "") {alert("내용을 입력해주세요."); return;}

        let jsonData = {"content":value};
        console.log(jsonData);

        ajaxAPI("", jsonData, "POST").then((response) => {
            console.log(response);
            if(response.entity != null){


                    let item = "<div class='item' data-no='" + response.entity.no + "'>"
                             +  "     <button class='del'>X</button>"
                             +  "     <em class='tit'>#" + response.entity.no + "</em>"
                             +  "     <p>" + response.entity.content + "</p>"
                             +  "     <span class='date'>" + response.entity.rdate.substr(0, 10); + "</span>"
                             +  " </div>";

                    $('.ready').append(item);

            } else {
                alert("업로드에 실패했습니다. 다시 시도해주세요.");
            }


        }).catch((errorMsg) => {
            console.log(errorMsg)
        });


    });

    $(document).on('click', '.del', function(){

         if(!confirm("정말 삭제 하시겠습니까?")) {return;}

         let no =  $(this).parent().data("no");
         let jsonData = {no:"no"}
         ajaxAPI(no, jsonData, "DELETE").then((response) => {
            console.log(response);
            if(response.result == 1){
                $(this).parent().remove();

            } else {
                alert("삭제에 실패했습니다. 다시 시도해주세요.");
            }
        }).catch((errorMsg) => {
            console.log(errorMsg)
        });


    });
})
/*
function sorting(obj){
    let children = obj.parent().children();

    console.log(children);
}
*/