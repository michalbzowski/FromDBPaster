/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mbzowski
 */
class DatabaseConnector {

    private final PropertiesReader properties;
    private String url;
    private Connection conn;

    DatabaseConnector(PropertiesReader properties) {
        this.properties = properties;
        this.url = "jdbc:oracle:thin:@" + properties.getConnectionAdress();
    }

    public void connect() throws SQLException {
        this.url = "jdbc:oracle:thin:@" + properties.getConnectionAdress();
        this.conn = DriverManager.getConnection(url, properties.getDatabaseUser(), properties.getDatabaseUserPassword());
    }

    public String getCode() throws SQLException {
        String sql = "select acd_code from ( ze_wzgledu_bezpieczenstwa_wpisz_swoj_select ) where rownum = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, properties.getUserAlias());
        ResultSet rs;

        rs = stmt.executeQuery();
        String code = "";
        if (rs.next()) {
            code = rs.getString("acd_code");
        }
        System.out.println(code);
        return code;
    }

    public void disconnect() throws SQLException {
        conn.close();
    }

}
