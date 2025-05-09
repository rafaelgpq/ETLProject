package org.example;

import org.example.entity.Employee;
import org.example.etl.CreateDB;
import org.example.etl.ExtractData;
import org.example.etl.LoadToCSV;
import org.example.etl.ZipCSV;
import org.example.mail.SendEmail;

import java.util.List;

public class ETLRunner {
    public static void main(String[] args) {
        try {
            CreateDB.run();
            List<Employee> employees = ExtractData.run();
            LoadToCSV.run(employees);
            ZipCSV.run();
            SendEmail.run();
            System.out.println("✅ ETL Process Completed Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
