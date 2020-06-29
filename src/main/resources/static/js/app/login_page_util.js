var main = {
    init : function () {
        var _this = this;

        if(_this.getUrlParameter("error")!=null)
        {
            $("#login_error").show();
        }
        else if(_this.getUrlParameter("logout")!=null)
        {
            $("#login_logout").show();
        }
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