package com.bus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
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
 * Servlet implementation class Addtrains
 */
@WebServlet("/Addbus")
public class Addbus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Addbus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
	    String busno=request.getParameter("bno");
	    String busid=request.getParameter("busid");
	    String source=request.getParameter("source");
	    String destination=request.getParameter("destination");
	    String date=request.getParameter("date");
		String stops=request.getParameter("stops");
		int Stops1=Integer.parseInt(stops);	
		System.out.println("stoplength:"+Stops1);
		DateFormat format=new SimpleDateFormat("HH:mm");
		 Date date1=new Date();
		 String time=format.format(date1);
		 System.out.println("time:"+time);
		 session.setAttribute("busno", busno);
		 session.setAttribute("busid", busid);
		
		 
		try {
			  DateTest t=new DateTest();
	          int dd= t.dateDiffernce(date);
	          if(dd>0)
	          {
	        	  
	      
			Connection con=DbConnection.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into buses(busno,busid,source,destination,daten,timen,stops,rate) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, busno);
			ps.setString(2, busid);
			ps.setString(3, source);
			ps.setString(4, destination);
			ps.setString(5, date);
			ps.setString(6, time);
			ps.setInt(7,Stops1);
			ps.setString(8, "0");
			int a=ps.executeUpdate();
		
			if(a>0)
			{
				for(int i=1;i<=Stops1;i++)
				{	      	
				PreparedStatement ps2=con.prepareStatement("insert into stationcount(stationno,busid) values(?,?)");
				 ps2.setString(1, Integer.toString(i));
				 ps2.setString(2,busid);
				 ps2.executeUpdate();
				}	
				response.sendRedirect("Addstation.jsp?add");
				
				}
	          }
			else
			{
				response.sendRedirect("adminhome.jsp?wrng");
			}
	          
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
