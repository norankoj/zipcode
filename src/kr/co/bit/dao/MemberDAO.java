package kr.co.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import kr.co.bit.database.ConnectionManager;
import kr.co.bit.vo.MemberVO;

public class MemberDAO {
	public MemberVO selectvo(String id) {
		MemberVO vo = null;
		//조회
		ConnectionManager cm = new ConnectionManager();
		Connection con = cm.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs  =null;
		String sql ="select * from Member_tbr where user_id= ?";
		try {
			stmt =con.prepareStatement(sql);
			stmt.setString(1, id);
			rs= stmt.executeQuery();
			//아이디만 필요한데 그냥 다 해봤따
			while (rs.next()) {
				vo= new MemberVO();
				vo.setId(rs.getString(1));
				vo.setPw(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setEmail(rs.getString(4));
				vo.setZipcode(rs.getString(5));
				vo.setAddr1(rs.getString(6));
				vo.setAddr2(rs.getString(7));
				vo.setTool(rs.getString(8));
                
				String temp = rs.getString(9);
				String []langs= temp.split("-");
				String []vals= {"","","",""};
				for(String point: langs) {
					int index = Integer.parseInt(point);
				    vals[index]=point;
				}
                     vo.setLangs(vals);
				vo.setProject(rs.getString(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( vo == null) {
			vo = new MemberVO();
			vo.setId(id);
		}
	
			
			cm.connectClose(con, stmt, rs);
	
		return vo;
	}
	
	
	public boolean insert(MemberVO vo) { //즉시처리  트라이 케치, finally..?
		
		boolean flag=false;
		ConnectionManager cm = new ConnectionManager();
		Connection con= cm.getConnection();
		String sql = "insert into member_tbr values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt=null;//매번 컴파일 할것인가 안할것인가애 따라 바뀐다.그래서 매번 준비되어있다는 뜻 prpared q붙님
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getId());
			stmt.setString(2, vo.getPw());
			stmt.setString(3, vo.getName());
			stmt.setString(4, vo.getEmail());
			stmt.setString(5, vo.getZipcode());
			stmt.setString(6, vo.getAddr1());
			stmt.setString(7, vo.getAddr2());
			stmt.setString(8, vo.getTool());
			String [] temp =vo.getLangs();
			StringBuffer sb = new StringBuffer(temp[0]); //temp[0]으로 시작하는 이유; 값이 있다는 말
			for(int i=1;i<temp.length;i++) {
				//-구분자로 해서 구현//다중값을 쓰면 안되기때문에 그래서 배열대신 "-"를 집어넣어 하나의 값 처럼 처리한다.
			sb.append("-"+temp[i]);
			}
			stmt.setString(9, sb.toString());
			stmt.setString(10, vo.getProject());
			int affectedCount = stmt.executeUpdate();// 몇개 테이블이 영향받았는지 // 위에 보냈기떄문에 안보낸다. 에러난다. 
			if(affectedCount>0) { //affectedCount레코드 변경될때마다 올라가는건데 결국 거의1 근데 안나올때도 있다.
				flag=true; //성공시 true 로!
				System.out.println("삽입완료");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			
			cm.connectClose(con, stmt, null);//rs없다고 지우면 에러나요 갯수3개니까 구래서 null 넣으면 되지 
			//statement를 preparestatememt로 바꿔도 클로즈 에러 안나는 이유는 다형성. 더 큰 클래스 이기떄문입니다.
		}
		return flag;
	}
	
	
	public MemberVO search(String id)  {
		
		Statement stmt=null;
		MemberVO dao = null;
		
		ConnectionManager cm = new ConnectionManager();
		Connection con= cm.getConnection();
		
		String sql = "select * from Member_tbr where user_id='"+id+"'";
		
		try {
			stmt= con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				dao = new MemberVO();
				dao.setName(rs.getString(1));
				dao.setPw(rs.getString(2));
				dao.setName(rs.getString(3));
				dao.setEmail(rs.getString(4));
				dao.setZipcode(rs.getString(5));
				dao.setAddr1(rs.getString(6));
				dao.setAddr2(rs.getString(7));
				dao.setTool(rs.getString(8));
				String temp = rs.getString(9);
				
				
				String []langs= temp.split("-");
				String []vals= {"","","",""};
				for(String point: langs) {
					int index = Integer.parseInt(point);
				    vals[index]=point;
				}
			//	String []lang = vals;
                     dao.setLangs(vals);
				
				dao.setProject(rs.getString(10));
		
				//배열로 변환하는 코드 작성 
				
				/*String temp1 = rs.getString(1);
				String temp2 = rs.getString(2);
				String temp3 = rs.getString(3);
				String temp4 = rs.getString(4);
				String temp5 = rs.getString(5);
				String temp6 = rs.getString(6);
				String temp7 = rs.getString(7);
				String temp8 = rs.getString(8);
				String temp9 = rs.getString(9);
				String temp10 = rs.getString(10);*/
			//System.out.println(temp1+","+temp2+","+temp3+","+temp4+","+temp5+","+temp6+","+temp7+","+temp8+","+lang+","+temp10);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			cm.connectClose(con, stmt, null);
		}
	
		return dao;
	
	}
	
	
	//
	public ArrayList<MemberVO> Select(){
		ArrayList<MemberVO> list = null;
		ConnectionManager cm = new ConnectionManager();
		Connection con= cm.getConnection();
		
		Statement stmt;
		String sql = "select * from member_tbr";
		try {
			stmt= con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = new ArrayList<MemberVO>();
			MemberVO dao = null;
			while(rs.next()) {
				dao = new MemberVO();
				dao.setName(rs.getString(1));
				dao.setPw(rs.getString(2));
				dao.setName(rs.getString(3));
				dao.setEmail(rs.getString(4));
				dao.setZipcode(rs.getString(5));
				dao.setAddr1(rs.getString(6));
				dao.setAddr2(rs.getString(7));
				dao.setTool(rs.getString(8));
				String temp = rs.getString(9);
				//배열로 변환하는 코드 작성 
			
				String []temp1= temp.split("-");
				String []temp2= {"","","",""};
				for(String point: temp1) {
					int index = Integer.parseInt(point);
				    temp2[index]=point;
				}
				dao.setLangs(temp2);
				
				dao.setProject(rs.getString(10));
	
			}
			cm.connectClose(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	

}
