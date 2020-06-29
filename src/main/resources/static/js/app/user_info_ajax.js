
var main = {
    init : function () {
        var _this = this;

        $(document).on("click","#role_select_submit",function(e){
           _this.update_role();
        });

    },

    update_role : function(){

        var data={
            userId : $('#role_select_user_id').val(),
            role: $('#role_select').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/role_update',
//            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('완료');
//            window.location.href='/main';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
}
main.init();