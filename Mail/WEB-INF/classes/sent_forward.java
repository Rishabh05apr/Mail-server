import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class sent_forward extends HttpServlet implements SingleThreadModel
{
	String url;
	Connection con;
	Statement stmt;
	ResultSet rs;
	String check;
	int id;
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
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url="jdbc:mysql://localhost:3306/onlinemail?user=root&password=root";
			con=DriverManager.getConnection(url);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from sent");
			String s=(String)request.getParameter("rb");
			System.out.print(s);
			id=Integer.parseInt(s);
			while(rs.next())
			{
				if(rs.getInt("Sno")==id)
				{
					break;
				}
			}
			display(response);
		}
		catch(Exception e)
		{}
	}
	public void display(HttpServletResponse response)
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
				out.println("<input type='text' name='to' value=''></input>");
				out.println("</th>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<font size='4'>Subject</font>");
				out.println("</th>");
				out.println("<th>");
				out.println("<input type='text' name='subject' value="+rs.getString("Subject")+"></input>");
				out.println("</th>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<th colspan=2>");
				out.println("<textarea name='message' rows='10'>"+rs.getString("Message")+"</textarea>");
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
		out.println("</body>");
		out.println("</html>");
	}
	catch(Exception e)
	{}
	}
}