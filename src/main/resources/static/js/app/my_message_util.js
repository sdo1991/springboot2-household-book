var main = {
    init : function () {
        var _this = this;
        var now_page = _this.getUrlParameter('page');
        var last_page=$("#last_page").val();

        var page=now_page;
        console.log(page);
        if(page==null)
            now_page=page=1;
        else if(page==2)
            page=1;
        else if(page>2)
            page=Number(page)-Number(2);

        $('a.page-link').each(function (index, item)
        {
            var number=Number(page)+Number(index)-Number(1);
            if(index!=0 && index!=6)
            {
                $(item).text(number);
                $(item).attr("href","/my_message?page="+number);
                if(number==now_page)
                    $(item).parents('li.page-item').attr("class","page-item active");
                if(number>last_page)
                    $(item).hide();
            }
            else
            {
                if(number==0)
                    number=1;
                if(number>last_page)
                    number=last_page;
                $(item).attr("href","/my_message?page="+number);
            }

        });
        _this.updateMessageCheck();
    },

    getUrlParameter : function(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
        for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    },

    updateMessageCheck : function(x){

        var data={
            userId : $('#user_login_id').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/checkMessage',
//            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }

}

main.init();