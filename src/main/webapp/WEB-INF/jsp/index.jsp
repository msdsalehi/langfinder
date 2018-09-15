<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Masoud Salehi Alamdari - Programming Language Searcher</title>
        <style>
            body{
                background-color: #EEEEEE;
            }
            input{
                font-size: 14pt;
            }
        </style>
    </head>
    <body>
        <form action="results.htm" method="POST">
            <div style="width: 100%; margin-top: 30px; text-align: center;">
                <h1>Search for a programming language</h1>
                <h4>Just by its name, type or even the name of its designers!</h4>
                <input type="text" id="searchBox" name="searchBox" 
                       style="width: 60%; height: 40px; margin-top: 60px; padding-left: 10px;"/>
                <br/>
                <input type="submit" id="searchBtn" name="searchBtn" 
                       style="width: 20%; height: 40px; margin-top: 20px;"
                       value="GO!"/>
            </div>
        </form>
    </body>
</html>
