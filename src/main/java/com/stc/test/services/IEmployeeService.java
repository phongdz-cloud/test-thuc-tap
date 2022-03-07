package com.stc.test.services;

import com.stc.test.dtos.NhanVienDTO;
import com.stc.test.dtos.NhanVienStatusDTO;
import com.stc.test.entities.NhanVien;
import com.stc.test.exceptions.DuplicatedEmailException;
import com.stc.test.exceptions.EmployeeNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:34 PM
 * Filename  : IEmployeeService
 */
public interface IEmployeeService {
    Page<NhanVien> getEmployeePaging(String search, int page, int size, String sort, String column);

    List<NhanVien> getRoleByDonVi(String donVi);

    NhanVien addNewEmployeeCore(NhanVienDTO nhanVienDTO) throws DuplicatedEmailException;

    NhanVien createEmployee(NhanVienDTO nhanVienDTO) throws DuplicatedEmailException;

    NhanVien getEmployeeByEmail(String email) throws EmployeeNotFoundException;

    NhanVien getEmployeeById(String id) throws EmployeeNotFoundException;

    NhanVien updateEmployee(String id, NhanVienDTO nhanVienDTO) throws EmployeeNotFoundException;

    NhanVien changeStatus(String id, NhanVienStatusDTO nhanVienStatusDTO) throws EmployeeNotFoundException;
}
