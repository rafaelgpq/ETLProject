package org.example.etl;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateDBTest {

    @Test
    void shouldCreateEmployeesTableAndInsertData() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE employees (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "department TEXT, " +
                "salary REAL)");

        PreparedStatement ps = conn.prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?)");
        Object[][] data = {
                {1, "Alice", "Engineering", 75000.0},
                {2, "Bob", "Marketing", 50000.0},
                {3, "Charlie", "HR", 60000.0}
        };

        for (Object[] row : data) {
            for (int i = 0; i < row.length; i++) {
                ps.setObject(i + 1, row[i]);
            }
            ps.executeUpdate();
        }

        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM employees");
        assertThat(rs.next()).isTrue();
        int rowCount = rs.getInt(1);
        assertThat(rowCount).isEqualTo(3);

        rs.close();;
        stmt.close();
        ps.close();
        conn.close();;
    }
}
