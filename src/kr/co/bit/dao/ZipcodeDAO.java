package kr.co.bit.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import kr.co.bit.database.ConnectionManager;
import kr.co.bit.vo.ZipcodeVO;

public class ZipcodeDAO {
	
	ArrayList<ZipcodeVO> list = null; 
	
	public ArrayList<ZipcodeVO> Search_dong(String dong) {
		
		Statement stmt=null;
		ResultSet rs= null;
		ZipcodeVO vo = null;
		
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		
		String sql = "select * from ZIPCODE_TBL where dong like'"+dong+"'";
		//선생님방식
//		String sql = "select * from ZIPCODE_TBL where dong like ?";
//		PreparedStatement pstmt; //쿼리만 먼저보내고 나중에 컴파일된파일을 보내고....? 
//		pstmt = con.prepareStatement(sql);
//		pstmt.setString(1, "%"+dong+"%");
//		ResultSet rs = pstmt.executeQuery();
		
		list = new ArrayList<ZipcodeVO>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				vo = new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setRi(rs.getString(5));
				vo.setBldg(rs.getString(6));
				vo.setBunji(rs.getString(7));
				vo.setSeq(rs.getString(8));
				
				list.add(vo);
			}
			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		mgr.connectClose(con, stmt, rs);
		}
		
		return list;
		
	}

	private ZipcodeVO getInstance(String line) { // 아래에서 라인 받기 
		// 한 라인 읽어서 분석해서 vo인스턴스 생성 (-,로 분리)
		//4,5,6번 비었을 수 도 있어서 비우면 널처리가 되야된다. 사망연산자로 한 칸 띄어주면 true가 됩니다. 
		
        String[] temp =line.split(","); 
        ZipcodeVO vo = new ZipcodeVO();

        vo.setZipcode(temp[0]); 
        vo.setSido(temp[1]); 
        vo.setGugun(temp[2].equals("")?" ":temp[2]); 
        vo.setDong(temp[3]);
        vo.setRi(temp[4].equals("")?" ":temp[4]); 
        vo.setBldg(temp[5].equals("")?" ":temp[5]); 
        vo.setBunji(temp[6].equals("")?" ":temp[6]); 
        vo.setSeq(temp[7]);
        		

		
		return vo;
	}

	public boolean insert(String path) {// Zipcodevo에서 바꿨다. 파일을 불러오려고
		boolean flag = false;
		 list= new ArrayList<ZipcodeVO>();
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		// 파일 접속해서 내용을 읽어와 5만개 정도를 입력한다.
		// file 객체생성
		File file = new File(path);
		
		// 스트림생성

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine())!= null) {
				// 한 라인 읽어서
				System.out.println(line);
				//전달
				
				// 리스트에저장
				list.add(this.getInstance(line));
			
			
			}
			br.close();
			fr.close();
			//flag=true; 테스트용
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());
		String sql="insert into zipcode_tbl values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try {
			pstmt =con.prepareStatement(sql);
//			ZipcodeVO vo = list.get(0);
			for(ZipcodeVO vo : list) {
			pstmt.setString(1, vo.getZipcode());
			pstmt.setString(2, vo.getSido());
			pstmt.setString(3, vo.getGugun());
			pstmt.setString(4, vo.getDong());
			pstmt.setString(5, vo.getRi());
			pstmt.setString(6, vo.getBldg());
			pstmt.setString(7, vo.getBunji());
			pstmt.setString(8, vo.getSeq());
			
			int affectedCount = pstmt.executeUpdate();
			}
			//스키마와 동일하게 적어야된다 만약 아니라면 zipcode_tbl뒤에 순서대로 집어넣어야된다 근데 스키마 순서에 맞춰하는게 제일 편합니다.
			/*if(affectedCount>0) {
			flag=true;
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 리스트를 가지고 한꺼번에 입력
	
			
		
		
		mgr.connectClose(con, pstmt, null);
		return flag;
	}
}
