<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Patient</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <style>
        .container {
            width: 65%;
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
        }
        .forSubmit {
            margin-top: 3%;
            text-align: center;
        }
        .goBack {
            position: absolute;
            top: 40px;
            right: 45px;
            width: 90px;
            height: 40px;
        }
        input {
            width: 70%;
        }
    </style>
</head>
<body>
<button class="btn btn-danger goBack" onclick="location.href='/patient'">Go back</button >

<form action="/patient/update/${patient.id}" method="post">
    <div class="wrapper1 container">
        <div class="row">
            <div class="col">Id</div>
            <div class="col">FirstName</div>
            <div class="col">LastName</div>
            <div class="col">Age</div>
            <div class="col">Diagnosis</div>
        </div>
        <div class="row">
            <div class="col"><input value="${patient.id}" readonly> </div>
            <div class="col"><input type = "text" value="${patient.firstName}" name = "age" required pattern="^[ 0-9]+$" ></div>
            <div class="col"><input type = "text" value="${patient.lastName}" name = "lastName" required ></div>
            <div class="col"><input type = "text" value="${patient.age}" name = "age" required pattern="^[ 0-9]+$" ></div>
            <div class="col"><input type = "text" value="${patient.diagnosis}" name = "diagnosis" required ></div>
        </div>
    </div>

    <div class="forSubmit" >
        <button type = "submit" class="btn btn-success" > Submit </button >
    </div >
</form>
</body>
</html>