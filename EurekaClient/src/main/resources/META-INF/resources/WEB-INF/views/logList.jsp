<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>LogsList</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <style>
        .container {
            width: 75%;
            margin: auto;
            align-content: center;
        }
        .wrapper1 {
            padding-top: 2%;
        }
        .col {
            font-size: 18px;
            padding: 10px;
            text-align: center;
            border: 1px solid grey;
            background-color: black;
            color: white;
            overflow: scroll;
        }
        .goBack {
            position: absolute;
            top: 40px;
            right: 45px;
            width: 90px;
            height: 40px;
        }
        h1 {
            margin-left: 20%;
            color: red;
            width: 70%;
        }
        .logDataWrapper {

        }
    </style>
</head>

<body>
    <button class="btn btn-danger goBack" onclick="location.href='/'">Go back</button >

    <h1>${error}</h1>
    <c:if test="${error eq ''}">
        <div class="container wrapper1">
            <div class="row logHeaderWrapper">
                <div class="col">MessageType</div>
                <div class="col">MessageText</div>
                <div class="col">EntityName</div>
            </div>
            <c:forEach items="${logs}" var ="log">
                <div class="row logDataWrapper">
                    <div class="col">${log.messageType}</div>
                    <div class="col">${log.messageText}</div>
                    <div class="col">${log.entityName}</div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>