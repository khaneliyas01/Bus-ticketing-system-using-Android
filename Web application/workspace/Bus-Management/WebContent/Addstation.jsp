<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.util.DbConnection"%>
<%@page import="java.sql.Connection"%>

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

<%if(request.getParameter("add")!=null)
	{
		
	out.println("<script>alert('please insert station  ')</script>");
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
	           
            <%
      //    String Bookseat=session.getAttribute("TSeat").toString();
       //    String Tid=session.getAttribute("Trainid").toString(); 
        //    int bookseat1=Integer.parseInt(Bookseat);
            
       //   ArrayList bookseatn=(ArrayList)session.getAttribute("checklist");  
         // System.out.print("Bookseat:"+Bookseat);
         //   int tid=Integer.parseInt(request.getParameter("Trainid"));
         String busno=session.getAttribute("busno").toString();
         String busid=session.getAttribute("busid").toString();
            Connection con=DbConnection.getConnection();
            
            	  PreparedStatement ps1=con.prepareStatement("select * from stationcount where busid="+busid);
            	  ResultSet rs1=ps1.executeQuery();
            	  if(rs1.next())
            	  {
                  	
          
            %>
           
                    <div align="center">                       
          <form method="post" action="stationadd" >
            <center><table border="1">
            
            <tr>
            <td><strong>bus no</strong></td><td><input  type="text" name="busno" id="busno" value="<%=busno %>" readonly="readonly"> </input></td>
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            
             <tr>
            <td><strong>bus-id</strong></td><td><input  type="text" name="busid"  id="busid" value="<%=busid %>" readonly="readonly"></input></td>
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr> 
            
            <tr>
          <tr>
            <td><strong>Stationname</strong></td><td><input type="text" name="stationname" id="stationname" ></input></td>
            </tr>
           <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
            <tr></tr>
             
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
           
            <%
            
            }
            	  else
            	  {
            		  response.sendRedirect("adminhome.jsp?st");
            	  }
           %>
               
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