package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/OtpCredit")
public class OtpCredit extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String eotp=req.getParameter("o");
	int otp1=Integer.parseInt(eotp);
	
	HttpSession session = req.getSession();
	Integer otp = (Integer) session.getAttribute("otp");
	resp.setContentType("text/html");
	PrintWriter writer = resp.getWriter();
	if (otp!=null) {
		if (otp==otp1) {
			RequestDispatcher dispatcher=req.getRequestDispatcher("AmountCredit.html");
			dispatcher.include(req, resp);
		} else {
			RequestDispatcher dispatcher=req.getRequestDispatcher("OtpCredit.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Invalid Otp</h1></center>");
		}
	} else {
		writer.println("<center><h1>Session  time out</h1></center>");
	}
}
}
