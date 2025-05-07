package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExtractData {
    public static List<String[]> run() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:etl.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

        List<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            rows.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("name"),
                    rs.getString("department"),
                    String.valueOf(rs.getDouble("salary"))
            });
        }

        rs.close();
        stmt.close();
        conn.close();
        return rows;
    }
}
