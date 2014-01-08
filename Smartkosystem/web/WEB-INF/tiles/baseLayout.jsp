<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <link rel="stylesheet" href="resources/css/stilark.css"/>
    </head>
    <body>
        <div id="page-wrap">
            <table id="block">
                <tr>
                    <td id="tablehead">
                        <tiles:insertAttribute name="header" />
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td id="menu">
                        <tiles:insertAttribute name="menu" />
                    </td>
                    <td id="menubody">
                        <tiles:insertAttribute name="body" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <footer>
                            <p>Alle varer kan variere i pris.</p>
                        </footer>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </body>
</html>
