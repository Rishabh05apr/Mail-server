import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class sent_delete extends HttpServlet implements SingleThreadModel
{
	String url;
	Connection con;
	Statement stmt;
	ResultSet rs;
	PrintWriter out;
	String check,user;
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
			user=(String)session.getAttribute("user");
			System.out.println("Session id="+session.getId());
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url="jdbc:mysql://localhost:3306/onlinemail?user=root&password=root";
			con=DriverManager.getConnection(url);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from sent");
			response.setContentType("text/html");
      	  		out = response.getWriter();

			String s=(String)request.getParameter("rb");
			System.out.print(s);
			int id=Integer.parseInt(s);
			while(rs.next())
			{
				if(rs.getInt("Sno")==id)
				{
					break;
				}
			}
			String to=rs.getString("To");
			String subject=rs.getString("Subject");
			String message=rs.getString("Message");
			String date=rs.getString("Date");
			String username=rs.getString("Username");
			rs.deleteRow();

			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from deleted");
			rs.moveToInsertRow();
			rs.updateString("To",to);
			rs.updateString("Subject",subject);
			rs.updateString("Message",message);
			rs.updateString("Date",date);
			rs.updateString("Username",username);
			rs.updateString("Folder","s");
			rs.insertRow();
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from sent");
			
			out.println("<html>");
			out.println("<body>");
			out.println("<br/>");
			out.println("<br/>");
			out.println("<table align='right'>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<form method='post' action='logout'>");
				out.println("<input type='submit' name='logout' value='Logout'/>");
				out.println("</form>");
				out.println("</th>");
			out.println("</tr>");
			out.println("</table>");

			out.println("<h1 align='center'><font size='5'>Welcome </font>");
			out.println("<font size='5' color='green'>"+user+"</font><h1>");
			
			out.println("<table align='left' cellpadding='3'>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<pre>           </pre>");
				out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
		
			out.println("<table align='center' border='1' cellpadding='3' bordercolor='green'>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<form method='post' action='compose'>");
				out.println("<input type='submit' name='compose' value='Compose'/>");
				out.println("</form>");
				out.println("</th>");
				out.println("<th>");
				out.println("<form method='post' action='inbox'>");
				out.println("<input type='submit' name='inbox' value='Inbox'/>");
				out.println("</form>");
				out.println("</th>");
				out.println("<th>");
				out.println("<form method='post' action='outbox'>");
				out.println("<input type='submit' name='outbox' value='Outbox'/>");
				out.println("</form>");
				out.println("</th>");
				out.println("<th>");
				out.println("<form method='post' action='sent'>");
				out.println("<input type='submit' name='sent' value='Sent items'/>");
				out.println("</form>");
				out.println("</th>");
				out.println("<th>");
				out.println("<form method='post' action='drafts'>");
				out.println("<input type='submit' name='drafts' value='Drafts'/>");
				out.println("</form>");
				out.println("</th>");
				out.println("<th>");
				out.println("<form method='post' action='deleted'>");
				out.println("<input type='submit' name='deleted' value='Deleted'/>");
				out.println("</form>");
				out.println("</th>");	
			out.println("</tr>");
			out.println("</table>");
			
			out.println("<table align='left' border='2' cellpadding='3' bordercolor='blue'>");
				out.println("<form method='post' action='sent1'>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<input type='submit' name='forward' value='Forward'/>");
				out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<input type='submit' name='delete' value='Delete'/>");
				out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<input type='submit' name='deleteall' value='Delete All'/>");
				out.println("</td>");	
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<input type='submit' name='content' value='Content'/>");
				out.println("</td>");	
			out.println("</tr>");
			out.println("</table>");
		
			out.println("<table align='center' border='2' cellpadding='3' bordercolor='blue'>");
			out.println("<tr>");
				out.println("<th>");
				out.println("<font size='4'>To</font>");
				out.println("</th>");
				out.println("<th>");
				out.println("<font size='4'>Subject</font>");
				out.println("</th>");
				out.println("<th>");
				out.println("<font size='4'>Date</font>");
				out.println("</th>");
			out.println("</tr>");
			while(rs.next())
			{int sno=rs.getInt("Sno");
				if(user.equals(rs.getString("Username")))
				{
				out.println("<tr>");
				out.println("<td>");
					out.println("<input type=radio name=rb value="+sno+"></input>"+rs.getString("To"));
				out.println("</td>");
				out.println("<td>");
					out.println(""+rs.getString("Subject")+"");
				out.println("</td>");
				out.println("<td>");
					out.println(""+rs.getString("Date")+"");
				out.println("</td>");
				out.println("</tr>");
				}
			}
			out.println("</form>");
			out.println("</table>");
			out.println("<h1 align='center'><font size='5'>Deleted</font></h1>");
			out.println("</body>");
			out.println("</html>");

		}
		catch(Exception e)
		{}
	}
}