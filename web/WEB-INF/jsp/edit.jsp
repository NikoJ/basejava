<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.Section" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
            </dd>
        </dl>
        </c:forEach>
        </p>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSections(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type="text" name='${type}' size="75" value="<%=section%>">
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea type="text" name='${type}' cols="75" rows="5"><%=section%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                    <c:choose>
                        <c:when test="${empty  section.getList()}">
                            <textarea name="${type}" cols="75"
                                      rows="5"></textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea name="${type}" cols="75"
                                      rows="5"><%=String.join("\n", ((ListSection) section).getList())%></textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>" varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name="${type}" size="100" value="${company.sitePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name="${type}_url" size="100" value="${company.sitePage.url}"></dd>
                        </dl>
                            <c:forEach var="position" items="${company.roles}">
                                <jsp:useBean id="position" type="com.urise.webapp.model.Role"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd><input type="text" name="${type}${counter.index}startDate" size="10"
                                               value="<%=DateUtil.format(position.getDateStart())%>"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd><input type="text" name="${type}${counter.index}endDate" size="10"
                                               value="<%=DateUtil.format(position.getDateEnd())%>"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name="${type}${counter.index}name" size="75"
                                               value="${position.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}description" cols="75"
                                                  rows="5">${position.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>