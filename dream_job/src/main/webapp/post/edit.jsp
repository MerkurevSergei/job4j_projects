<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body
        <%
            String id = request.getParameter("id");
            Post post = new Post(0, "", "", LocalDateTime.now());
            if (id != null) {
                post = PsqlStore.instOf().findPostById(Integer.parseInt(id));
            }
        %>>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/upload">Изображения</a>
            </li>
            <li class="nav-item">
                <c:choose>
                    <c:when test="${empty user}">
                        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<%=request.getContextPath()%>/auth.do?action=quit"> <c:out
                                value="${user.name}"/> | Выйти</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новая вакансия
                <% } else { %>
                Редактирование вакансии
                <% } %>
            </div>
            <div class="card-body">
                <form class="needs-validation" action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>"
                      method="post" novalidate>
                    <div class="row mb-3">
                        <div class="col-5">
                            <label for="name">Наименование</label>
                            <input id="name" type="text" class="form-control" name="name" value="<%=post.getName()%>"
                                   required>
                        </div>
                        <div class="col-4 align-self-end mb-2 text-danger" id="nameNoValid">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-5">
                            <label for="description">Описание</label>
                            <input id="description" type="text" class="form-control" name="desc"
                                   value="<%=post.getDescription()%>" required>
                        </div>
                        <div class="col-4 align-self-end mb-2 text-danger" id="descriptionNoValid">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            let forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            let validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                    if (document.getElementById("name").validity.valueMissing) {
                        document.getElementById("nameNoValid").innerHTML = "Заполните наименование";
                    }
                    if (document.getElementById("description").validity.valueMissing) {
                        document.getElementById("descriptionNoValid").innerHTML = "Заполните описание";
                    }
                }, false);
            });
        }, false);
    })();
</script>
</body>
</html>