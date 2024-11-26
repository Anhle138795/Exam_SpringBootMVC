package net.codejava.service;

import net.codejava.model.EmployeeServices;
import net.codejava.repository.EmployeeServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServicesService {

    @Autowired
    private EmployeeServicesRepository repository;

    public void save(EmployeeServices employeeService) {
         repository.save(employeeService);
    }
    
    public boolean addEmployeeService(EmployeeServices employeeService) {
        if (repository.existsByEmployeeIdAndServiceId(employeeService.getEmployeeId(), employeeService.getServiceId())) {
            return false; // Duplicate found
        }
        repository.save(employeeService);
        return true;
    }

    public EmployeeServices findById(int empServiceId) {
        return repository.findById(empServiceId);
    }

    public List<EmployeeServices> findAll() {
        return repository.findAll();
    }

    public void update(EmployeeServices employeeService) {
         repository.update(employeeService);
    }

    public void deleteById(int empServiceId) {
         repository.deleteById(empServiceId);
    }
}
