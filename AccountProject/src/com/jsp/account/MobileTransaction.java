package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/MobileTransaction")
public class MobileTransaction extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mob = req.getParameter("mb");
	String pass = req.getParameter("ps");
	PrintWriter writer=resp.getWriter();
	String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
	String select="select * from account where mobilenum=? and password=?";
	resp.setContentType("text/html");
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(select);
		ps.setString(1, mob);
		ps.setString(2, pass);
		ResultSet rs=ps.executeQuery();
		if (rs.next()) {
			double famt=rs.getDouble(10);
			RequestDispatcher dispatcher=req.getRequestDispatcher("Transaction.html");
			dispatcher.include(req, resp);
			HttpSession session = req.getSession();
			session.setAttribute("mbb", mob);
			session.setAttribute("pss", pass);
			session.setAttribute("fat", famt);
		} else {
			RequestDispatcher dispatcher=req.getRequestDispatcher("MobileTransaction.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Enter Valid Details</h1></center>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
