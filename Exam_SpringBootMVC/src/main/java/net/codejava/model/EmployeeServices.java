package net.codejava.model;

public class EmployeeServices {
    private int empServiceId;
    private int employeeId; 
    private int serviceId; 
    private String details;

  
    public EmployeeServices() {
		super();
	}
	public EmployeeServices(int empServiceId, int employeeId, int serviceId, String details) {
		super();
		this.empServiceId = empServiceId;
		this.employeeId = employeeId;
		this.serviceId = serviceId;
		this.details = details;
	}
	public int getEmpServiceId() {
    	return empServiceId; 
    	}
    public void setEmpServiceId(int empServiceId) {
    	this.empServiceId = empServiceId;
    	}
    public int getEmployeeId() { 
    	return employeeId;
    	}
    public void setEmployeeId(int employeeId) {
    	this.employeeId = employeeId; 
    	}
    public int getServiceId() { 
    	return serviceId; 
    	}
    public void setServiceId(int serviceId) {
    	this.serviceId = serviceId; 
    	}
    public String getDetails() {
    	return details; 
    	}
    public void setDetails(String details) { 
    	this.details = details; 
    	}
}
