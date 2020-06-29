var main = {
    init : function ()
    {
        _this=this;

        $(document).on("click","#notice_update_btn",function(){
             _this.submit_notice_update();
        });

        $(document).on("change","#sel1",function(){
             _this.change_sel($(this));
        });

        $("option.sel_option").each(function (index,item) {
            var x=$(item).val();
            var y=$(item).parents('div.alert_body').children('input.alert_type').val()
            if(x==y)
                $(item).attr('selected', 'selected');
        });

    },

    change_sel: function(x)
    {
        x.parents("div.alert_body").attr('class','alert alert_body alert-'+x.val())
    },

    submit_notice_update: function()
    {

        var flag=1;
        if($("#check1").is(":checked")==false)
            flag=0;

        var data={
            ko_title: $("#title_ko").val(),
            ja_title: $("#title_ja").val(),
            en_title: $("#title_en").val(),
            ko_contents: $("#contents_ko").val(),
            ja_contents: $("#contents_ja").val(),
            en_contents: $("#contents_en").val(),

            type: $("#sel1").val(),
            isActivated: flag,
            groupNum:$("#group_num").val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/notice',
//            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('공지를 수정했습니다.');
            window.location.href='/admin_notice_page';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
};
main.init();