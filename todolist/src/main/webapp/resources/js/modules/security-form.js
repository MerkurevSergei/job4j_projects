$(".security-form__form").on("submit", function (e) {
    let form = $(this)[0];
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        e.stopPropagation();
        e.preventDefault();
    }
});