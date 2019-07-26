<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bus Managment System</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/animate.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/jquery.bxslider.css">
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
	<link rel="stylesheet" type="text/css" href="css/set1.css" />
	<link href="css/overwrite.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  <script type="text/javascript">
function loadXMLDoc()
{
var xmlhttp;
var k=document.getElementById("busid").value;


var urls="pass1.jsp?busid="+k;

 
if (window.XMLHttpRequest)
  {
  xmlhttp=new XMLHttpRequest();
  }
else
  {
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4)
    {
        //document.getElementById("err").style.color="red";
        document.getElementById("err").innerHTML=xmlhttp.responseText;
 
    }
  }
xmlhttp.open("GET",urls,true);

xmlhttp.send();
}
</script>
<script type="text/javascript">
function loadXMLDoc5()
{
var xmlhttp;
var k=document.getElementById("date").value;


var urls="datechecking.jsp?date="+k;

 
if (window.XMLHttpRequest)
  {
  xmlhttp=new XMLHttpRequest();
  }
else
  {
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4)
    {
        //document.getElementById("err").style.color="red";
        document.getElementById("err1").innerHTML=xmlhttp.responseText;
 
    }
  }
xmlhttp.open("GET",urls,true);

xmlhttp.send();
}
</script>

<link href="jquery.datepick.css" rel="stylesheet">
<script src="jquery.plugin.js"></script>
<script src="jquery.datepick.js"></script>
<script>
	$(function() {
		$('#popupDatepicker').datepick();
		$('#inlineDatepicker').datepick({
			onSelect : showDate
		});
	});
</script>
</head>

<% 
if(request.getParameter("wrng")!=null)
{
out.println("<script>alert('Please Enter valid date')</script>");
}
	%>	
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse.collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html"><span>Bus Managment System</span></a>
			</div>
			<div class="navbar-collapse collapse">							
				<div class="menu">
						<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="index.jsp">Home</a></li>
						<li role="presentation"><a href="Addnewbus.jsp">Add-New-Bus</a></li>
		          	<li role="presentation"><a href="businfo.jsp">booking-info</a></li>
		          	<li role="presentation"><a href="report.jsp">Report-all-buses</a></li>
						
									
											
					</ul>
				</div>
			</div>			
		</div>
	</nav>
	
	
	<div class="container">
		<div class="row">
			<div class="slider">
				<div class="img-responsive">
					<ul class="bxslider">				
	  <li><img src="img/bb.jpg" width="120%" alt=""/></li>								
		
					</ul>
				</div>	
			</div>
		</div>
	</div>
		<div align="center">
       
          <form method="post" action="Addbus" >
            <center><table>
            
            
             <tr>
            <td><strong>Startpoint</strong></td><td><input  type="text" name="busid" id="busid" placeholder="101" required onkeyup="loadXMLDoc()"></input>&nbsp;&nbsp;&nbsp;&nbsp;
                        <span id="err"> </span></td>
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr> 
            
            <tr>
  <td><strong>End-point</strong></td><td><input  type="text" name="source" placeholder="Source" required></input></td>
            
            </tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
              <tr>
  <td><strong>Destination</strong></td><td><input  type="text" name="destination" placeholder="destination" required></input></td>
            
            </tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            
            
            
             <tr>
            <td><strong>Date</strong></td><td><input type="text" name="date" id="popupDatepicker" placeholder="date" required></input>&nbsp;&nbsp;&nbsp;&nbsp;
                          </span></td> 
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            
          
           
               <tr>
            <td><strong>No of stops</strong></td><td><input type="text" name="stops"  placeholder="stops-count" required></input></td>
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            
        
			<!-- <tr>
            <td><strong>User Name</strong></td><td><input type="text" name="username" id="username" placeholder="Username" required onkeyup="loadXMLDoc()"></input>&nbsp;&nbsp;&nbsp;
             <span id="err"> </span></td>
            </tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            -->
    
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            
            <tr>
            <td><input type="submit" value="Submit"></input></td>
            
            <td> 
           <input type="reset" value="Reset"></td>
            </tr>
            
            
            
            </table></center>
					
												
			</form>
    	<footer>
		
		<div class="last-div">
			<div class="container">
				<div class="row">
					<div class="copyright">
						© 2014 Smart Ambulance System Multi-purpose theme | <a target="_blank" href="http://bootstraptaste.com">Bootstraptaste</a>
					</div>	
                    <!-- 
                        All links in the footer should remain intact. 
                        Licenseing information is available at: http://bootstraptaste.com/license/
                        You can buy this theme without footer links online at: http://bootstraptaste.com/buy/?theme=Smart Ambulance System
                    -->				
				</div>
			</div>
			<div class="container">
				<div class="row">
					<ul class="social-network">
						<li><a href="#" data-placement="top" title="Facebook"><i class="fa fa-facebook fa-1x"></i></a></li>
						<li><a href="#" data-placement="top" title="Twitter"><i class="fa fa-twitter fa-1x"></i></a></li>
						<li><a href="#" data-placement="top" title="Linkedin"><i class="fa fa-linkedin fa-1x"></i></a></li>
						<li><a href="#" data-placement="top" title="Pinterest"><i class="fa fa-pinterest fa-1x"></i></a></li>
						<li><a href="#" data-placement="top" title="Google plus"><i class="fa fa-google-plus fa-1x"></i></a></li>
					</ul>
				</div>
			</div>
			
			<a href="" class="scrollup"><i class="fa fa-chevron-up"></i></a>	
				
			
		</div>	
	</footer>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-2.1.1.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
	<script src="js/wow.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/jquery.isotope.min.js"></script>
	<script src="js/jquery.bxslider.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script src="js/functions.js"></script>	
	<script type="text/javascript">$('.portfolio').flipLightBox()</script>
  </body>
</html>