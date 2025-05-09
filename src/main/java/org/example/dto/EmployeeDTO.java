package org.example.dto;

public class EmployeeDTO {
    private String fullName;
    private String dept;
    private String yearlySalary;

    public EmployeeDTO(String fullName, String dept, String yearlySalary) {
        this.fullName = fullName;
        this.dept = dept;
        this.yearlySalary = yearlySalary;
    }

    public String getFullName() { return fullName; }
    public String getDept() { return dept; }
    public String getYearlySalary() { return yearlySalary; }
}
