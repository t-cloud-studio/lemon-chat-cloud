package com.tcloud.web.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageRequest<T> {

    private final Long DEFAULT_PAGE_SIZE = 20L;
    private final Long DEFAULT_PAGE_NUM = 1L;

    private Long pageSize = DEFAULT_PAGE_SIZE;

    private Long pageNum = DEFAULT_PAGE_NUM;


    private T condition;


    public <T> Page<T> toPage(){
        return new Page<>(pageNum, pageSize);
    }


}
