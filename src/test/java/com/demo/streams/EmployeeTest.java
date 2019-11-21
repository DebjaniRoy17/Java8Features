package com.demo.streams;

public class EmployeeTest {
	
	private int empId;
	public int getEmpId() {
		return empId;
	}
	public EmployeeTest(int empId, String empName, double empSalary) {
	
		this.empId = empId;
		this.empName = empName;
		this.empSalary = empSalary;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public double getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}
	private String empName;
	private double empSalary; 

}
