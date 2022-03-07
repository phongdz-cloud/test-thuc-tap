package com.stc.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stc.test.entities.embedded.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/7/22
 * Time      : 09:07
 * Filename  : NhanVien
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class NhanVien {
    @Id
    private String id;

    @Field(value = "fullname")
    @Size(min = 3, max = 25, message = "Họ và tên không được để trống ít nhất là 3 kí tự và tối đa là 25 kí tự")
    private String hoTen;

    @Field(value = "email")
    @Email(message = "Email bạn vừa nhập không đúng yêu cầu. Vui lòng nhập đúng định dạng vd: example@gmail.com")
    @Size(min = 10, max = 35, message = "Email không được bỏ trống hoặc ít hơn 10 kí tự!")
    private String email;

    @JsonIgnore
    @Size(min = 5, max = 15, message = "Mật khẩu không được bỏ trống và ít nhất 5 kí tự và tối đa là 15 kí tự")
    private String password;

    private List<Role> roles = new ArrayList<>();

    private boolean trangThai = true;
}
