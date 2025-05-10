package org.example.translator;

import org.example.dto.EmployeeDTO;
import org.example.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTranslatorTest {

    @Test
    void shouldTranslateEmployeeToDTO() {
        Employee emp = new Employee(1, "Alice", "Engineering", 75000.0);

        EmployeeDTO dto = EmployeeTranslator.toDTO(emp);

        assertThat(dto).isNotNull();
        assertThat(dto.getFullName()).isEqualTo("Alice");
        assertThat(dto.getDept()).isEqualTo("Engineering");
        assertThat(dto.getYearlySalary()).isEqualTo("75000.00");
    }

    @Test
    void shouldReturnNullIfInputIsNull() {
        EmployeeDTO dto = EmployeeTranslator.toDTO(null);
        assertThat(dto).isNull();
    }
}
