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
		//�����Թ��
//		String sql = "select * from ZIPCODE_TBL where dong like ?";
//		PreparedStatement pstmt; //������ ���������� ���߿� �����ϵ������� ������....? 
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

	private ZipcodeVO getInstance(String line) { // �Ʒ����� ���� �ޱ� 
		// �� ���� �о �м��ؼ� vo�ν��Ͻ� ���� (-,�� �и�)
		//4,5,6�� ����� �� �� �־ ���� ��ó���� �Ǿߵȴ�. ��������ڷ� �� ĭ ����ָ� true�� �˴ϴ�. 
		
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

	public boolean insert(String path) {// Zipcodevo���� �ٲ��. ������ �ҷ�������
		boolean flag = false;
		 list= new ArrayList<ZipcodeVO>();
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		// ���� �����ؼ� ������ �о�� 5���� ������ �Է��Ѵ�.
		// file ��ü����
		File file = new File(path);
		
		// ��Ʈ������

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine())!= null) {
				// �� ���� �о
				System.out.println(line);
				//����
				
				// ����Ʈ������
				list.add(this.getInstance(line));
			
			
			}
			br.close();
			fr.close();
			//flag=true; �׽�Ʈ��
			
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
			//��Ű���� �����ϰ� ����ߵȴ� ���� �ƴ϶�� zipcode_tbl�ڿ� ������� ����־�ߵȴ� �ٵ� ��Ű�� ������ �����ϴ°� ���� ���մϴ�.
			/*if(affectedCount>0) {
			flag=true;
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ����Ʈ�� ������ �Ѳ����� �Է�
	
			
		
		
		mgr.connectClose(con, pstmt, null);
		return flag;
	}
}
