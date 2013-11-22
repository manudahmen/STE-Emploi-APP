<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" href="styles/styles.css" media="screen" rel="stylesheet" />
<title>Logout page</title>
</head>
<body>
<h1>Logout</h1>
<jsp:scriptlet>   
if(session!=null)
{
session.removeAttribute("user");
session.removeAttribute("session_id");
session.invalidate();
}
</jsp:scriptlet>
<h2 style="margin-left: 200px;">Bye bye</h2>
<h2 style="margin-left: 200px;"><a href="login.jsp">Login</a></h2>
</body>
</html>
</jsp:root>