package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String mob = req.getParameter("mb");
	String pass = req.getParameter("ps");
	resp.setContentType("text/html");
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
			double amt = rs.getDouble(10);
			RequestDispatcher dispatcher=req.getRequestDispatcher("BankProject.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1 style='margin-top:150px'>Available balance : "+amt+" Rs</h1></center>");
		} else {
			RequestDispatcher dispatcher=req.getRequestDispatcher("CheckBalance.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Enter Valid details</h1></center>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
