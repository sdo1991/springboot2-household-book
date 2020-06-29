var main = {
    init : function () {
        var _this = this;

        _this.getInfo();




    },

    getInfo: function()
    {

        var userType=$("#user_type").val();
        if(userType!="normal")
            $("#password_form_group").hide();

        var gender=$("#user_gender").val()
        if(gender=="male")
        {
            $("#gender_male").attr("checked", true);
        }
        else
        {
            $("#gender_female").attr("checked", true);
        }

        var mainLang=$("#user_main_lang").val();
        if(mainLang=="ko")
        {
            $("#ko_op").attr("selected", true)
        }
        else if(mainLang=="ja")
        {
            $("#ja_op").attr("selected", true)
        }
        else if(mainLang=="en")
        {
            $("#en_op").attr("selected", true)
        }
        else{}



        var x=$("#interest_lang").val().split( ",");

        var vals='';
        var text='';
        console.log(x);

        for(var i in x)
        {
            if(x[i]=="ko")
            {
                text+="한국어";
            }
            else if(x[i]=="ja")
            {
                text+="일본어";
            }
            else if(x[i]=="en")
            {
                text+="영어";
            }
            vals+=x[i];

            if(i<x.length-2)
            {
                vals+=','
                text+=', '
            }
        }
        $('#interest_lang_text').val(text);
        $('#interest_lang').val(vals);
    }

}

main.init();