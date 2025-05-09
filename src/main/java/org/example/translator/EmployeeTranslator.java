package org.example.translator;

import org.example.dto.EmployeeDTO;
import org.example.entity.Employee;

public class EmployeeTranslator {
    public static EmployeeDTO toDTO(Employee emp) {
        if (emp == null) return null;
        return new EmployeeDTO(
                emp.getName(),
                emp.getDepartment(),
                String.format("%.2f", emp.getSalary())

        );
    }
}
