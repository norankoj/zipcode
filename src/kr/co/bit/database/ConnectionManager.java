package kr.co.bit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	
	
	
	public Connection getConnection() {
		Connection con= null;
		
		try {
			InitialContext initCtx = new InitialContext();
			//naming pool?�� Ŀ�ؼ� Ǯ�� �־ �װ� ����ϱ����� ���� ���̹� Ǯ�� ã�ƿ�
			Context ctx =(Context)initCtx.lookup("java:comp/env/");
			//ã������ ���� jdbc���ִ� oracle�ȿ� Ŀ�ؼ�Ǯ�� ����
			DataSource ds = (DataSource)ctx.lookup("jdbc/oracle");
			//Ŀ�ؼ��� ���� 
			con =ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	
	
	/*public Connection getConnection() {
		Connection con = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String driver = "oracle.jdbc.OracleDriver";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "hr", "1234");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}*/
	
	public void connectClose(Connection con,Statement stmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
