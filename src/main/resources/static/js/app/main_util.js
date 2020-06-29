var main = {
    init : function () {
        var _this = this;

        $('.tag-button').on('click', function () {
            _this.tag_change($(this));
        });

        $(document).on("click","#update_modal_button",function(){
            _this.open_modal($(this));
        });


        $(document).on("click","#post_report_modal",function(){
            _this.open_post_report_modal($(this));
        });

        $(document).on("click","#reply_report_modal",function(){
            _this.open_reply_report_modal($(this));
        });


        $("input.reply_id").each(function (index,item) {
            if($(item).val()==_this.getUrlParameter("report_reply"))
                $(item).parents('div.reply-media').attr("style","border:2px solid red;")

        });

        if(_this.getUrlParameter("post_id")!=null&&_this.getUrlParameter("reported")=="yes")
        {
            $("#post_contents").attr("style","border:2px solid red;")
        }


/*
        var data={
            postId: x.siblings('input.delete_input').val()
        };

        $("i.language").each(function (index,item) {
            console.log(index);
            console.log($(item).text("dddd"));
        });
*/
    },

    tag_change : function (x) {
        if(x.attr('class')=='tag-button btn btn-outline-primary')
            x.attr('class', 'tag-button btn btn-outline-secondary');
        else
            x.attr('class', 'tag-button btn btn-outline-primary');
    },

    open_modal : function(x){
        //alert(x.siblings('input.update_tags').val());

        $("#update_contents").val(x.siblings('input.update_contents').val());
        $("#update_id").val(x.siblings('input.update_id').val());
        $("#updateModal").modal('show');

        var tags=x.siblings('input.update_tags');
        console.log(tags);
    },


    open_post_report_modal : function(x){
        $("#report_post_id").val(x.parents('div.post_body_div').children('input.hidden_post_id').val());
        $("#report_user_id").val(x.parents('div.media').children('input.post_author_id').val());
        $("#report_target").val('post');
        $("#reportModal").modal('show');

        console.log(tags);
    },

    open_reply_report_modal : function(x){
        $("#report_post_id").val(x.parents('div.post_body_div').children('input.hidden_post_id').val());
        $("#report_user_id").val(x.parents('div.reply-media').children('input.reply_author_id').val());
        $("#report_target").val('reply');
        $("#report_reply_id").val(x.parents('div.reply-media').children('input.reply_id').val())
        $("#reportModal").modal('show');

        console.log(tags);
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
    }

};

main.init();