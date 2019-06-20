<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DepartmentsList</title>
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
        .goBack {
            position: absolute;
            top: 40px;
            right: 45px;
            width: 90px;
            height: 40px;
        }
        h2 {
            text-align: center;
        }
        .forSubmit {
            margin-top: 3%;
            margin-left: 45%;
        }
        input {
            width: 70%;
        }
        .btnAction {
            text-decoration: none;
            background-color: grey;
            color: #FFFFFF;
            padding: 4px;
            margin: 0 1% 0 1%;
        }
    </style>
</head>

<body>
    <button class="btn btn-danger goBack" onclick="location.href='/'">Go back</button >

<div class="container wrapper1">
    <div class="row">
        <div class="col">Name</div>
        <div class="col">Floor</div>
        <div class="col">Actions</div>
    </div>
    <c:forEach items="${departments}" var ="department">
        <div class="row">
            <div class="col">${department.name}</div>
            <div class="col">${department.floor}</div>
            <div class="col">
                <a type="button" class="btnAction" href="/department/${department.id}">Detail</a>
                <a type="button" class="btnAction" href="/department/delete/${department.id}">Delete</a>
            </div>
        </div>
    </c:forEach>
</div>

<br>
<br>
<br>

<form action = "/department/create" method = "post" >
    <div class="createWr1" >
        <h2> Add new department</h2>
    </div>
    <div class="container createWr2" >
        <div class="row">
            <div class="col" > Name </div >
            <div class="col" > Floor </div >
        </div>
        <div class="row">
            <div class="col" ><input type = "text" name = "name" required ></div >
            <div class="col" ><input type = "text" name = "floor" required pattern="^[ 0-9]+$" ></div >
        </div>
    </div >

    <div class="forSubmit" >
        <button type = "submit" class="btn btn-success" > Submit </button >
    </div >
</form>
</body>
</html>