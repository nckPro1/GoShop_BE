package org.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // 1. API Đọc (Xem danh sách nhân viên)
    // Yêu cầu quyền: EMPLOYEE_READ
    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<List<String>> getAllEmployees() {
        // Lấy thông tin user hiện tại từ SecurityContext nếu cần
        // UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> mockEmployees = List.of("Nguyễn Văn A", "Trần Thị B", "Phạm Văn C");
        return ResponseEntity.ok(mockEmployees);
    }

    // 2. API Ghi (Tạo nhân viên mới)
    // Yêu cầu quyền: EMPLOYEE_CREATE
    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public ResponseEntity<String> createEmployee(@RequestBody String employeeData) {
        return ResponseEntity.ok("Employee created successfully: " + employeeData);
    }

    // 3. API Cực kỳ nhạy cảm (Cập nhật lương)
    // Yêu cầu quyền: SALARY_UPDATE (Chỉ dành cho Admin hoặc cấp quản lý cao)
    @PutMapping("/salary/{id}")
    @PreAuthorize("hasAuthority('SALARY_UPDATE')")
    public ResponseEntity<String> updateEmployeeSalary(@PathVariable Long id, @RequestBody Double newSalary) {
        return ResponseEntity.ok("Salary updated for Employee ID " + id + " to " + newSalary);
    }
}