
window.onload = function() {
    const btnRemove = document.querySelector('.btnRemove');
    const btnGoList = document.querySelector('.btnList');

    btnRemove.addEventListener("click", function(e){
        e.preventDefault();
        const urlParams = new URL(location.href).searchParams;
        const no = urlParams.get('no');

        let jsonData = {"no":no};
        ajaxPost("gnb/delete", jsonData).then((response) => {

            if(response.result == 1)
                btnGoList.click();

            else
                alert('삭제에 실패했습니다.');

        }).catch((errorMsg) => {
            console.log(errorMsg)
        });
    });
};
