package com.stc.test.services.Impl;

import com.stc.test.dtos.NhanVienDTO;
import com.stc.test.dtos.NhanVienStatusDTO;
import com.stc.test.entities.NhanVien;
import com.stc.test.entities.embedded.Role;
import com.stc.test.exceptions.DuplicatedEmailException;
import com.stc.test.exceptions.EmployeeNotFoundException;
import com.stc.test.repositories.EmployeeRepository;
import com.stc.test.services.IEmployeeService;
import com.stc.test.utils.EnumRole;
import com.stc.test.utils.PageUtils;
import com.stc.vietnamstringutils.VietnameseStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:34 PM
 * Filename  : EmployeeServiceImpl
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final EmployeeRepository employeeRepository;

    private final VietnameseStringUtils vietnameseStringUtils;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, VietnameseStringUtils vietnameseStringUtils) {
        this.employeeRepository = employeeRepository;
        this.vietnameseStringUtils = vietnameseStringUtils;
    }

    @Override
    public Page<NhanVien> getEmployeePaging(String search, int page, int size, String sort, String column) {
        Pageable pageble = PageUtils.createPageble(page, size, sort, column);
        return employeeRepository.getEmployeePaging(vietnameseStringUtils.makeSearchRegex(search), pageble);
    }

    @Override
    public List<NhanVien> getRoleByDonVi(String donVi) {
        return employeeRepository.getRoleByDonVi(donVi);
    }

    @Override
    public NhanVien addNewEmployeeCore(NhanVienDTO nhanVienDTO) throws DuplicatedEmailException {
        Optional<NhanVien> employee = employeeRepository.findEmployeeByEmail(nhanVienDTO.getEmail());
        if (employee.isPresent()) {
            throw new DuplicatedEmailException("Email đã được khởi tạo trước đó.");
        }
        NhanVien createdEmployee = modelMapper.map(nhanVienDTO, NhanVien.class);
        createdEmployee.setRoles(Collections.singletonList(
                new Role(String.valueOf(EnumRole.NHAN_VIEN.ordinal()), EnumRole.NHAN_VIEN.name())));
        employeeRepository.save(createdEmployee);
        return createdEmployee;
    }

    @Override
    public NhanVien createEmployee(NhanVienDTO nhanVienDTO) throws DuplicatedEmailException {
        Optional<NhanVien> employeeByEmail = employeeRepository.findEmployeeByEmail(nhanVienDTO.getEmail());
        if (employeeByEmail.isPresent()) {
            throw new DuplicatedEmailException("Email đã được khởi tạo trước đó");
        }
        NhanVien createNhanVien = modelMapper.map(nhanVienDTO, NhanVien.class);
        createNhanVien.setRoles(Collections.singletonList(
                new Role(String.valueOf(EnumRole.TRUONG_DON_VI.ordinal()), EnumRole.TRUONG_DON_VI.name())));
        return employeeRepository.save(createNhanVien);
    }

    @Override
    public NhanVien getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        Optional<NhanVien> employee = employeeRepository.findEmployeeByEmail(email);
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException("Email này không được tìm thấy trong cơ sở dữ liệu!");
        }
        return employee.get();
    }

    @Override
    public NhanVien getEmployeeById(String id) throws EmployeeNotFoundException {
        Optional<NhanVien> employee = employeeRepository.findById(id);
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException("Không tìm thấy nhân viên nào có id này trong cơ sở dữ liệu");
        }
        return employee.get();
    }

    @Override
    public NhanVien updateEmployee(String id, NhanVienDTO nhanVienDTO) throws EmployeeNotFoundException {
        NhanVien oldEmployee = getEmployeeById(id);
        if (!nhanVienDTO.getHoTen().isEmpty()) {
            oldEmployee.setHoTen(nhanVienDTO.getHoTen());
        }
        if (!nhanVienDTO.getEmail().isEmpty()) {
            oldEmployee.setEmail(nhanVienDTO.getEmail());
        }
        if (!nhanVienDTO.getPassword().isEmpty()) {
            oldEmployee.setPassword(nhanVienDTO.getPassword());
        }
        return employeeRepository.save(oldEmployee);
    }

    @Override
    public NhanVien changeStatus(String id, NhanVienStatusDTO nhanVienStatusDTO) throws EmployeeNotFoundException {
        NhanVien changeEnableEmployee = getEmployeeById(id);
        changeEnableEmployee.setTrangThai(nhanVienStatusDTO.isTrangthai());
        employeeRepository.save(changeEnableEmployee);
        return changeEnableEmployee;
    }


}
