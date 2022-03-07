package com.stc.test.entities.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/7/22
 * Time      : 09:09
 * Filename  : Role
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    // mã đơn vị
    private String donVi;

    // Lấy role từ enumrole
    private String role;


}
