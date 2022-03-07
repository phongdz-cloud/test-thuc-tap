package com.stc.test.repositories;

import com.stc.test.entities.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:34 PM
 * Filename  : EmployeeRepository
 */
public interface EmployeeRepository extends MongoRepository<NhanVien, String> {
    @Query(value = "{$or: [{'email' : { $regex: ?0, $options: 'i'} }, {'fullname' : { $regex: ?0, $options: 'i'} }] }"
            , sort = "{'trangThai': -1, 'fullname' : 1}")
    Page<NhanVien> getEmployeePaging(String search, Pageable pageable);

    @Query(value = "{'email' : ?0}")
    Optional<NhanVien> findEmployeeByEmail(String email);

    @Query(value = "{'roles.donVi': ?0}")
    List<NhanVien> getRoleByDonVi(String donVi);

}
