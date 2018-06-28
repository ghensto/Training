/**
 * 
 */
package com.gcit.lms.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionUtil {

	public Connection getConnection() throws ClassNotFoundException, SQLException {

		Properties prop = new Properties();
		InputStream input = null;
		Connection conn = null;
		try {
			input = new FileInputStream("mysql.properties");
			prop.load(input);
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			conn.setAutoCommit(Boolean.FALSE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conn;
	}
}
