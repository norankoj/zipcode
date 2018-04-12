<%@page import="kr.co.bit.vo.ZipcodeVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="./command?cmd=search" method="post">
	
	동검색<input type="text" name="id" > 
		<input type="submit" value="검색"> 
		</form>
	

	<%
		ArrayList<ZipcodeVO> list = (ArrayList<ZipcodeVO>)request.getAttribute("LIST");
		if (list == null) {
			list = new ArrayList<ZipcodeVO>();
		}
		StringBuffer sb = new StringBuffer("<select onchange='popup()' id='popup'><option>주소선택");
			for (ZipcodeVO vo :list) {
				sb.append("<option value='"+vo.getAddress()+"'>");
				sb.append(vo.toString());
			}
			sb.append("</select>");
			out.print(sb.toString());
		
	%>
</body>
</html>