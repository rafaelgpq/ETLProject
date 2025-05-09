package org.example.etl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipCSVTest {

    private static final String CSV_FILE = "output.csv";
    private static final String ZIP_FILE = "output.zip";

    @BeforeEach
    void setUp() throws Exception {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("Name,Dept,Salary\n");
            writer.write("Alice,Engineering,75000.00\n");
        }
    }

    @AfterEach
    void cleanUp() {
        new File(CSV_FILE).delete();
        new File(ZIP_FILE).delete();
    }

    @Test
    void shouldZipCsvFileIntoOutputZip() throws Exception {
        ZipCSV.run();

        File zip = new File(ZIP_FILE);
        assertThat(zip).exists().isFile();

        try (
                FileInputStream fis = new FileInputStream(ZIP_FILE);
                ZipInputStream zis = new ZipInputStream(fis)
        ) {
            ZipEntry entry = zis.getNextEntry();
            assertThat(entry).isNotNull();
            assertThat(entry.getName()).isEqualTo(CSV_FILE);

            String content = new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8))
                    .lines()
                    .reduce("", (a, b) -> a + "\n" + b)
                    .trim();

            assertThat(content).contains("Alice,Engineering,75000.00");
        }
    }
}
