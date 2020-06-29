var main = {
    init : function () {
        var _this = this;

        $('[data-toggle="popover"]').popover({html: true});
        $("textarea.autosize").on('keydown keyup', function () {$(this).height(1).height( $(this).prop('scrollHeight')+12 );});

        $('[data-toggle="tooltip"]').tooltip();
    },

}

main.init();