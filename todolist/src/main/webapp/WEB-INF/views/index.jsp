<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS, JavaScript dependencies -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
            integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous"></script>
    <script data-main="resources/js/index"
            src="${pageContext.request.contextPath}/resources/js/lib/require.js"></script>
    <title>To-Do List</title>
</head>
<body>

<div class="index__header container-fluid pl-4 pr-4">
    <div class="row">
        <%--   TASK-FILTER   --%>
        <div class="index__task-filter col-auto align-self-center">
            <c:import url="modules/task-filter.jsp"/>
        </div>

        <%--   TASK-BUILDER   --%>
        <div class="col-auto mr-auto align-self-center">
            <c:import url="modules/task-builder.jsp"/>
        </div>

        <div class="index__security col-auto align-self-center mt-1 mb-1">
            <div class="security row">

                <%-- NOT AUTHORIZE  --%>
                <c:if test="${empty sessionScope.user}">
                    <%-- SING UP --%>
                    <div class="col-auto">
                        <c:import url="modules/security-form.jsp">
                            <c:param name="link" value="sign-up"/>
                            <c:param name="formId" value="sign-up__form"/>
                            <c:param name="nameId" value="sign-up__name"/>
                            <c:param name="passwordId" value="sign-up__password"/>
                            <c:param name="title" value="Sing Up"/>
                        </c:import>
                    </div>
                    <%-- SEPARATOR --%>
                    <span class="col-auto pl-0 pr-0 ml-2 mr-2"
                          style="color: #0d6efd; display: inline-block">|</span>
                    <%-- SIGN IN --%>
                    <div class="col-auto">
                        <c:import url="modules/security-form.jsp">
                            <c:param name="link" value="sign-in"/>
                            <c:param name="formId" value="sign-in__form"/>
                            <c:param name="nameId" value="sign-in__name"/>
                            <c:param name="passwordId" value="sign-in__password"/>
                            <c:param name="title" value="Sign In"/>
                        </c:import>
                    </div>
                </c:if>

                <%-- ALLREADY AUTHORIZE  --%>
                <c:if test="${!empty sessionScope.user}">
                    <a class="security-form__toggle h6 m-0" href="${pageContext.request.contextPath}/sign-out">
                        <c:out value="Sign Out (${sessionScope.user.name})"/>
                    </a>
                </c:if>
            </div>
        </div>

    </div>
</div>

<div class="index__content container-fluid overflow-hidden pl-4 pr-4 mt-3">
    <div class="card-container row row-cols-auto g-4">
    </div>
</div>
<script id="cardTemplate" type="text/x-jsrender">
    <c:import url="modules/card.jsp"/>
</script>
</body>
</html>