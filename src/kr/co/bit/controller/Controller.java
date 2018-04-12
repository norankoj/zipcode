package kr.co.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.bit.dao.MemberDAO;
import kr.co.bit.vo.MemberVO;

public class Controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(request, resp);
		// System.out.println("111");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");

		cmd = cmd == null ? "" : cmd; // ��..!
		System.out.println("working");
		String url = "./home.jsp";// �⺻��
		if (cmd.equals("viewRegist")) {

			url = "./regist_member.jsp";

		} else if (cmd.equals("regist")) {

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String zip1 = request.getParameter("zip1");
			String zip2 = request.getParameter("zip2");
			String addr1 = request.getParameter("addr1");
			String addr2 = request.getParameter("addr2");
			String tool = request.getParameter("tool");
			String project = request.getParameter("project");
			String[] langs = request.getParameterValues("lang");
			/*
			 * String[] temp = {"","","",""}; for(String index : langs){ int idx =
			 * Integer.parseInt(index); temp[idx] = index; }
			 */ // �ֳ� ���� �𵨿��� ó���ϰ� �ٲ�� �����Դϴ�.
			// MemberVO ������ Ŭ������ ���� �ν��Ͻ��� �ϳ� ����
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPw(pw);
			vo.setName(name);
			vo.setEmail(email);
			vo.setZipcode(zip1 + "-" + zip2);
			vo.setAddr1(addr1);
			vo.setAddr2(addr2);
			vo.setTool(tool);
			vo.setProject(project);
			vo.setLangs(langs);

			MemberDAO dao = new MemberDAO();
			boolean flag = dao.insert(vo);// �̰ŷ� ���� dao���� ���� ���
			// ��ĭ���� �ϰ� ȸ�������ϸ� ������������ �Ѿ�� �ϴµ�, �̰� �ֱ׷��ĸ� ���� �Ⱦ��� nulló���� �Ǽ� ������ �ǿ�
			// �ι�° ����ó�� ��� xml�� ���ϴ�. ���������� ������ �ȴ�.//�ٵ����� ����

			System.out.println(vo.getId());
			request.setAttribute("message", "success");

		} else if (cmd.equals("search")) {

			String id = request.getParameter("id");
			MemberDAO dao = new MemberDAO();
			MemberVO vo = dao.selectvo(id);
			url="./regist_member.jsp";
			//url = "./home.jsp";//�ٽ� Ȩ���� �������ؼ��� �⺻���� ������ url�̸� �Ƚᵵ �˴ϴ�. ���� ���� ����..
			request.setAttribute("vo", vo);

		} else if(cmd.equals("searchid")) {
			MemberVO vo =null;
			MemberDAO dao = new MemberDAO();
			vo=dao.selectvo(request.getParameter("id"));
			request.setAttribute("result",vo);
			url="./id_check.jsp";
			
		}else if(cmd.equals("viewpoint")) {
			url="./id_check.jsp";
		}else if (cmd.equals("viewidservice")) {
			//url="./id_check.jsp";
			//�۾��ϱ� ���� regist_member�� ���� �����۽��� ������ �������� ���� �����Բ� �ٲߴϴ�.
			//String json ="{\"user\":\"admin\",\"message\":\"success\"}";
			//�̷��� �Ϸ��� �ߴ��� �ٷ� �������� ������ ������ ���̵� ���񽺿��� �����ֱ�� �Ѵ�.
			url="./id_service.jsp";
		}
			
			
			/*//���̵� üũâ���� �޾ƿ� ���Ӻ�����
			String id = request.getParameter("id");
			//dao�� �ҷ��ɴϴ�
			MemberDAO dao = new MemberDAO();
			
			//dao�� ���̵� ���� �����ְ� ã������ �̹� �ִ� ���̵�, �� �ΰ��� �ƴϸ� �̹� �ִ� ���̵� 
			if(dao.search(id)!=null){
	
				request.setAttribute("message","�̹� ������� ���̵� �Դϴ�.");
				url="./id_check.jsp";		
			}else {			
				request.setAttribute("message", "�ÿ밡���մϴ�.");
				url="./id_check.jsp";
			}*/
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, resp);
	}
}
