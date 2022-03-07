package com.stc.test.controllers;

import com.stc.test.dtos.NhanVienDTO;
import com.stc.test.dtos.NhanVienStatusDTO;
import com.stc.test.entities.NhanVien;
import com.stc.test.exceptions.DuplicatedEmailException;
import com.stc.test.exceptions.EmployeeNotFoundException;
import com.stc.test.exceptions.InvalidException;
import com.stc.test.services.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:33 PM
 * Filename  : EmployeeController
 */
@RequestMapping("/api")
@RestController
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /***
     * @author: phonghh
     * @param search: Từ khóa tìm kiếm theo email hoặc name
     * @param page: Số trang, bắt đầu từ 0
     * @param size: số phần tử trên 1 trang, default 20
     * @param sort: sort trên mongo
     * @param column:sort trên mongo
     * @return
     */
    @ApiOperation(value = "Lấy ra danh sách nhân viên và phân trang")
    @GetMapping("/employee/paging")
    public ResponseEntity<Page<NhanVien>> getEmployeePaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "email") String column) {

        return ResponseEntity.ok(employeeService.getEmployeePaging(search, page, size, sort, column));
    }


    /**
     * @param nhanVienDTO: Dto thêm nhân viên với role là Admin
     * @return Trả về thông tin vừa tạo nhân viên
     * @throws DuplicatedEmailException quăng ra exception nếu trong database đã có email này tồn tại
     */
    @ApiOperation(value = "Tạo nhân viên với role là admin")
    @PostMapping("/employee/admin")
    public ResponseEntity<NhanVien> createAdmin(@Valid @RequestBody NhanVienDTO nhanVienDTO) throws DuplicatedEmailException {
        return ResponseEntity.ok(employeeService.createEmployee(nhanVienDTO));
    }

    /**
     * @param nhanVienDTO Dto thêm nhân viên với role là Admin
     * @return Trả về thông tin vừa tạo nhân viên với role là NHAN_VIEN
     * @throws DuplicatedEmailException: quăng ra exception nếu trong database đã có email này tồn tại
     */
    @ApiOperation(value = "Tạo nhân viên với role là NHAN_VIEN")
    @PostMapping("/employee")
    public ResponseEntity<NhanVien> createEmployeeCore(@Valid @RequestBody NhanVienDTO nhanVienDTO) throws DuplicatedEmailException {
        return ResponseEntity.ok(employeeService.addNewEmployeeCore(nhanVienDTO));
    }

    /**
     * @param email: Tham số truyền vào để lấy ra nhân viên có email trong cơ sở dữ liệu
     * @return Trả về đối tượng tìm được ở đây là nhân viên
     * @throws EmployeeNotFoundException: Ném ra exception nếu không tìm thấy nhân viên nào có email tuong ứng với đầu vào
     * @throws InvalidException:          Ném ra invalidException nếu người nhập bỏ trống email
     */
    @ApiOperation(value = "Tìm ra nhân viên theo email")
    @GetMapping("/employee/getEmail/{email}")
    public ResponseEntity<NhanVien> getEmployeeByEmail(@Valid @PathVariable(value = "email") String email) throws EmployeeNotFoundException, InvalidException {
        if (!email.isEmpty()) {
            NhanVien employeeByEmail = employeeService.getEmployeeByEmail(email);
            return ResponseEntity.ok(employeeByEmail);
        } else {
            throw new InvalidException("Email không được rỗng");
        }
    }

    /**
     * @param id: Tham số truyền vào để lấy ra nhân viên theo id
     * @return trả ra đổi tượng NhanVien nếu tìm được NhanVien có id với tham số đầu vào
     * @throws EmployeeNotFoundException : ném ra exception nếu không tìm thấy NhanVien theo id tương ứng
     * @throws InvalidException          : ném ra Exception nếu tham số đầu vào là rỗng
     */
    @ApiOperation(value = "Tìm ra nhân viên theo id")
    @GetMapping("/employee/getId/{id}")
    public ResponseEntity<NhanVien> getEmployeeById(@Valid @PathVariable(value = "id") String id) throws EmployeeNotFoundException, InvalidException {
        if (!id.isEmpty()) {
            NhanVien employeeById = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employeeById);
        } else {
            throw new InvalidException("Id không được bỏ trống");
        }
    }

    /**
     * @param id:          Kiểu dữ liệu chuỗi dùng để tìm NhanVien theo id
     * @param nhanVienDTO: Kiểu dữ liệu object dùng để cập nhật thông tin nhân viên
     * @return Trả về object NhanVien sau khi vừa cập nhật
     * @throws EmployeeNotFoundException: Ném ra ngoại lệ nếu không tìm thấy nhân viên
     * @throws InvalidException:          Ném ra ngoại lệ nều Id là một chuỗi rỗng
     */
    @ApiOperation(value = "Cập nhật lại thông tin nhân viên")
    @PutMapping("/employee/{id}")
    public ResponseEntity<NhanVien> updateEmployee(@Valid @PathVariable(value = "id") String id
            , @Valid @RequestBody NhanVienDTO nhanVienDTO) throws EmployeeNotFoundException, InvalidException {
        if (!id.isEmpty()) {
            NhanVien employee = employeeService.updateEmployee(id, nhanVienDTO);
            return ResponseEntity.ok(employee);
        } else {
            throw new InvalidException("Id không được để rỗng");
        }
    }

    /**
     * @param id                Kiểu dữ liệu chuỗi dùng để tìm NhanVien theo id
     * @param nhanVienStatusDTO Kiểu dữ liệu object dùng để cập nhật thông tin là trạng thái của nhân viên
     * @return Trả về object NhanVien sau khi vừa cập nhật
     * @throws EmployeeNotFoundException: Ném ra ngoại lệ nếu không tìm thấy nhân viên
     * @throws InvalidException:          Ném ra ngoại lệ nều Id là một chuỗi rỗng
     */
    @ApiOperation(value = "Thay đổi trạng thái của nhân viên")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<NhanVien> changeStatus(@Valid @PathVariable(value = "id") String id,
                                                 @RequestBody NhanVienStatusDTO nhanVienStatusDTO) throws EmployeeNotFoundException, InvalidException {
        if (!id.isEmpty()) {
            NhanVien nhanVien = employeeService.changeStatus(id, nhanVienStatusDTO);
            return ResponseEntity.ok(nhanVien);
        } else {
            throw new InvalidException("Id không được để rỗng");
        }
    }

    /**
     * @param donVi là kiểu dữ liệu String dùng để lấy ra nhân viên là trưởng đơn vị
     * @return Trả về danh sách nhân viên có Role là trưởng đơn vị.
     */
    @ApiOperation(value = "Get nhân viên là trưởng đơn vị của đơn vị có được truyền vào")
    @GetMapping("/employee/getRole/{donVi}")
    public ResponseEntity<List<NhanVien>> getRoleDonVi(@Valid @PathVariable(value = "donVi") String donVi) {
        return ResponseEntity.ok(employeeService.getRoleByDonVi(donVi));
    }


}
