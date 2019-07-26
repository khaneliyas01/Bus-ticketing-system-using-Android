
<%@page import="com.util.DbConnection"%>
<%@ page import="java.io.*,java.sql.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
 
<%
 
            String sn=request.getParameter("busid");
            
 
					Connection con=DbConnection.getConnection();
                    Statement st=con.createStatement();
                    
                    //ResultSet rs = st.executeQuery("select * from emp where empno="+sn);
                    ResultSet rs = st.executeQuery("select * from buses where busid='"+sn+"'");  // this is for name
                    if(rs.next())
                    {    
                        out.println("<font color=red>");
                        out.println(" Already Present");
                        out.println("</font>");
 
                    }else {
 
                        out.println("<font color=green>");
                        out.println("Available");
                        out.println("</font>");
 
                    }
                    
                    
                    
                    
                    
                    
 
rs.close();
st.close();
con.close();
 
%>