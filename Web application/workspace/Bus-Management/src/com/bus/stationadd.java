package com.bus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.DbConnection;

/**
 * Servlet implementation class WindowBooking
 */
@WebServlet("/stationadd")
public class stationadd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public stationadd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String busno=request.getParameter("busno");
	   String busid=request.getParameter("busid");
	   String stationname=request.getParameter("stationname");
			 
			   
	 // int seat=Integer.parseInt(seats);
	   
	   DateFormat format=new SimpleDateFormat("HH:mm");
		 Date date1=new Date();
		 String time1=format.format(date1);
		 System.out.println(busno);
		 System.out.println(busid);
		 System.out.println(stationname);
		 
		 System.out.println(time1);
		    int counter=0;
		   try {
		   HttpSession session=request.getSession();
		Connection con=DbConnection.getConnection();
	          
		/*counter++;
	          
		      PreparedStatement ps3=con.prepareStatement("select * from stationname where trainid="+userid);
		      ResultSet rs=ps3.executeQuery();
		      if(rs.next())
		      {
		    	String stationno=rs.getString("stationno");
		    	  */
		     
                 
        	  PreparedStatement ps2=con.prepareStatement("insert into stationname(busid,busno,stationname,stationno) values(?,?,?,?)");
        	  ps2.setString(1,busid);
        	  ps2.setString(2, busno);
        	  ps2.setString(3, stationname);
        	  ps2.setString(4, "1");
        	    int a=ps2.executeUpdate();
        	    PreparedStatement ps=con.prepareStatement("delete from stationcount limit 1 ");
        		ps.executeUpdate();
        	
        	  if(a>0)
        	  {
        		  response.sendRedirect("Addstation.jsp?added");
        	  }
	   
	
	     		
		   }
	 catch (Exception e) {
		e.printStackTrace();
	}
	   
	
	
	}

}
