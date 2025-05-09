package org.example.etl;

import org.example.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class ExtractDataTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        conn = DriverManager.getConnection("jdbc:sqlite::memory");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "department TEXT, " +
                "salary REAL)");
        stmt.execute("DELETE FROM employees");

        stmt.execute("INSERT INTO employees VALUES (1, 'Alice', 'Engineering', 75000)");
        stmt.execute("INSERT INTO employees VALUES (2, 'Bob', 'Marketing', 65000)");
        stmt.close();
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.close();
    }

    @Test
    void shouldExtractEmployeesCorrectly() throws Exception {
        List<Employee> employees = ExtractData.run(conn);

        assertThat(employees)
                .hasSize(2)
                .extracting(Employee::getName, Employee::getDepartment, Employee::getSalary)
                .containsExactlyInAnyOrder(
                        tuple("Alice", "Engineering", 75000.0),
                        tuple("Bob", "Marketing", 65000.0)
                );
    }
}
