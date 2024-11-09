package com.agrify.dl;

import io.github.cdimascio.dotenv.*;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DAOConnection
 */
public class DAOConnection {
	
	public static Connection getConnection() throws Exception {
		Connection connection = null;
		try {
			
			Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
			String DB_NAME = dotenv.get("DB_NAME");
			String DB_HOST = dotenv.get("DB_HOST");
			String DB_USER = dotenv.get("DB_USER");
			String DB_PASS = dotenv.get("DB_PASS");
			String DB_PORT = dotenv.get("DB_PORT");
			String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT +"/" + DB_NAME;
			
			// Old class do not use
			// Class.forName("com.mysql.jdbc.Driver");
			// New class, but class should now be loaded automatically although will still
			// produce warning
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			return connection;
		} catch (Exception e) {
			// System.out.println(e);
			throw new Exception(e.getMessage());
		}
	}
}