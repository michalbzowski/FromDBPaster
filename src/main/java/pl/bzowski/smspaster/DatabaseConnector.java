/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mbzowski
 */
class DatabaseConnector {

	private final PropertiesReader properties;
	private String url;
	private Connection conn;

	DatabaseConnector(final PropertiesReader properties) {
		this.properties = properties;
		this.url = "jdbc:oracle:thin:@" + properties.getConnectionAdress();
	}

	void connect() throws SQLException {
		this.url = "jdbc:oracle:thin:@" + properties.getConnectionAdress();
		this.conn = DriverManager.getConnection(url, properties.getDatabaseUser(), properties.getDatabaseUserPassword());
	}

	String getCode() throws SQLException {
		final String sql = properties.getSelectStatement();
		final PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, properties.getUserAlias());
		final ResultSet rs;

		rs = stmt.executeQuery();
		String code = "";
		if (rs.next()) {
			code = rs.getString(properties.getSmsCodeColumnAlias());
		}
		System.out.println(code);
		return code;
	}

	void disconnect() throws SQLException {
		conn.close();
	}

}
