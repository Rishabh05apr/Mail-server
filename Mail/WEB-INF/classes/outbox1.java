import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class outbox1 extends HttpServlet implements SingleThreadModel
{
	String url;
	Connection con;
	Statement stmt;
	ResultSet rs;
	PrintWriter out;
	String check,user;
	RequestDispatcher rd;
	ServletContext context;		

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			context=getServletContext();
			HttpSession session=request.getSession(true);
			check=(String)session.getAttribute("check");
			if(check==null)
			{
				System.out.println("Error");
		 	      	response.sendRedirect("http://localhost:8080/Mail");	
			}
			user=(String)session.getAttribute("user");
			System.out.println("Session id="+session.getId());
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url="jdbc:mysql://localhost:3306/onlinemail?user=root&password=root";
			con=DriverManager.getConnection(url);
			response.setContentType("text/html");
      	  		out = response.getWriter();
			
			if((request.getParameter("deleteall")+"").equals("Delete All"))
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/outbox_deleteall");	
			}
			else if((request.getParameter("sendall")+"").equals("Send All"))
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/outbox_sendall");		
			}
			else if(request.getParameter("rb")==null)
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/outbox_select");	
			}
			else if((request.getParameter("content")+"").equals("Content"))
			{
				url="/servlet/outbox_content";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);	
			}
			else if((request.getParameter("delete")+"").equals("Delete"))
			{
				
				url="/servlet/outbox_delete";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);
			}
			else if((request.getParameter("send")+"").equals("Send"))
			{
				url="/servlet/outbox_send";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);		
			}
			
		}
		catch(Exception e)
		{}
	}
}