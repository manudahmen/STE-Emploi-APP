<%-- 
    Document   : addevent
    Created on : 09-avr.-2009, 11:10:51
    Author     : ubuntuuser
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html:errors/>
<html:form action="AddTask.do">
    <bean:parameter id="etudiant" name="etudiant"/>
    <h3><bean:message key="message.form.addtask.titre"/></h3>
    Catégorie de la tâche:
        <html:select property="type">
            <html:optionsCollection label="label" name="task" property="codes"/>
        </html:select>
        Date et heure: <html:text name="task" property="date" />
    Entreprise:
    Description:
    Commentaires:
    Information supplémentaires: 
    <html:submit/>
</html:form>
<script language="javascript" type="text/javascript">
    function setEventType(type)
    {
        alert("Type:" + type);
        eventType = type;
        // Appel DWR;
    }
</script>
<form id="addevent_form">
    <h3>Ajouter un évènement</h3>
    <select id="type">
        <option id="0" onclick="">Choisir
        <option id="1" onclick="setEventType(this.id)">Envoi CV
        <option id="2" onclick="setEventType(this.id)">Relance téléphonique
        <option id="3" onclick="setEventType(this.id)">Rendez-vous
        <option id="4" onclick="setEventType(this.id)">Informations sur une entreprise
        <option id="5" onclick="setEventType(this.id)">Réponse à l'envoi d'un CV
        <option id="6" onclick="setEventType(this.id)">Dépôt CV en ligne
    </select>

</form>
