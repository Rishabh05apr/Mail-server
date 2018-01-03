import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Auth1 extends HttpServlet implements SingleThreadModel
{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			response.setContentType("text/html");
      	  		PrintWriter out = response.getWriter();
			
			out.println("<html>");	
				out.println("<body>");
				out.println("<form action='Auth' method='post' align='center'>");
				out.println("<font size='7'> Authentication </font>");
				out.println("<br/>");
				out.println("<font size='5'>");
				out.println("Username:");
				out.println("<input type='text' name='username'/>");
				out.println("<br/>");
				out.println("Password:");
				out.println("<input type='password'  name='password'/>");
				out.println("<br/>");
				out.println("<input type='submit' name='login' value='login'/>");
				out.println("<input type='reset' name='clear' value='clear'/>");
				out.println("</font>");
				out.println("</form>");
				out.println("<h1 align='center' size='4'>Please check username and password<h1>");
				out.println("</body>");
			out.println("</html>");
			
		}
		catch(Exception e)
		{}

	}
}