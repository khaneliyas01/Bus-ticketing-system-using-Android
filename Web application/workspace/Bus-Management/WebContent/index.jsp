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
				<a class="navbar-brand" href="index.jsp"><span>Bus Managment System</span></a>
			</div>
			<div class="navbar-collapse collapse">							
				<div class="menu">
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="index.jsp">Home</a></li>
										
					</ul>
				</div>
			</div>			
		</div>
	</nav>
					
	
	 <%
    if(request.getParameter("failed")!=null)
	{
	out.println("<script>alert('login unsuccessfully')</script>");
	}
    %>
	
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
		<center> 
	 <h2>admin Login</h2>
     <form action="Adminlogin" name="admin" method="post" onsubmit="return validation()">
	<table cellspacing="1"  cellpadding="5" style="width:50%; margin-left:auto;margin-right:auto;">
	<tr class="tableheader">
        <p>
         
          <label for="username">Username: </label>
          <input type="text" class="text" name="username" id="username" />
        </p>
        <p>
          <label for="password">Password: </label>
          <input type="password"  class="text" name="password" id="password" />
        </p>
        <p>
          <input type="submit" name="Login" value="Login" /> 

		</p>
		<p>							

						</center>		
        </p>
       
        </tr>
        </table>
      </form>
  </center>
		
	
	<footer>
					<div class="col-md-4 f-contact">
						<h3 class="widgetheading">Stay in touch</h3>
						<a href="#"><p><i class="fa fa-envelope"></i> example@gmail.com</p></a>
						<p><i class="fa fa-phone"></i>  +345 578 59 45 416</p>
						<p><i class="fa fa-home"></i> Bus Managment System inc  |  PO Box 23456 
							Little Lonsdale St, New York <br>
							Victoria 8011 USA</p>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="last-div">
			<div class="container">
				<div class="row">
					<div class="copyright">
						� 2014 Bus Managment System Multi-purpose theme | <a target="_blank" href="http://bootstraptaste.com">Bootstraptaste</a>
					</div>	
                    <!-- 
                        All links in the footer should remain intact. 
                        Licenseing information is available at: http://bootstraptaste.com/license/
                        You can buy this theme without footer links online at: http://bootstraptaste.com/buy/?theme=Bus Managment System
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