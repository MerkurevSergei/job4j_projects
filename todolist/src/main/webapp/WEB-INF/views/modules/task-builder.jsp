<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="btnType" value="${['btn-outline-primary','btn-outline-success','btn-outline-warning']}" scope="application" />
<form class="task-builder needs-validation" novalidate>
    <%--   TASK-BUILDER HEADER   --%>
    <div class="task-builder__header row form-group pt-2 pb-2">
        <div class="col pr-0">
            <label class="sr-only" for="task-builder__title"></label>
            <input type="text"
                   class="form-control form-control-sm position-static"
                   id="task-builder__title" name="title"
                   placeholder="Enter a title..."
                   autocomplete="off" tabindex="1" required>
        </div>
        <div class="col-auto pl-0">
            <button type="submit" class="task-builder__submit btn btn-sm ml-1" tabindex="3">
                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-double-right"
                     fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708z"></path>
                    <path fill-rule="evenodd"
                          d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708z"></path>
                </svg>
            </button>
        </div>
    </div>
    <%--   TASK-BUILDER CONTENT   --%>
    <div class="task-builder__content row form-group pt-2 pb-2">
        <div class="col">
            <label class="sr-only" for="task-builder__description"></label>
            <textarea rows="1" class="form-control form-control-sm"
                      id="task-builder__description" name="description"
                      placeholder="Add a description if necessary..."
                      autocomplete="off"
                      tabindex="2"></textarea>
        </div>
        <div class="col mt-3">
            <div class="task-builder" data-toggle="buttons">
                <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                <c:forEach items="${categories}" var="category" varStatus="categories">
                    <span class="task-builder__category">
                        <input type="checkbox" class="task-builder__category-input btn-check" name="categories[]"
                               id="task-builder__add-category-${category.id}"
                               value="${category.id}" autocomplete="off">
                        <label class="btn btn-sm ${btnType[categories.index % 3]} task-builder__category-label "
                               for="task-builder__add-category-${category.id}">${category.name}</label>
                    </span>
                </c:forEach>
            </div>
        </div>
    </div>
</form>