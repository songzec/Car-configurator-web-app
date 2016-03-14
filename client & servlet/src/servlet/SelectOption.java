package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.SocketIO;
import model.Automobile;

/**
 * Servlet implementation class SelectOption
 */
@WebServlet("/SelectOption")
public class SelectOption extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312783342069749368L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SocketIO s = new SocketIO("localhost", 4444);
		String modelName = request.getParameter("modelName");
		if(modelName == null) {
			System.out.println("Nothing to be configured!");
			return;
		}
		Automobile auto = s.getAutomobileObject(modelName);
		
		ArrayList<String> optionSetList = auto.getOpsetsName();
		request.setAttribute("auto", auto);
		request.setAttribute("optionSetList", optionSetList);
		request.getRequestDispatcher("/SelectOption.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SocketIO s = new SocketIO("localhost", 4444);
		String modelName = request.getParameter("modelName");
		if (modelName == null) {
			System.out.println("Nothing to be configured..");
			return;
		}
		Automobile auto = s.getAutomobileObject(modelName);
		ArrayList<String> optionSetList = auto.getOpsetsName();
		for(String optionSet : optionSetList) {
			String choice = request.getParameter(optionSet);
			auto.setOptionChoice(optionSet, choice);
		}

		request.setAttribute("auto", auto);
		request.getRequestDispatcher("/DisplayResult.jsp").forward(request, response);
	}
}