package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LoadToCSV {
    public static void run(List<String[]> data) throws IOException {
        FileWriter writer = new FileWriter("output.csv");
        writer.append("ID,Name,Department,Salary\n");
        for (String[] row: data) {
            writer.append(String.join(",", row)).append("\n");
        }
        writer.flush();
        writer.close();
        System.out.println("✅ Data written to output.csv.");
    }
}
