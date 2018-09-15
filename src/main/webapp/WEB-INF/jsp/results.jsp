<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Masoud Salehi Alamdari - Programming Language Searcher</title>
        <link href="<c:url value='/resources/css/results.css'/>" type="text/css" rel="stylesheet"/>
        <script src="<c:url value='/resources/js/jquery-1.11.2.min.js'/>"></script>
        <script src="<c:url value='/resources/js/results.js'/>"></script>
        <script>
            jQuery(document).ready(function () {
                search(jQuery('#searchBox').val());
            });
        </script>
    </head>
    <body>
        <div style="background-color: #EEEEEE; overflow: auto;">
            <h2 style="float: left; margin-right: 20px; margin-left: 20px;">Search</h2>
            <input type="text" id="searchBox" name="searchBox" onkeyup="search(jQuery('#searchBox').val())"
                   style="width: 40%; height: 40px; margin-top: 10px; margin-right: 20px; float: left;"
                   value="<%= request.getParameter("searchBox") %>"/>
            <input type="button" id="searchBtn" name="searchBtn" 
                   style="width: 80px; height: 47px; margin-top: 10px; float: left;"
                   value="GO!" onclick="search(jQuery('#searchBox').val())"/>
        </div>
        <div id="resultsBox" style="clear: both; padding: 20px;">
        </div>
    </body>
</html>
