package kr.co.bit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.bit.dao.ZipcodeDAO;
import kr.co.bit.vo.ZipcodeVO;

public class CommandController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
		// System.out.println("in");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		cmd = cmd == null ? "" : cmd;
		String url = "./make_zipcode.jsp";
		if (cmd.equals("insert")) {
			String path = this.getServletContext().getRealPath("WEB-INF/file/zipcode.csv"); // application은
																							// servletcontext 타입이라고 합니다.
			ZipcodeDAO dao = new ZipcodeDAO();
			boolean flag = dao.insert(path);
			request.setAttribute("result", flag ? "true" : "false");
		} else if (cmd.equals("search")) {

			String dong = request.getParameter("id");
			//dong = new String (dong.getBytes("ISO-8859-1"),"UTF-8"); 동이 깨질때 
			ZipcodeDAO dao = new ZipcodeDAO();
			System.out.println(dong);
			ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
			list = dao.Search_dong(dong);

			request.setAttribute("LIST", list);
			System.out.println(list.size());
			//url = "./make_zipcode.jsp";
		} else if (cmd.equals("zip")) {
			//url="./regist_member.jsp";
			
		}

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, resp);

	}

}
