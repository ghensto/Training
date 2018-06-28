/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Abiola
 *
 */
public abstract class BaseDAO<T>{
	
	public static Connection conn = null;
	
	@SuppressWarnings("static-access")
	public BaseDAO(Connection connection){
		this.conn = connection;
	}

	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate();
	}
	
	public Integer saveWithID(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while(rs.next()){
			return rs.getInt(1);//check if this is 0 or 1.
		}
		
		return null;
	}
	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count = 1;
		if (vals != null) {
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		
		
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	public abstract List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException;
	
	public List<T> readFirstLevel(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}
	
	public abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException;
}
