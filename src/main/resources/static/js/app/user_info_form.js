var main = {
        init : function () {
            $('[data-toggle="popover"]').popover({html: true});

            $('#datepicker').datepicker({
                    uiLibrary: 'bootstrap4',
                    format: 'yyyy-mm-dd'
            });

            var _this = this;

            $(document).on("click","a.select_interest_lang",function(){
                _this.select_interest_lang($(this));
            });


            $(document).on("change keyup paste","#myInput",function(){
                _this.myFunction();
            });

            $(document).on("click","#lang_submit",function(){
                _this.put_interest_langs();
            });

            $(document).on("change","#profile_image",function(){
               _this.makePreview(this);
            });

        },

        select_interest_lang : function(x)
        {
            if(x.attr('class')=='select_interest_lang')
                x.attr('class','select_interest_lang selected');
            else
                x.attr('class','select_interest_lang');

        },

        put_interest_langs : function()
        {
            var x=$('.lang_list a.selected');
            var vals='';
            var text='';

            x.each(function (index, item)
            {
                vals+=$(item).attr('name');
                text+=$(item).text();
                if(index<x.length-1)
                {
                    vals+=','
                    text+=', '
                }
            });


            $('#interest_lang_text').val(text);
            $('#interest_lang').val(vals);

        },

        myFunction : function()
        {
           var input, filter, ul, li, a, i, txtValue;
           input = document.getElementById("myInput");
           filter = input.value.toUpperCase();
           ul = document.getElementById("myUL");
           li = ul.getElementsByTagName("li");
           for (i = 0; i < li.length; i++) {
               a = li[i].getElementsByTagName("a")[0];
               txtValue = a.textContent || a.innerText;
               if (txtValue.toUpperCase().indexOf(filter) > -1) {
                   li[i].style.display = "";
               } else {
                   li[i].style.display = "none";
               }
           }
        },

        makePreview : function(input)
        {
          if (input.files && input.files[0]) {
          var reader = new FileReader();

          reader.onload = function (e) {
           $('#image').attr('src', e.target.result);
          }

          reader.readAsDataURL(input.files[0]);
          }
        }


};
main.init();