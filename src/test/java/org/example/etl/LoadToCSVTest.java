package org.example.etl;

import org.example.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class LoadToCSVTest {

    private static final String OUTPUT_FILE = "output.csv";

    @AfterEach
    void cleanUp() {
        new File(OUTPUT_FILE).delete();
    }

    @Test
    void shouldWriteEmployeesAsFormattedCSVLines() throws Exception {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "Engineering", 75000),
                new Employee(2, "Bob", "Marketing", 65000)
        );

        LoadToCSV.run(employees);

        List<List<String>> rows = readCsvDataLines();

        assertThat(rows)
                .extracting(
                        row -> row.get(0),
                        row -> row.get(1),
                        row -> row.get(2)
                )
                .containsExactlyInAnyOrder(
                    tuple("Alice", "Engineering", "75000.00"),
                    tuple("Bob", "Marketing", "65000.00")
                );
    }

    private List<List<String>> readCsvDataLines() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(",")))
                    .collect(Collectors.toList());
        }
    }
}
