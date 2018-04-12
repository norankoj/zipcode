<%@page import="kr.co.bit.vo.ZipcodeVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>요청</title>
<script type="text/javascript">
/*  function popup(){
	var val =document.getElementById("popup").value;
	alert(val);
	
}  */
  function use_zipcode(){
	var val = document.getElementById("popup").value;
	var temp = val.split(" "); //javascript에서는 배열을 이렇게 써두 된답니다  
	var zipcode = temp[0].split("-");
	var a= zipcode[0]; //이미 배열을 자르려고 하지마.. 후
	var b= zipcode[1]; 

	var address = val.substring(temp[0].length+1);//+1한이유 빈칸이있어서 빈칸다음부터 자르겠다.
	alert(a+"/"+b+"/"+address); //값을 제대러 받아왔나 확인 

	document.getElementById("zip1").value =a;
	document.getElementById("zip2").value =b;
	document.getElementById("addr1").value =address;
	
} 

function work_close(){
	//zip코드 값 가져오기
	
	var zip1 = document.getElementById("zip1").value;
	var zip2 = document.getElementById("zip2").value;
	var addr1 = document.getElementById("addr1").value;
	var addr2 = document.getElementById("addr2").value;
	
	opener.document.getElementById("userzip1").value = zip1;
	opener.document.getElementById("userzip2").value = zip2;
	opener.document.getElementById("useraddr1").value = addr1;
	opener.document.getElementById("useraddr2").value = addr2;
	
	self.close();
	return false;
}  
 
</script>
</head>
<body>
<%
String zip1 = request.getParameter("zip1");
String zip2 = request.getParameter("zip2");
String addr1 = request.getParameter("addr1");
String addr2 = request.getParameter("addr2");
%>
	<form action="./command?cmd=search" method="post">
	동검색
	  <input type="text" name="id">
	  <input type="submit" value="검색">
	</form>
	<%
		ArrayList<ZipcodeVO> list = (ArrayList<ZipcodeVO>)request.getAttribute("LIST");
		if (list == null) {
			list = new ArrayList<ZipcodeVO>();
		}
		StringBuffer sb = new StringBuffer("<select onchange='popup()' id='popup'><option>주소선택");
			for (ZipcodeVO vo :list) {
				sb.append("<option value='"+vo.getAddress()+"'onclick=use_zipcode()>");
				sb.append(vo.toString());
			}
			sb.append("</select>");
			out.print(sb.toString());
	%>
	
	<table>
	  <tr>
	  <td><input type="text" name="id" id="zip1" <%-- value="<%=zip1%>" --%> size="3">-
	      <input type="text" name="id" id="zip2"  size="3"></td></tr>
	  <tr>
	  <td><input type="text" name="id" id="addr1"></td>
	  </tr>
	  <tr>
	  <td><input type="text" name="id" id="addr2"></td>
	  </tr>
	  <tr><td></td></tr>
	
	</table>
		<button onclick="return work_close()">사용하기</button>
	
	
</body>
</html>