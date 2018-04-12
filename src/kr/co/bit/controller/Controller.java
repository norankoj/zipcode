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

		cmd = cmd == null ? "" : cmd; // 와..!
		System.out.println("working");
		String url = "./home.jsp";// 기본값
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
			 */ // 왜냐 이제 모델에서 처리하게 바꿨기 때문입니다.
			// MemberVO 데이터 클래스를 만들어서 인스턴스를 하나 생성
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
			boolean flag = dao.insert(vo);// 이거로 이제 dao에서 삽입 명령
			// 빈칸으로 하고 회원가입하면 에러페이지로 넘어가게 하는데, 이게 왜그러냐면 값을 안쓰면 null처리가 되서 에러가 되용
			// 두번째 예외처리 방법 xml로 갑니다. 각오류마다 만들어야 된다.//근데안함 ㅋㅋ

			System.out.println(vo.getId());
			request.setAttribute("message", "success");

		} else if (cmd.equals("search")) {

			String id = request.getParameter("id");
			MemberDAO dao = new MemberDAO();
			MemberVO vo = dao.selectvo(id);
			url="./regist_member.jsp";
			//url = "./home.jsp";//다시 홈으로 가기위해서는 기본값과 동일한 url이면 안써도 됩니다. 괜히 쓰면 에러..
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
			//작업하기 전에 regist_member에 가서 아이작스로 보내는 페이지를 요기로 들어오게끔 바꿉니다.
			//String json ="{\"user\":\"admin\",\"message\":\"success\"}";
			//이렇게 하려고 했눈데 바로 쏴버리면 구조가 깨져서 아이디 서비스에서 보내주기로 한다.
			url="./id_service.jsp";
		}
			
			
			/*//아이디 체크창에서 받아온 네임벨류값
			String id = request.getParameter("id");
			//dao를 불러옵니다
			MemberDAO dao = new MemberDAO();
			
			//dao에 아이디 값을 보내주고 찾았을때 이미 있는 아이디, 즉 널값이 아니면 이미 있는 아이디 
			if(dao.search(id)!=null){
	
				request.setAttribute("message","이미 사용중인 아이디 입니다.");
				url="./id_check.jsp";		
			}else {			
				request.setAttribute("message", "시용가능합니다.");
				url="./id_check.jsp";
			}*/
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, resp);
	}
}
