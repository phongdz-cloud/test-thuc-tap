package com.stc.test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 3:34 PM
 * Filename  : DuplicatedEmailException
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedEmailException extends Exception {
    public DuplicatedEmailException(String mes) {
        super(mes);
    }
}
