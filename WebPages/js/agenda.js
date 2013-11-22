
var echeances = null;
var taches = null;
var index_echeance = 0;
var index_tache = 0;
function afficheTaches(d)
{
    var div_j = Document.getElementById("jour_"+d+"_events");
    div_j.innerHTML = "";
    i = index_echeance ;
    j=0;
    while(echeances[i].date==dates[d])
    {
        div_j.innerHTML+="<div id='tache_"+d+"_"+j+">Echeance"
        div_j.innerHTML+="</div>"
        index_echeance++;
        i++;
        j++;
    }
    if(user_role=="etudiant")
    {
        for(i = index_tache ; i<taches.length; i++)
        {
            div_j+="<div id='tache_"+d+"_"+j+"'>Tache"



            div_j+="</div>"
        index_tache++;
        i++;j++;
        }
    }
    div_j.innerHTML+="</div>";
}
function getEcheancesRoleEtudiant()
{
    dateMin = dates[0]+" 00:00:00";
    dateMax = dates[34]+" 23:59:59";
// Appel ajax :  (user_id, session, dateMin, dateMax)
}
function getTachesRoleEtudiant()
{
    dateMin = dates[0]+" 00:00:00";
    dateMax = dates[34]+" 23:59:59";
// Appel ajax :  (user_id, session, dateMin, dateMax)
}
function getEcheancesRoleFormateur()
{
    Document.getElementById("formation");
    Document.getElementById("etudiant_id");
    dateMin = dates[0]+" 00:00:00";
    dateMax = dates[34]+" 23:59:59";
// Appel ajax :  (user_id, session, dateMin, dateMax, formation_id, etudiant_id)
}
function initCalendar()
{

    echeances = getEcheances(user_id, session, millis);
    taches = getTaches(user_id, session, millis);
        if(user_role=="formateur")
        {
            getEcheancesRoleFormateur();
        }
        else
        {
            getEcheancesRoleEtudiant();
            getTachesRoleEtudiant();
        }
for(d=0; d<35; d++)
    {
        afficheEcheances(d);
    }
}
function initCalendarF(value)
{
    formation = value;
    getEcheancesFormation(value);
}
function initCalendarE(value)
{
    etudiant = value;
    getEcheancesEtudiant(value);
}