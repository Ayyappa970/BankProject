package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Credit")
public class Credit extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mob = req.getParameter("mb");
	String pass = req.getParameter("ps");
	PrintWriter writer=resp.getWriter();
		String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
		String select="select * from account where mobilenum=? and password=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(select);
			ps.setString(1, mob);
			ps.setString(2, pass);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				double damt=rs.getDouble(10);
				Random r=new Random();
				int otp=r.nextInt(10000);
				if (otp<1000) {
					otp+=1000;
				}
				HttpSession session=req.getSession();
				session.setAttribute("mb", mob);
				session.setAttribute("pas", pass);
				session.setAttribute("otp", otp);
				session.setAttribute("dat", damt);
				//session.setMaxInactiveInterval(10);
				RequestDispatcher dispatcher = req.getRequestDispatcher("OtpCredit.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1>Your otp : "+otp+"</h1></center>");
			} else {
				RequestDispatcher dispatcher=req.getRequestDispatcher("Credit.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1>Enter Valid details</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
