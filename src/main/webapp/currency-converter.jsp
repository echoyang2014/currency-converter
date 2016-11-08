<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <div class="logout">
            ${pageContext.request.userPrincipal.name} <a class="links" onclick="document.forms['logoutForm'].submit()" onmouseover="" >Logout</a>
        </div>
    </c:if>

    <form:form method="POST" modelAttribute="exchangeRateRequest" class="form-signin">
        <spring:bind path="from">
            <div class="form-group">
                <span class="currency-title"> From</span><form:select path="from" items="${currencies}"/>
            </div>
        </spring:bind>
        <spring:bind path="to">
            <div class="form-group">
                <span class="currency-title"> To</span><form:select path="to" items="${currencies}"/>
            </div>
        </spring:bind>
        <spring:bind path="date">
            <div class="form-group">
                <span class="currency-title"> Date</span><form:input type="date" path="date" />
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary" type="submit">Get Rate</button>
    </form:form>

    <c:if test="${!empty lastCurrencyExchanges}">
        <center>
            <table class="currencyHistoryTable">
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Rate</th>
                    <th>Exchange Date</th>
                </tr>

                <c:forEach items="${lastCurrencyExchanges}" var="currencyExchange">
                    <tr class="currencyHistoryRate" id="${currencyExchange.id}" onclick="setUpdateForm('${currencyExchange.id}');">
                        <td><c:out value="${currencyExchange.fromCode}"/></td>
                        <td><c:out value="${currencyExchange.toCode}"/></td>
                        <td><c:out value="${currencyExchange.displayRate}"/></td>
                        <td><c:out value="${currencyExchange.displayExchangeDate}"/></td>
                    </tr>
                </c:forEach>
            </table>
            </center>
    </c:if>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
