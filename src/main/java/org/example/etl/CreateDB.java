package org.example.etl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CreateDB {
    public static void run() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:etl.db");
        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE IF EXISTS employees");
        stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "department TEXT, " +
                "salary REAL)");
        stmt.execute("DELETE from employees");

        PreparedStatement ps = conn.prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?)");
        Object[][] data = {
                {1, "Alice", "Engineering", 75000.0},
                {2, "Bob", "Marketing", 50000.0},
                {3, "Charlie", "HR", 60000.0}
        };

        for (Object[] row: data) {
            for (int i = 0; i < row.length; i++) {
                ps.setObject(i + 1, row[i]);
            }
            ps.executeUpdate();
        }

        ps.close();
        stmt.close();
        conn.close();
        System.out.println("✅ Database created and data inserted.");
    }
}
