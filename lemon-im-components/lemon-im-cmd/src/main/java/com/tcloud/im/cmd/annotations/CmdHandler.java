package com.tcloud.im.cmd.annotations;

import com.tcloud.im.common.enums.Command;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author evans
 * @description
 * @date 2023/8/3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface CmdHandler {


    Command cmd();

}
