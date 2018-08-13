<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/app.resume.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Редактирование резюме ${resume.fullName}</title>
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
                <c:forEach var="type" items="<%=SectionType.values()%>">
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
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <br>
            <div class="card">
                <div class="card-header text-white bg-dark"><h3>ФИО</h3></div>
                <div class="card-body">
                    <div class="card-text"><input class="form-control" type="text" name="fullName" size="50"
                                                  value="${resume.fullName}"></div>
                </div>
            </div>
            <br>
            <div class="card">
                <div class="card-header text-white bg-dark" id="contacts"><h3>Контакты:</h3></div>
                <div class="card-body">
                    <p class="card-text">
                        <c:forEach var="type" items="<%=ContactType.values()%>">
                    <dl class="form-row">
                        <dt class="col-3">${type.title}</dt>
                        <dd class="col-6"><input class="form-control" type="text" name="${type.name()}" size="30"
                                                 value="${resume.getContact(type)}">
                        </dd>
                    </dl>
                    </c:forEach>
                    </p>
                </div>
            </div>
            <br>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSections(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE'}">
                        <div class="card">
                            <div class="card-header text-white bg-dark" id="position"><h3>${type.title}</h3></div>
                            <div class="card-body">
                                <div class="card-text"><input class="form-control" type="text" name='${type}' size="75"
                                                              value="<%=section%>"></div>
                            </div>
                        </div>
                        <br>
                    </c:when>
                    <c:when test="${type=='PERSONAL'}">
                        <div class="card">
                            <div class="card-header text-white bg-dark" id="personal"><h3>${type.title}</h3></div>
                            <div class="card-body">
                                <div class="card-text">
                                    <textarea class="form-control" type="text" name='${type}'
                                              rows="3"><%=section%></textarea>
                                </div>
                            </div>
                        </div>
                        <br>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                        <c:choose>
                            <c:when test="${empty  section.getList()}">
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
                                        <div class="card-text">
                                    <textarea class="form-control" type="text" name='${type}'
                                              rows="4"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <br>
                            </c:when>
                            <c:otherwise>
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
                                        <div class="card-text">
                                    <textarea class="form-control" type="text" name='${type}'
                                              rows="4"><%=String.join("\n", ((ListSection) section).getList())%></textarea>
                                        </div>
                                    </div>
                                </div>
                                <br>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type == 'EDUCATION'}">
                        <div class="card">
                            <c:if test="${type=='EXPERIENCE'}">
                                <div class="card-header text-white bg-dark" id="exp"><h3>${type.title}</h3></div>
                            </c:if>
                            <c:if test="${type=='EDUCATION'}">
                                <div class="card-header text-white bg-dark" id="education"><h3>${type.title}</h3></div>
                            </c:if>
                            <div class="card-body">
                                <div class="card-text">
                                    <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>"
                                               varStatus="counter">
                                        <dl class="form-row">
                                            <dt class="col-3">Название учереждения:</dt>
                                            <dd class="col-6"><input class="form-control" type="text" name="${type}"
                                                                     size="100"
                                                                     value="${company.sitePage.name}"></dd>
                                        </dl>
                                        <dl class="form-row">
                                            <dt class="col-3">Сайт учереждения:</dt>
                                            <dd class="col-6"><input class="form-control" type="text" name="${type}_url"
                                                                     size="100"
                                                                     value="${company.sitePage.url}">
                                            </dd>
                                        </dl>
                                        <c:forEach var="position" items="${company.roles}">
                                            <jsp:useBean id="position" type="com.urise.webapp.model.Role"/>
                                            <hr>
                                            <dl class="form-row">
                                                <dt class="col-3">Начальная дата:</dt>
                                                <dd class="col-6"><input class="form-control" type="text"
                                                                         name="${type}${counter.index}startDate"
                                                                         size="10"
                                                                         value="<%=DateUtil.format(position.getDateStart())%>"
                                                                         placeholder="MM/yyyy"></dd>
                                            </dl>
                                            <dl class="form-row">
                                                <dt class="col-3">Конечная дата:</dt>
                                                <dd class="col-6"><input class="form-control" type="text"
                                                                         name="${type}${counter.index}endDate" size="10"
                                                                         value="<%=DateUtil.format(position.getDateEnd())%>"
                                                                         placeholder="MM/yyyy"></dd>
                                            </dl>
                                            <dl class="form-row">
                                                <dt class="col-3">Должность:</dt>
                                                <dd class="col-6"><input class="form-control" type="text"
                                                                         name="${type}${counter.index}name" size="75"
                                                                         value="${position.name}"></dd>
                                            </dl>
                                            <dl class="form-row">
                                                <dt class="col-3">Описание:</dt>
                                                <dd class="col-6"><textarea class="form-control"
                                                                            name="${type}${counter.index}description"
                                                                            rows="3">${position.description}</textarea>
                                                </dd>
                                            </dl>
                                        </c:forEach>
                                        <hr>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <br>
                    </c:when>
                </c:choose>
            </c:forEach>
            <br>
            <div class="content" style="text-align: center">
                <button class="btn btn-primary btn-lg" type="submit">Сохранить</button>
                <button class="btn btn-danger btn-lg" type="button" onclick="window.history.back()">Отменить</button>
            </div>
        </form>

    </div>
</main>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>