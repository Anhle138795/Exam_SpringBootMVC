package net.codejava.controller;

import net.codejava.model.Employees;
import net.codejava.service.EmailService;
import net.codejava.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/employees")
public class EmployeesController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmailService emailService;
    
   
    @GetMapping("/dashboard")
    public String dashboard() {
        return "employees/emp_index";
    }
    
   
    @GetMapping("/login")
    public String loginForm() {
        return "employees/emp_login";
    }
    
  
    @PostMapping("/chklogin")
    public String checkLogin(@RequestParam("email") String email,
                             @RequestParam("pwd") String password,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Employees employee = employeeService.findByEmail(email);
        
        if (employee != null) {
            if (employee.getStatus() == 0) {
              
                logger.warn("Attempt to login with unverified account: " + email);
                return "redirect:/employees/login?unverified=true";
            }
            
            if (employeeService.checkPassword(password, employee.getPassword())) {
           
                HttpSession session = request.getSession();
                session.setAttribute("employee", employee);
                
                logger.info("User authenticated: " + email + ", UserType: " + employee.getUserType());
                
             
                switch (employee.getUserType().toUpperCase()) {
                    case "ADMIN":
                        return "redirect:/admin/dashboard";
                    case "MANAGER":
                        return "redirect:/manager/dashboard";
                    case "EMPLOYEE":
                        return "redirect:/employees/dashboard";
                    default:
                        logger.warn("Unknown userType: " + employee.getUserType());
                        return "redirect:/employees/login?error=true";
                }
            } else {
                logger.error("Password check failed for user: " + email);
            }
        } else {
            logger.error("User not found: " + email);
        }
        
        return "redirect:/employees/login?error=true";
    }

   
    @GetMapping("/register")
    public String registerForm() {
        return "employees/emp_register";
    }
    
    
    @PostMapping("/register")
    public String registerUser(@RequestParam("fullname") String fullname,
                               @RequestParam("pwd") String password,
                               @RequestParam("userType") String userType,
                               @RequestParam("email") String email,
                               @RequestParam("phone") String phone,
                               @RequestParam("address") String address,
                               @RequestParam("profileImage") String profileImage,
                               @RequestParam("experienceYears") int experienceYears,
                               @RequestParam("salary") int salary,
                               @RequestParam("status") int status,
                               HttpServletRequest request) {
       
        Employees newEmployee = new Employees();
        newEmployee.setFullname(fullname);
        newEmployee.setPassword(password); 
        newEmployee.setUserType(userType);
        newEmployee.setEmail(email);
        newEmployee.setPhone(phone);
        newEmployee.setAddress(address);
        newEmployee.setProfileImage(profileImage);
        newEmployee.setExperienceYears(experienceYears);
        newEmployee.setSalary(salary);
        newEmployee.setStatus(status);

        String siteURL = ServletUriComponentsBuilder.fromRequestUri(request)
                        .replacePath(null)
                        .build()
                        .toUriString();

        employeeService.saveUser(newEmployee, siteURL);
        
        logger.info("New user registered: " + fullname + ", UserType: " + userType);
        
        return "redirect:/employees/login?registered=true";
    }
    
    // Verify account
    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("code") String code, Model model) {
        Employees employee = employeeService.findByVerifyCode(code);
        
        if (employee == null) {
            model.addAttribute("message", "Invalid verification code.");
            return "employees/emp_verify"; 
        }
        
        // Activate user
        employee.setStatus(1);
        employee.setVerifyCode(null); 
        employeeService.updateUser(employee);
        
        model.addAttribute("message", "Your account has been verified. You can now log in.");
        return "employees/emp_verify";
    }
    
}
