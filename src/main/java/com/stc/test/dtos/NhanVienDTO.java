package com.stc.test.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stc.test.entities.embedded.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:33 PM
 * Filename  : NhanVienDTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienDTO {
    private String hoTen;

    private String email;

    private String password;

    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    private boolean trangThai = true;
}
