<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.TextBoxSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/app.resume.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="resume"><img src="img/back.png"> Управление резюме</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">ФИО <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contacts">Контакты </a>
                </li>
                <c:forEach var="resumeType" items="${resume.sections}">
                    <c:set var="type" value="${resumeType.key}"/>
                    <c:choose>
                        <c:when test="${type=='OBJECTIVE'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#position">${type.title} </a>
                            </li>
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#personal">${type.title} </a>
                            </li>
                        </c:when>
                        <c:when test="${type=='ACHIEVEMENT'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#achiev">${type.title} </a>
                            </li>
                        </c:when>
                        <c:when test="${type=='QUALIFICATIONS'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#qualific">${type.title} </a>
                            </li>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#exp">${type.title} </a>
                            </li>
                        </c:when>
                        <c:when test="${type=='EDUCATION'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#education">${type.title} </a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </nav>
</header>
<main role="main" class="container">
    <div class="resume-container">
        <h2 id="contacts">${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a>
        </h2>
        <hr>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <c:set var="type" value="${contactEntry.key}"/>
                <c:choose>
                    <c:when test="${type=='PHONE'}">
                        <img src="img/mobile.png"
                             alt="Телефон: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </c:when>
                    <c:when test="${type=='SKYPE'}">
                        <img src="img/skype.png"
                             alt="Skype: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </c:when>
                    <c:when test="${type=='EMAIL'}">
                        <img src="img/mail.png"
                             alt="Email: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                        <br/>
                    </c:when>
                    <c:when test="${type=='GITHUB'}">
                        <img src="img/git.png"
                             alt="GitHub: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                        <br/>
                    </c:when>
                    <c:when test="${type=='STACKOVERFLOW'}">
                        <img src="img/stov.png"
                             alt="StackOverflow: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </c:when>
                    <c:when test="${type=='HEADHUNTER'}">
                        <img src="img/hh.png"
                             alt="HeadHunter: "> <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </c:when>
                </c:choose>
            </c:forEach>
        </p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <c:if test="${type=='OBJECTIVE'}">
                <div class="card">
                    <div class="card-header text-white bg-dark" id="position"><h3> ${type.title} </h3></div>
                    <div class="card-body">
                        <h5 class="card-title"><%=((TextBoxSection) section).getText()%>
                        </h5>
                    </div>
                </div>
                <br>
            </c:if>
            <c:if test="${type!='OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type=='PERSONAL'}">
                        <div class="card">
                            <div class="card-header text-white bg-dark" id="personal"><h3> ${type.title} </h3></div>
                            <div class="card-body">
                                <p class="card-text">
                                    <%=((TextBoxSection) section).getText()%>
                                </p>
                            </div>
                        </div>
                        <br>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                        <div class="card">
                            <c:if test="${type=='ACHIEVEMENT'}">
                                <div class="card-header text-white bg-dark" id="achiev"><h3>${type.title}</h3>
                                </div>
                            </c:if>
                            <c:if test="${type=='QUALIFICATIONS'}">
                                <div class="card-header text-white bg-dark" id="qualific"><h3>${type.title}</h3>
                                </div>
                            </c:if>
                            <div class="card-body">
                                <p class="card-text">
                                <ul class="list-rectangle">
                                    <c:forEach var="item" items="<%=((ListSection)section).getList()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                                </p>
                            </div>
                        </div>
                        <br>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type == 'EDUCATION'}">
                        <div class="card">
                            <c:if test="${type=='EXPERIENCE'}">
                                <div class="card-header text-white bg-dark" id="exp"><h3>${type.title}</h3>
                                </div>
                            </c:if>
                            <c:if test="${type=='EDUCATION'}">
                                <div class="card-header text-white bg-dark" id="education"><h3>${type.title}</h3>
                                </div>
                            </c:if>
                            <div class="card-body">
                                <c:forEach var="company" items="<%=((CompanySection)section).getCompanies()%>">
                                    <c:choose>
                                        <c:when test="${empty company.sitePage.url}">
                                            <div class="card-title">
                                                <ul class="list-rectangle">
                                                    <li>
                                                        <h3>${company.sitePage.name}</h3>
                                                    </li>
                                                </ul>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <h3 class="card-title">
                                                <ul class="list-rectangle">
                                                    <li>
                                                        <a href="${company.sitePage.url}">${company.sitePage.name}</a>
                                                    </li>
                                                </ul>
                                            </h3>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:forEach var="position" items="${company.roles}">
                                        <jsp:useBean id="position" type="com.urise.webapp.model.Role"/>
                                        <table>
                                            <tr>
                                                <td class="td-time"><%=HtmlUtil.formatDates(position)%>
                                                </td>
                                                <br>
                                                <td><b>${position.name}</b><br>${position.description}</td>
                                            </tr>
                                        </table>
                                        <br>
                                    </c:forEach>
                                </c:forEach>
                            </div>
                        </div>
                        <br>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </div>
</main>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>