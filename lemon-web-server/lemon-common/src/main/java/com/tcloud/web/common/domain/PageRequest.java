package com.tcloud.web.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Data
public class PageRequest<T> {

    private final Long DEFAULT_PAGE_SIZE = 20L;
    private final Long DEFAULT_PAGE_NUM = 1L;

    private Long pageSize = DEFAULT_PAGE_SIZE;

    private Long pageNum = DEFAULT_PAGE_NUM;


    private T condition;


    public <R> Page<R> toPage(){
        return new Page<>(pageNum, pageSize);
    }


    public T getCondition(Class<T> conditionClazz){
        if (Objects.nonNull(condition)){
            return condition;
        }
        try {
            return conditionClazz.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            throw new ApplicationBizException("instance query condition object is error:", exception);
        }
    }

}
