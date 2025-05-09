package org.example.etl;

import org.example.entity.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExtractData {
    public static List<Employee> run(Connection conn) throws Exception {
        List<Employee> employees = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                ));
            }
        }
        return employees;
    }

    public static List<Employee> run() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:etl.db")) {
            return run(conn);
        }
    }
}
