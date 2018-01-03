import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class messagenull extends HttpServlet implements SingleThreadModel
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
			check=(String)session.getAttribute("check");
			if(check==null)
			{
				System.out.println("Error");
		 	      	response.sendRedirect("http://localhost:8080/Mail");	
			}
			System.out.println("Session id="+session.getId());
			
			display(response,request);
		}
		catch(Exception e)
		{}
	}
	public void display(HttpServletResponse response,HttpServletRequest request)
	{	try{
		response.setContentType("text/html");
      	  	PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<br/>");
		out.println("<br/>");
		out.println("<form method='post' action='compose_send'>");
		out.println("<table align='center' border='2' cellpadding='3' bordercolor='blue'>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<font size='4'>To</font>");
				out.println("</th>");
				out.println("<th>");
				out.println("<input type='text' name='to' value="+request.getParameter("to")+"></input>");
				out.println("</th>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<font size='4'>Subject</font>");
				out.println("</th>");
				out.println("<th>");
				out.println("<input type='text' name='subject' value="+request.getParameter("subject")+"></input>");
				out.println("</th>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<th colspan=2>");
				out.println("<textarea name='message' rows='10'>"+request.getParameter("message")+"</textarea>");
				out.println("</th>");
			out.println("</tr>");

		out.println("<table align='center' border='1' cellpadding='3' bordercolor='blue'>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<input type='submit' name='send' value='  Send  '/>");
				out.println("</th>");
				out.println("<th>");
				out.println("<input type='submit' name='save' value='  Save  '/>");
				out.println("</th>");
				out.println("<th>");
				out.println("<input type='submit' name='back' value='  Back  '/>");
				out.println("</th>");
			out.println("</tr>");
			out.println("</table>");

		out.println("</form>");
		out.println("</table>");
		out.println("<h1 align='center'>Please enter message<h1>");
		out.println("</body>");
		out.println("</html>");
	}
	catch(Exception e)
	{}
	}
}