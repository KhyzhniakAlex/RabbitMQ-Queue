<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>DoctorsList</title>
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
            .createWr1 {
                margin: auto;
            }
            .createWr2 {
                align-content: center;
                /*margin-left: 20%;*/
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
                text-align: center;
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
            <div class="col">FirstName</div>
            <div class="col">LastName</div>
            <div class="col">Age</div>
            <div class="col">Salary</div>
            <div class="col">Actions</div>
        </div>
        <c:forEach items="${doctors}" var ="doctor">
            <div class="row">
                <div class="col">${doctor.firstName}</div>
                <div class="col">${doctor.lastName}</div>
                <div class="col">${doctor.age}</div>
                <div class="col">${doctor.salary}</div>
                <div class="col">
                    <a type="button" class="btnAction" href="/doctor/${doctor.id}">Detail</a>
                    <a type="button" class="btnAction" href="/doctor/delete/${doctor.id}">Delete</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <br>
    <br>
    <br>

    <form action = "/doctor/create" method = "post" >
        <div class="createWr1" >
            <h2> Add new doctor</h2>
        </div>
        <div class="container createWr2" >
            <div class="row">
                <div class="col" > FirstName </div >
                <div class="col" > LastName </div >
                <div class="col" > Age </div >
                <div class="col" > Salary </div >
            </div>
            <div class="row">
                <div class="col" ><input type = "text" name = "firstName" required ></div >
                <div class="col" ><input type = "text" name = "lastName" required ></div >
                <div class="col" ><input type = "text" name = "age" required pattern="^[ 0-9]+$" ></div >
                <div class="col" ><input type = "text" name = "salary" required pattern="^[ 0-9]+$"></div >
            </div>
        </div >

        <div class="forSubmit" >
            <button type = "submit" class="btn btn-success" > Submit </button >
        </div >
    </form>
    </body>
</html>