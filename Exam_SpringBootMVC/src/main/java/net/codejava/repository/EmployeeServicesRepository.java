package net.codejava.repository;

import net.codejava.model.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeServicesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map database rows to EmployeeServices model
    private RowMapper<EmployeeServices> rowMapper = new RowMapper<EmployeeServices>() {
        @Override
        public EmployeeServices mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmployeeServices employeeService = new EmployeeServices();
            employeeService.setEmpServiceId(rs.getInt("emp_service_id"));
            employeeService.setEmployeeId(rs.getInt("employee_id"));
            employeeService.setServiceId(rs.getInt("service_id"));
            employeeService.setDetails(rs.getString("details"));
            return employeeService;
        }
    };
    
    public boolean existsByEmployeeIdAndServiceId(int employeeId, int serviceId) {
        String sql = "SELECT COUNT(*) FROM Employee_Services WHERE employee_id = ? AND service_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, employeeId, serviceId);
        return count != null && count > 0;
    }


    // Create
    public void save(EmployeeServices employeeService) {
        String sql = "INSERT INTO Employee_Services (employee_id, service_id, details) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employeeService.getEmployeeId(), employeeService.getServiceId(), employeeService.getDetails());
    }

    // Read by ID
    public EmployeeServices findById(int emp_service_id) {
        String sql = "SELECT * FROM Employee_Services WHERE emp_service_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{emp_service_id}, rowMapper);
    }

    // Read all
    public List<EmployeeServices> findAll() {
        String sql = "SELECT * FROM Employee_Services";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Update
    public void update(EmployeeServices employeeService) {
        String sql = "UPDATE Employee_Services SET employee_id = ?, service_id = ?, details = ? WHERE emp_service_id = ?";
         jdbcTemplate.update(sql, employeeService.getEmployeeId(), employeeService.getServiceId(), employeeService.getDetails(), employeeService.getEmpServiceId());
    }

    // Delete
    public void deleteById(int emp_service_id) {
        String sql = "DELETE FROM Employee_Services WHERE emp_service_id = ?";
         jdbcTemplate.update(sql, emp_service_id);
    }
}
