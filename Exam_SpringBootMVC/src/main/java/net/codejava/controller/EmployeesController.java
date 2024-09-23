//package net.codejava.controller;
//
//import net.codejava.model.Employees;
//import net.codejava.service.EmailService;
//import net.codejava.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/employees")
//public class EmployeesController {
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/register")
//    public String registerEmployee(@RequestParam("fullname") String fullname,
//                                   @RequestParam("email") String email,
//                                   @RequestParam("password") String password,
//                                   Model model) {
//        // Tạo mã xác minh ngẫu nhiên
//        String verifyCode = UUID.randomUUID().toString();
//
//        Employees newEmployee = new Employees();
//        newEmployee.setFullname(fullname);
//        newEmployee.setEmail(email);
//        newEmployee.setPassword(password); // Để bảo mật, nên mã hóa mật khẩu trước khi lưu
//        newEmployee.setVerifyCode(verifyCode); // Lưu mã xác minh
//
//        // Lưu thông tin người dùng vào cơ sở dữ liệu
//        employeeService.saveUser(newEmployee); // Sử dụng phương thức saveUser đã mã hóa mật khẩu
//
//        // Gửi email xác minh
//        String subject = "Xác minh đăng ký của bạn";
//        String body = "Sử dụng mã xác minh sau để hoàn tất đăng ký: " + verifyCode;
//        emailService.sendVerificationEmail(email, subject, body);
//
//        return "register_success"; // Redirect đến trang xác nhận
//    }
//
//    @GetMapping("/verify")
//    public String verifyEmployee(@RequestParam("email") String email, 
//                                 @RequestParam("code") String code, 
//                                 Model model) {
//        Employees employee = employeeService.findByEmail(email);
//        if (employee != null && employee.getVerifyCode() != null && employee.getVerifyCode().equals(code)) {
//            // Xác minh thành công, xóa mã xác minh và cập nhật trạng thái
//            employee.setVerifyCode(null);
//            employeeService.updateUser(employee); // Cập nhật thông tin nhân viên
//
//            model.addAttribute("message", "Xác minh thành công!"); // Thêm thông báo thành công
//            return "verification_success"; // Redirect đến trang thành công
//        } else {
//            model.addAttribute("error", "Mã xác minh không hợp lệ hoặc đã được xác minh trước đó.");
//            return "verification_fail"; // Redirect đến trang thất bại
//        }
//    }
//}
