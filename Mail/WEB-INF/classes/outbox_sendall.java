import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class outbox_sendall extends HttpServlet implements SingleThreadModel
{
	String url;
	Connection con;
	Statement stmt,stmt1,stmt2;
	ResultSet rs,rs1,rs2;
	PrintWriter out;
	String check,user;
	String to,subject,date,username,message;
	boolean flag,flag1;

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
			rs=stmt.executeQuery("Select * from outbox");

			stmt1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs1=stmt1.executeQuery("Select * from authentication");

			stmt2=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs2=stmt2.executeQuery("Select * from sent");

			response.setContentType("text/html");
      	  		out = response.getWriter();

			flag1=false;

			while(rs.next())
			{
				if(user.equals(rs.getString("Username")))
				{
					to=rs.getString("To");
			        	subject=rs.getString("Subject");
					message=rs.getString("Message");
					date=rs.getString("Date");
					username=rs.getString("Username");
					flag=false;
					rs1.beforeFirst();
					while(rs1.next())
					{
						if(to.equals(rs1.getString("Username")))
						{
							flag=true;
							flag1=true;
						}
					}
					if(flag==true)
					{
		
						rs2.moveToInsertRow();
						rs2.updateString("To",to);
						rs2.updateString("Subject",subject);
						rs2.updateString("Message",message);
						rs2.updateString("Date",date);
						rs2.updateString("Username",username);
						rs2.insertRow();
					}
				}

			}			
			
			rs.afterLast();
			while(rs.previous())
	    		{	
				if(user.equals(rs.getString("Username")))
				{
					to=rs.getString("To");
	    				rs1.beforeFirst();
					flag=false;
					while(rs1.next())
					{
						if(to.equals(rs1.getString("Username")))
						{
							flag=true;
						}
					}
					if(flag==true)
					{
						rs.deleteRow();
					}
				}
	    		}
			

			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from outbox");
			
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
			out.println("<tr>");
				out.println("<td>");
				out.println("<form method='post' action='outbox1'>");
				out.println("<input type='submit' name='send' value='Send'/>");
				out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
				out.println("<td>");
				out.println("<input type='submit' name='sendall' value='Send All'/>");
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
			if(flag1==true)
			out.println("<h1 align='center'><font size='5'>Mails sent</font></h1>");
			else
			out.println("<h1 align='center'><font size='5'>Mail cannot be sent</font></h1>");
			out.println("</body>");
			out.println("</html>");

		}
		catch(Exception e)
		{
			System.out.print("outbox send all"+e.getMessage());
		}
	}
}