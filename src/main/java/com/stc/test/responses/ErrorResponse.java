package com.stc.test.responses;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 10:56 AM
 * Filename  : ErrorResponse
 */
@XmlRootElement(name = "error")
@Data
public class ErrorResponse {

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;

}
