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
@WebServlet("/TransactionAmount")
public class TransactionAmount extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String tamt=req.getParameter("tm");
	int amt=Integer.parseInt(tamt);
	String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
	HttpSession session=req.getSession();
	String mob = (String) session.getAttribute("mbb");
	String pass = (String) session.getAttribute("pss");
	String tmob = (String) session.getAttribute("amob");
	Double famt = (Double) session.getAttribute("fat");
	Double samt = (Double) session.getAttribute("sat");
	resp.setContentType("text/html");
	PrintWriter writer = resp.getWriter();
	if (amt>0) {
		if (amt>0 && amt<=50000) {
		if (famt>amt) {
				famt-=amt;
				samt+=amt;
				String update="update account set amount=? where mobilenum=?";
				String update1="update account set amount=? where mobilenum=?";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement ps2=connection.prepareStatement(update);
					ps2.setDouble(1, famt);
					ps2.setString(2, mob);
					int result = ps2.executeUpdate();
					PreparedStatement ps3=connection.prepareStatement(update1);
					ps3.setDouble(1, samt);
					ps3.setString(2, tmob);
					int result1 =ps3.executeUpdate();
					if (result!=0 && result1!=0) {
						RequestDispatcher dispatcher=req.getRequestDispatcher("BankProject.html");
						dispatcher.include(req, resp);
						writer.println("<center><h1 style='margin-top:30px'>"+amt+" Transfered Successfully...</h1></center>");
					} else {
						RequestDispatcher dispatcher=req.getRequestDispatcher("TransactionAmount.html");
						dispatcher.include(req, resp);
						writer.println("<center><h1>Transaction failed</h1></center>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				RequestDispatcher dispatcher=req.getRequestDispatcher("TransactionAmount.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1>Insufficient Balance</h1></center>");
			}
		} else {
			RequestDispatcher dispatcher=req.getRequestDispatcher("TransactionAmount.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Trasfer amount is too big</h1></center>");
			writer.println("<center><h1>Please enter amount below 50000</h1></center>");
		}
	} else {
		RequestDispatcher dispatcher=req.getRequestDispatcher("TransactionAmount.html");
		dispatcher.include(req, resp);
		writer.println("<center><h1>Enter valid Amount</h1></center>");
	}
}
}
