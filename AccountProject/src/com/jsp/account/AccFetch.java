package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/AccFetch")
public class AccFetch extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String accNum=req.getParameter("an");
	String mob=req.getParameter("mn");
	String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
	String select="select * from account where accnum=? and mobilenum=?";
	PrintWriter writer=resp.getWriter();
	resp.setContentType("text/html");
	if (accNum.length()==10 && mob.length()==10) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(select);
			ps.setString(1, accNum);
			ps.setString(2, mob);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				String faname = rs.getString(4);
				String pnum = rs.getString(6);
				String anum = rs.getString(7);
				String add = rs.getString(8);
				String gen = rs.getString(9);
				double am = rs.getDouble(10);
				Random r=new Random();
				int otp=r.nextInt(10000);
				if (otp<1000) {
					otp+=1000;
				}
				HttpSession session=req.getSession();
				session.setAttribute("otp", otp);
				session.setAttribute("ac", accNum);
				session.setAttribute("fn", fname);
				session.setAttribute("ln", lname);
				session.setAttribute("fna", faname);
				session.setAttribute("pn", pnum);
				session.setAttribute("an", anum);
				session.setAttribute("ad", add);
				session.setAttribute("mb", mob);
				session.setAttribute("g", gen);
				session.setAttribute("ammt", am);
				RequestDispatcher dispatcher=req.getRequestDispatcher("Otp1.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2>Your Otp : "+otp+"</h2></center>");
			} else {
				RequestDispatcher dispatcher=req.getRequestDispatcher("AccFetch.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2>No Data Found....</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else {
		RequestDispatcher dispatcher=req.getRequestDispatcher("AccFetch.html");
		dispatcher.include(req, resp);
		writer.println("<center><h2>Enter valid Details</h2></center>");
	}
}
}
