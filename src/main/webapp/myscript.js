var xmlhttp;
function loadXMLDoc(url,cfunc)
{
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=cfunc;
xmlhttp.open("GET",url,true);
xmlhttp.send();

}
function myFunction()
{
loadXMLDoc("/assignment6/solum/meetings",function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
	  var i;
	  var xmlDoc = xmlhttp.responseXML;
	  var table = "<table><tr><th>Year</th><th>Number of Meetings</th></tr>";
	  var x = xmlDoc.getElementsByTagName("meeting");
	  
	  for (i = 0; i <x.length; i++) { 
	    table += "<tr><td>" +
	    x[i].getElementsByTagName("year")[0].childNodes[0].nodeValue +
	    "</td><td>" +
	    x[i].getElementsByTagName("count")[0].childNodes[0].nodeValue +
	    "</td></tr>";
	  }
	  table += "</table>";
	  document.getElementById("myDiv").innerHTML = table;	 
    }
  });
}