<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/app.resume.css">
    <title>Список всех резюме</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">Управление резюме</a><a href="resume?action=add"><img src="img/add.png"></a>
    </nav>
</header>
<main class="container" role="main">
    <div class="main-container">
        <table class="table table-responsive-sm table-hover">
            <thead>
            <tr>
                <th>ФИО</th>
                <th>Почта</th>
                <th>Телефон</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                <tr>
                    <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                    </td>
                    <td><%=ContactType.PHONE.toHtml(resume.getContact(ContactType.PHONE))%>
                    </td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>