package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.SocketIO;

@WebServlet("/SelectModel")
public class SelectModel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3528952586566638853L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SocketIO s = new SocketIO("localhost", 4444);
		ArrayList<String> list = s.getAutomobileList();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/SelectModel.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}