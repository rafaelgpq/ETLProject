package org.example;

import java.util.List;

public class ETLRunner {
    public static void main(String[] args) {
        try {
            CreateDB.run();
            List<String[]> data = ExtractData.run();
            LoadToCSV.run(data);
            ZipCSV.run();
            SendEmail.run();
            System.out.println("✅ ETL Process Completed Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
