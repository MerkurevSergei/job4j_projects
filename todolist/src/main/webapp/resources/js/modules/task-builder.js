define('task-builder', ['cards-manager'], function (cardsmanager) {

    /* ********************************* */
    /* SHOWING FORM WHEN FOCUS ON INPUTS */
    $('.task-builder__header input').focus(function () {
        $('.task-builder__content').addClass('task-builder__content--show');
        $('.task-builder__header').addClass('task-builder__header--show');
    });

    /* ************************************** */
    /* HIDE FORM WHEN CLICK OUTSIDE THE FORM  */
    $(document).click(function (event) {
        if ($(event.target).closest(".task-builder").length) return;
        $('.task-builder__content').removeClass('task-builder__content--show');
        $('.task-builder__header').removeClass('task-builder__header--show');
        event.stopPropagation();
    });

    /* *********************************** */
    /* HIDE FORM AND ADD ITEM WHEN SUBMIT  */
    $(".task-builder").on("submit", function (e) {
        let form = $(this)[0];
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
        } else {
            $('.task-builder__content').removeClass('task-builder__content--show');
            $('.task-builder__header').removeClass('task-builder__header--show');
            cardsmanager.addItem($(this).serialize());
            this.reset();
            form.classList.remove('was-validated');
        }
        e.stopPropagation();
        e.preventDefault();
    });

    /* ***************** */
    /* FLEXIBLE TEXTAREA ON TASKADD-FORM */
    $('.task-builder textarea').on('input', function () {
        this.style.height = '1px';
        this.style.height = (this.scrollHeight + 6) + 'px';
    });

});