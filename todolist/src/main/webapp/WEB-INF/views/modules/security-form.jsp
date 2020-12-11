<div class="security-form dropdown m-0 p-0">
    <a class="security-form__toggle dropdown-toggle h6" href="#" role="button"
       data-toggle="dropdown" aria-expanded="false">${param.title}</a>
    <form class="security-form__form row dropdown-menu dropdown-menu-right needs-validation" method="post"
          id='${param.formId}' novalidate action='${param.link}' >
        <div class="col pb-2 pt-1">
            <label class="sr-only" for='${param.nameId}'></label>
            <input type="text"
                   class="form-control form-control-sm"
                   id='${param.nameId}' name="name"
                   placeholder="Enter a name..."
                   autocomplete="off" tabindex="1" required>
        </div>
        <div class="col pt-2 pb-2">
            <label class="sr-only" for='${param.passwordId}'></label>
            <input type="password"
                   class="form-control form-control-sm"
                   id='${param.passwordId}' name="password"
                   placeholder="Enter a password..."
                   tabindex="2"
                   autocomplete="new-password"
                   required>
        </div>
        <div class="col pt-3 pb-1">
            <button type="submit" class="security-form__submit btn btn-block btn-sm" tabindex="3">${param.title}
            </button>
        </div>
    </form>
</div>