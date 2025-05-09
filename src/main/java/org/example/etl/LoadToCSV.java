package org.example.etl;

import org.example.dto.EmployeeDTO;
import org.example.entity.Employee;
import org.example.translator.EmployeeTranslator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LoadToCSV {
    public static void run(List<Employee> employees) throws IOException {
        FileWriter writer = new FileWriter("output.csv");
        writer.append("ID,Name,Department,Salary\n");

        for (Employee emp: employees) {
            EmployeeDTO dto = EmployeeTranslator.toDTO(emp);
            writer.append(String.format("%s,%s,%s\n",
                    dto.getFullName(),
                    dto.getDept(),
                    dto.getYearlySalary()
            ));
        }

        writer.flush();
        writer.close();
        System.out.println("✅ Data written to output.csv.");
    }
}
