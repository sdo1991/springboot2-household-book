
var main = {
    init : function () {
        var _this = this;


        $(window).on("scroll",function(){
            _this.load_more_post(_this);
        });

        $(document).on("click","#help",function(e){
//            _this.load_more_post($(this));
                });


        $(document).on("click",".more_replies_load",function(){
            _this.load_all_replies($(this));

        });

        $(document).on("click","#btn-post-save",function(){
            _this.save($(this));
        });

        $(document).on("click","#btn-post-update",function(){
            _this.update($(this));
        });
        $(document).on("click","#btn-post-delete",function(){
            _this.delete($(this));
        });

        $(document).on("click","#btn-like",function(){
           _this.add_like($(this));
        });


        $(document).on("click","#add_follower_btn",function(){
           _this.add_follower($("#user_login_id").val(),$(this).parents('div.post_body_div').children('input.post_author_id').val());
        });

        $(document).on("click","#add_follower_btn_user_page",function(){
           _this.add_follower($("#user_login_id").val(),_this.getUrlParameter("user_id"));
        });


        $(document).on("click","#btn-reply-save",function(){
           _this.save_reply($(this),_this);
//            _this.load_all_replies($(this).parents('form.reply_write_form').siblings('div.reply').children('div.reply_util_div'));
        });
        $(document).on("click","#update_reply_button",function(){
           _this.update_reply($(this));
        });

        $(document).on("click","#update_reply_submit_button",function(){
           _this.update_submit_reply($(this), _this);
        });


        $(document).on("click","#delete_reply_button",function(){
           _this.delete_reply($(this), _this);
        });

        $(document).on("click",".translate_post",function(){
            var x=$(this);
            var text=x.siblings('p.contents').text();
            var userLang=$("#user_profile_mainLang").val();
            var textLang=x.parents('div.post_body_div').children('input.hidden_author_mainLang').val();

            _this.translate(text,userLang,textLang, x.siblings('p.translate-div'))
//            x.siblings('span.translate-div').html('<hr>'+)
        });

        $(document).on("click",".translate_reply",function(){
            var x=$(this);
            var text=x.siblings('span.contents').text();
            var userLang=$("#user_profile_mainLang").val();
            var textLang=x.parents('div.reply-media').children('input.reply_author_mainLang').val();


            _this.translate(text,userLang,textLang, x.siblings('span.translate-div'))
           // _this.translate(text,userLang,textLang, targetDom)
            //x.siblings('p.translate-div').html('<hr>'+_this.translate(text,userLang,textLang))
        });


        $(document).on("click","#submit_report",function(){
         _this.send_report();
        });


        var post_id = _this.getUrlParameter('post_id');
        if(post_id!=null)
            $(window).off("scroll")
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

    send_report: function(){
//         console.log($("#report_post_id").val());
//         console.log($("#report_user_id").val());


        var data={
            postId : $("#report_post_id").val(),
            reportedUserId: $("#report_user_id").val(),
            reporterUserId: $("#user_login_id").val(),
            contents: $("#report_contents").val(),
            category: $("#report_category").val(),
            target: $("#report_target").val(),
            reportedReplyId: $("#report_reply_id").val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/report',
//            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('신고했습니다.');
            $('#reportModal').modal('hide');
//            window.location.href='/main';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    },

    load_all_replies: function(x){
        var data={
            postId : x.parents('div.post_body_div').children('input.hidden_post_id').val(),
            userId: $('#user_login_id').val()
        };

//        console.log('x:'+x.siblings('input.reply_post_id').val());


        $.ajax({
            type: 'POST',
            url: '/api/post/load_all_replies',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
            var replies=data.replies;
            var s='';
            $.each(replies, function(key, value)
            {
                var r=value;
                s+='<div class="media reply-media">'
                s+='<input type="hidden" value="'+r.author.id+'" class="reply_author_id">'

                s+='<input type="hidden" value="'+r.id+'" class="reply_id">'
                s+='<input type="hidden" value="'+r.author.mainLang+'" class="reply_author_mainLang">'
                s+='<img src="'+r.author.picture+'" alt="John Doe" class="rounded-circle"style="width:30px; height:30px;"><a href="/user_page?user_id='+r.author.id+'">'+r.author.name+'</a><div class="media-body p-1">';
                s+='<span id="post_contents" class="contents">'+r.contents+'</span><span id="translate-div" class="translate-div">'
                s+='</span><br>';
                s+='<button type="button" class="btn translate_reply" id="translate_reply_button" style="padding:1px"><i class="fas fa-language" style="color:blue;font-size:15px"></i>번역</button>'
                s+='<button class="btn" id="reply_report_modal"><i class="fas fa-exclamation-triangle" style="color:red;font-size:15px"></i>신고</button>'

                if(!r.isMine){}
                else{
                    s+='<button type="button" class="btn" id="update_reply_button" style="padding:1px"><i class="fas fa-pencil-alt" style="color:blue;font-size:15px"></i>수정</button>'
                    s+='<button type="button" class="btn" id="delete_reply_button"  style="padding:1px"><i class="fas fa-eraser" style="color:red;font-size:15px"></i>삭제</button>';
                }

                s+='</div></div>'
             });
           x.siblings('div.replies_list').html(s);
           var offset = x.siblings('div.replies_list').offset();
           $('html, body').animate({scrollTop : offset.top}, 400);

            if(x.attr('class')=='more_replies_load')
            {
               x.hide();
            }
//            alert(Number(x.parents('div.media-body').children('i.reply_num').text()));

        }).fail(function(error){
            alert(JSON.stringify(error));
        });


    },

    load_more_post: function(_this)
    {
        if (($(window).scrollTop() >= $(document).height() - $(window).height()-10))
        {

            $(window).off("scroll");
            //alert('load')
            var form={
                userId: $('#user_login_id').val(),
                page: $('input.page').val(),
                url:window.location.href
            };

            $.ajax({
                 type: 'POST',
                 url: '/api/post/load',
                 contentType: 'application/json; charset=utf-8',
                 dataType: 'json',
                 data: JSON.stringify(form)
             }).done(function(data){
             console.log(data);
                var s='';
                $.each(data, function(key, value){

                    var dto=value;
                    var post=dto.post;

                    s+='<div class="media border p-3 post_body_div">'

                    s+='<input type="hidden" value="'+dto.author.id+'" class="post_author_id">'
                    s+='<input type="hidden" class="hidden_post_id" value="'+post.id+'">'
                    s+='<input type="hidden" class="hidden_author_mainLang" value="'+dto.author.mainLang+'">'

                    s+='<div class="media-body"><div class="row">';
                    s+='<img class="rounded-circle align-self-start col-4 col-sm-4 col-md-2 col-lg-2 col-xl-2"'
                   s+=' alt="Cinque Terre" src="'+post.author.picture+'" style="width:60px; height:60px">';
                   s+='<a href="/user_page?user_id='+post.author.id+'"><h4 class="col">'+post.author.name+'</br></a>'
                   s+='<small>'
                   s+='<i class="language"><img src="/image/flagicon/'+dto.author.mainLang+'.png"></i> -> ';

                   $.each(dto.author.interestLang, function(key, value)
                   {
                       s+='<i class="language"><img src="/image/flagicon/'+value+'.png"></i> ';
                   });

                   s+='</small>'
                   s+='</h4></div><p>';
                   s+='<div id="post-image-carousel" class="carousel slide p-1" data-ride="carousel"><ul class="carousel-indicators">';

 //                   for(var i in post.images)
                    $.each(dto.images, function(key, value)
                    {
                        var i=value;
                        s+='<li data-target="#demo" data-slide-to="'+i.number+'" ';
                        if(!i.state){}
                        else
                            s+='class="active">';
                        s+='</li>';
                    });

                    s+='</ul><div class="carousel-inner">'

                    //for(var i in post.images)
                    $.each(dto.images, function(key, value)
                    {
                        var i=value;
                        s+='<div class="carousel-item';
                        if(!i.state){}
                        else
                            s+=' active">'
                        s+='<img src="'+i.imageName+'" style="width:100%; height: 300px;"></div>';
                    });
                    s+='</div><a class="carousel-control-prev" href="#post-image-carousel" data-slide="prev"><span class="carousel-control-prev-icon"></span></a><a class="carousel-control-next" href="#post-image-carousel" data-slide="next"><span class="carousel-control-next-icon"></span></a></div>';

                    s+='<div id="post-body-'+post.id+'"><p id="post_contents" class="contents">'+post.contents+'</p>';
                    s+='<p id="translate-div" class="translate-div"></p><button type="button" id="btn-post-translate" name="btn-post-translate" class="btn translate_post"><i class="fas fa-language" style="color:blue;"></i>번역하기</button><p>'

                    //s+='<small class="text-muted">Last updated '+post.modifiedDate+'</small>'
                    s+='</p>';
//                    for(var t in post.tags)
                    $.each(post.tags, function(key, value)
                    {
                        var t=value;
                        s+='<a href="/tag_page?tag='+t+'" class="card-text">'+'#'+t+'</a>';
                    });
                    s+='<br><small><i>Posted on'+post.createdDate+'</i></small>'

                    s+='<p><div class="row float-left"><input type="hidden" value="'+post.id+'" class="like_post">';
                    s+='<input type="hidden" value="'+$('#user_login_id').val()+'" class="like_user">'

                    s+='<button id="btn-like" class="btn"><i class="'+post.isLike+' fa-heart" style="color:pink"></i><i class="like_num">'+post.likes+'</i></button><button class="btn"><i class="far fa-comment" style="color:blue"></i><i class="reply_num">'+post.replies+'</i></button>'
                    s+='<button class="btn" id="add_follower_btn"><i class="fas fa-user-plus" style="color:blue"></i>팔로우</button>'
                    s+='<button class="btn" id="post_report_modal"><i class="fas fa-exclamation-triangle" style="color:red"></i>신고</button>'
                    s+='</div><div class="float-right" >';
                    if(!post.isMine){}
                    else
                    {
                        s+='<form id="update_form" method="post"><input class="update_id" type="hidden" value="'+post.id+'"><input class="update_contents" type="hidden" value="'+post.contents+'"><input class="update_tags" type="hidden" value="'+post.tags+'"><button type="button" class="btn" id="update_modal_button"><i class="fas fa-pencil-alt" style="color:blue"></i>수정</button></form>'
                        s+='<form id="delete_form" method="post"><input class="delete_input" type="hidden" value="'+post.id+'"><button type="button" class="btn" id="btn-post-delete" ><i class="fas fa-eraser" style="color:red"></i>삭제</button></form>'
                    }

                    s+='</div></p></br></br><hr>';

                    s+='<div class="reply"><input class="reply_post_id" type="hidden" value="'+post.id+'">';
                    s+='<div class="replies_list">';
//                    for(var r in post)
                    $.each(dto.replies, function(key, value)
                    {
                        var r=value;
                        s+='<div class="media reply-media">'
                        s+='<input type="hidden" value="'+r.author.id+'" class="reply_author_id">'

                        s+='<input type="hidden" value="'+r.id+'" class="reply_id">'
                        s+='<input type="hidden" value="'+r.author.mainLang+'" class="reply_author_mainLang">'
                        s+='<img src="'+r.author.picture+'" alt="John Doe" class="rounded-circle"style="width:30px; height:30px;"><a href="/user_page/user_no='+r.author.id+'">'+r.author.name+'</a><div class="media-body p-1">';
                        s+='<span id="post_contents" class="contents">'+r.contents+'</span><span id="translate-div" class="translate-div">'
                        s+='</span><br>';
                        s+='<button type="button" class="btn translate_reply" id="translate_reply_button" style="padding:1px"><i class="fas fa-language" style="color:blue;font-size:15px"></i>번역</button>'
                        s+='<button class="btn" id="reply_report_modal"><i class="fas fa-exclamation-triangle" style="color:red;font-size:15px"></i>신고</button>'
                        if(!r.isMine){}
                        else{
                            s+='<button type="button" class="btn" id="update_reply_button" style="padding:1px"><i class="fas fa-pencil-alt" style="color:blue;font-size:15px"></i>수정</button>'
                            s+='<button type="button" class="btn" id="delete_reply_button"  style="padding:1px"><i class="fas fa-eraser" style="color:red;font-size:15px"></i>삭제</button>';
                        }
                        s+='</div></div>'
                    });
                    s+='</div>';
                    if(!dto.remainReplies){}
                    else
                        s+='<a href="#" class="more_replies_load">'+dto.remainReplies+'개의 댓글 더 보기</a>';
                    s+='<div class="reply_util_div"></div>'

                    s+='</div><form id="reply_write_form" class="reply_write_form"><div class="form-group row"><img src="'+$('#user_profile_image').val()+'" class="rounded-circle col-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" alt="Cinque Terre" style="width:50px; height:50px"><input type="hidden" class="reply_post_id" value="'+post.id+'">';
                    s+='<input type="hidden" class="reply_author_id" value="'+$('#user_login_id').val()+'">'
                    s+='<textarea class="reply_contents form-control col-8 col-sm-8 col-md-8 col-lg-8 col-xl-8" rows="2" id="comment" name="text" style="font-size:9pt; resize:none"></textarea><button type="button" id="btn-reply-save" class="btn btn-light" style="background-color: blue"><i class="far fa-edit" style="color:white"></i></button></div></form>';
                    s+='</div></p></div></div></br></br>';
                });


                $('#posts_div').append(s);

                //console.log(s);

                $('input.page').val(Number(form.page)+Number(1));

                $(window).on("scroll",function(){
                    _this.load_more_post(_this);
                });

            }).fail(function(error){
                alert(JSON.stringify(error));

                $(window).on("scroll",function(){
                    _this.load_more_post(_this);
                });

            });

    }
    },

    save : function (x) {

        var form = $('#write_form')[0];
        // FormData 객체 생성
        var formData = new FormData(form);
        // 코드로 동적으로 데이터 추가 가능.

        var tag='';
        var tags=$('#tags').children();
        $(tags).each(function(index, item){
            if($(item).hasClass( "btn-outline-primary" ))
               tag+=$(item).text();
        });


        formData.append("tags",tag);
        console.log('formdata:'+formData);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/post",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                //alert("SUCCESS : ", data);
                window.location.href='/main';
            },
            error: function (e) {
                alert("ERROR : ", e);
            }})
    },

    update : function(x){

        var tag='';
        var tags=$('#update_tags').children();
        $(tags).each(function(index, item){
            if($(item).hasClass( "btn-outline-primary" ))
               tag+=$(item).text();
        });

        //alert(tag);

        var data={
            id : $('#update_id').val(),
            //id: $('#post_id').val(),
            contents: $('#update_contents').val(),
//            tags:"#tag1#tag2"
        };
        data.tags=tag;


        $.ajax({
            type: 'PUT',
            url: '/api/post',
//            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다');
            window.location.href='/main?post_id='+data.id;
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    },

    delete : function(x){

        var data={
            postId: x.siblings('input.delete_input').val()
        };

        $.ajax({
            type: 'DELETE',
            url: '/api/post',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/main';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

        add_follower : function(sUser, rUser){

            if(sUser==rUser)
            {
                alert('자기 자신은 팔로워할 수 없습니다');
            }
            else{
            var data={
                sendUserId : sUser,
                receiveUserId :rUser
            };


            $.ajax({
                type: 'POST',
                url: '/api/post/add_follower',
    //             dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function(data){
                if(data==true)
                {
                    //x.children('.far').attr('class', 'fas fa-heart');
                    //x.children('i.like_num').text(Number(x.children('i.like_num').text())+1);
                    alert('팔로우 했습니다');
                }
                else
                {
                    alert("이미 팔로우 한 유저입니다.")
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });}
        },

    add_like : function(x){

        var data={
            postId : x.siblings('input.like_post').val(),
            userId : x.siblings('input.like_user').val()
        };
        var tempId=x.parents('div.post_body_div').children('input.post_author_id').val();

        if(data.userId==tempId)
        {
            alert('자신의 글은 좋아요 할 수 없습니다.')
        }
        else
        {
        $.ajax({
            type: 'POST',
            url: '/api/post/add_like',
//             dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
            if(data==true)
            {
                x.children('.far').attr('class', 'fas fa-heart');
                x.children('i.like_num').text(Number(x.children('i.like_num').text())+1);
                //alert('좋아요');
            }
            else
            {
                alert("이미 좋아요를 눌렀습니다.")
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
        }
    },

        save_reply : function(x,_this)
        {

        var data={
            post : x.siblings('input.reply_post_id').val(),
            author : x.siblings('input.reply_author_id').val(),
            contents : x.siblings('textarea.reply_contents').val()
        };


        $.ajax({
            type: 'POST',
            url: '/api/reply',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
            console.log(x.attr('id'))

            _this.load_all_replies(x.parents('form.reply_write_form').siblings('div.reply').children('div.reply_util_div'));

        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    },

        translate : function(text,targetLanguage, sourceLanguage, targetDom){
    //        alert();
            var result='';
            var data={
                text:text,
                sourceLanguage:sourceLanguage,
                targetLanguage:targetLanguage
//                text:x.siblings('p.contents').text(),
//                sourceLanguage: 'ko',
//                targetLanguage: 'en'
            };

            $.ajax({
                type: 'POST',
                url: '/api/translate',
                dataType: 'text',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function(data){
            /*
                x.siblings("p.translate-div").html(data);
                x.hide();*/
                //result= data;
                data="<hr>"+data;
                targetDom.html(data);
            }) .fail(function(error){
                alert(JSON.stringify(error));
            })
//            return result;
        },

        update_reply : function(x)
        {
//            alert("update reply");
            var target=x.siblings("span.contents");
            var contents=target.text();
            var reply_modify='<textarea id="post_contents" class="reply_update_contents form-control">'+contents+'</textarea>';
            reply_modify+='<button type="button" class="btn" id="update_reply_submit_button" style="padding:1px"><i class="fas fa-edit" style="color:blue;font-size:15px"></i>수정하기</button>';

            target.replaceWith(reply_modify);

//            var send_button='<button type="button" class="btn" id="update_reply_submit_button" style="padding:1px"><i class="fas fa-edit" style="color:blue;font-size:15px"></i>수정하기</button>';
//            x.replaceWith(send_button);

        },

        update_submit_reply : function(x, _this)
        {

            var data={
                id : x.parents('div.reply-media').children('input.reply_id').val(),
                contents : x.siblings('.reply_update_contents').val()
            };
            console.log(data.id)
            console.log(data.contents)


            $.ajax({
                type: 'PUT',
                url: '/api/reply',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function(data){
                _this.load_all_replies(x.parents('div.reply').children('div.reply_util_div'));


            }).fail(function(error){
                alert(JSON.stringify(error));
            });


        },

        delete_reply : function(x, _this)
        {
            //alert("delete reply");

            var data={
                id : x.parents('div.reply-media').children('input.reply_id').val()
            };


            $.ajax({
                type: 'DELETE',
                url: '/api/reply',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function(data){
                _this.load_all_replies(x.parents('div.reply').children('div.reply_util_div'));
            }).fail(function(error){
                alert(JSON.stringify(error));
            });

        }

};
main.init();