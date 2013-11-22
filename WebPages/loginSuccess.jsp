<%-- 
    Document   : test
    Created on : 26-avr.-2009, 13:25:16
    Author     : manuel
--%>
<%-- unused --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.myapp.struts.Getter, java.util.Map, java.net.URLDecoder" %>
<%
out.print(request.getParameter("action"));
%>
<%--
<%
String action = URLDecoder.decode(request.getParameter("action"), "UTF-8");
String [] a = action.split("/");
Map<String, String> map = new java.util.HashMap<String, String>();
String url = "/" + a[0]+"?" ;
for(int i=1; i<a.length; i=i+2)    {
        map.put(a[i], a[i+1]);
        url+= a[i]+ "=" + a[i+1];
        if(i<a.length-2) url+="&";
    }
Getter o = new Getter();

o.setObject(map);

out.print(url);
%>
<bean:define id="params" type="com.myapp.struts.Getter"  name="o" property="object"/>
<%--<logic:redirect page="<%= action %>" name="map"/>%>--%>
<%
String action = URLDecoder.decode(request.getParameter("action"), "UTF-8");
out.println("<h1>"+action+"</h1>");
action = action.replaceFirst("id=attr", "id="+((Integer)request.getAttribute("idnew")));
Getter g = new Getter();
g.setObject("action");
out.println("<h1>"+action+"</h1>");
%>

<logic:redirect href="<%= action %>"/>