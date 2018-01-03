import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class logout extends HttpServlet implements SingleThreadModel
{
	String check;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			HttpSession session=request.getSession(true);
			System.out.println("Session id="+session.getId());
			session.invalidate();
			response.sendRedirect("http://localhost:8080/Mail");	
		}
		catch(Exception e)
		{}

	}
}