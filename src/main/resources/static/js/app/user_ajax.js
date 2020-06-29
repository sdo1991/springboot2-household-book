
var main = {
    init : function () {
        var _this = this;

        $(document).on("click","#register-submit",function(){
            _this.join_user();
        });

        $(document).on("click","#user_update_submit",function(){
            _this.update_user();
        });

        $(document).on("click","#email_valid_check_btn",function(){
            _this.email_valid_check_btn();
        });
    },

    email_valid_check_btn: function(){
        var data={
            email : $("#email").val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/email_check',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
            alert(data.message);
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    },

    update_user : function(){
        var form = $('#register-form')[0];
        // FormData 객체 생성
        var formData = new FormData(form);
        // 코드로 동적으로 데이터 추가 가능.


//        formData.append("tags",tag);
        console.log('formdata:'+formData);

        $.ajax({
            type: "PUT",
            enctype: 'multipart/form-data',
            url: "/api/update_user",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
//            dataType: 'json'

        }).done(function(data){
            window.location.href='/update_user';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    join_user : function()
    {
        var form = $('#register-form')[0];
        // FormData 객체 생성
        var formData = new FormData(form);
        // 코드로 동적으로 데이터 추가 가능.


//        formData.append("tags",tag);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/join",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            dataType: 'json'

        }).done(function(data){

            alert(data.message);
            if(data.notError==true)
            {
                var userType=JSON.stringify(data.params.userType)
                if(userType=='"google"')
                {
                    window.location.href='http://localhost:8080/oauth2/authorization/google';
                }
                else if(userType=='"normal"')
                {
                    window.location.href='/main';
                }
                else
                    console.log(userType);
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }


};
main.init();