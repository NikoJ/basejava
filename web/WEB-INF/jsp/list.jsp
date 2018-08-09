<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>Списое всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="col-1">
        <table class="table table-dark">
            <tr>
                <th>Name</th>
                <th>Mobile</th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                <tr>
                    <td><a href="resume?uuid=${resume.uuid}">${resume.fullName}</a></td>
                    <td>${resume.getContact(ContactType.PHONE)}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>