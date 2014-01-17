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
            <table id="block" cellspacing="0" cellpadding="0">
                <tr>
                    <td id="tablehead" colspan="3">
                        <tiles:insertAttribute name="header" />
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
                            Footer
                        </footer>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </body>
</html>
