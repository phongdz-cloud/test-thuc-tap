package com.stc.test.utils;

import org.springframework.data.domain.*;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : phonghh
 * Date      : 3/7/22
 * Time      : 4:26 PM
 * Filename  : PageUtils
 */
public class PageUtils {

    public static Pageable createPageble(int page, int size, String sort, String sortColumn) {
        Sort sortable = Sort.by(sortColumn).descending();
        if (sort.trim().equalsIgnoreCase("asc")) {
            sortable = Sort.by(sortColumn).ascending();
        }
        return PageRequest.of(page, size, sortable);
    }

    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
