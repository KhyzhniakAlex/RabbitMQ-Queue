<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Typical hospital</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <style>
        * {
            margin: 0; padding: 0;
            /* border: 1px solid red; */
        }
        body {
            background-color: black;
        }
        .indexImage {
            width: 100%;
        }
        .choosingTab {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr 1fr;
            margin: 70px 0;
        }
        .choosingButton {
            -webkit-border-radius: 10%;
            -moz-border-radius: 10%;
            border-radius: 10%;
            text-align: center;
        }
        .chooseBtn {
            height: 60px;  width: 60%;
        }
        .loginBtn {
            position: absolute;
            top: 40px;
            right: 45px;
            width: 120px;
            height: 50px;
        }
        .logoutBtn {
            position: absolute;
            top: 115px;
            right: 45px;
            width: 120px;
            height: 50px;
        }
        .welcomeText {
            position: absolute;
            top: 8%;
            left: 4%;
            font-size: 2.5em;
            display: block;
            padding: 10px 15px;
            background-color: white;
            color: black;
            opacity: 0.8;
            border-radius: 25%;
        }
    </style>
</head>

<body>
<div class="welcomePage">
    <div class="welcomeImage"><img src="http://www.healthforum.com/images/lp-top-image-hospital-resources.jpg" class="indexImage"></div>

    <h2 class="welcomeText">Welcome to Typical hospital</h2>
    <button type="button" onclick="location.href='/login'" class="btn btn-danger loginBtn">Log in</button>
    <button type="button" onclick="location.href='/logout'" class="btn btn-dark logoutBtn">Log out</button>

    <div class="choosingTab">
        <div class="choosingButton">
            <button type="button" onclick="location.href='/department'" class="btn btn-primary chooseBtn">Departments</button>
        </div>
        <div class="choosingButton">
            <button type="button" onclick="location.href='/doctor'" class="btn btn-success chooseBtn">Doctors</button>
        </div>
        <div class="choosingButton">
            <button type="button" onclick="location.href='/patient'" class="btn btn-info chooseBtn">Patients</button>
        </div>
        <div class="choosingButton">
            <button type="button" onclick="location.href='/logs'" class="btn btn-warning chooseBtn">Logs</button>
        </div>
    </div>
</div>
</body>
</html>
