<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<form action="<c:url value='/save'/>" method='POST'>
    <table>
        <tr>
            <td>Название:</td>
            <td><label>
                <input type='text' name='name'>
            </label></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><label>
                <input type='text' name='text'>
            </label></td>
        </tr>
        <tr>
            <td>Адрес:</td>
            <td><label>
                <input type='text' name='address'>
            </label></td>
        </tr>
        <tr>
            <td><label>
                <select name='type.id'>
                    <c:forEach items='${types}' var='type' >
                        <option value='${type.id}'>${type.name}</option>
                    </c:forEach>
                </select></label>
            </td>
        </tr>
        <tr>
            <td><label>
                <select name="ruleIds" multiple>
                    <c:forEach var="rule" items="${rules}" >
                        <option value="${rule.id}">${rule.name}</option>
                    </c:forEach>
                </select></label>
            </td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form>
</body>
</html>