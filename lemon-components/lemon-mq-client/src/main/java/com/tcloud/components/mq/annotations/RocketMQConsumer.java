package com.tcloud.components.mq.annotations;

import com.tcloud.components.mq.enums.MQRouter;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMQConsumer {

    MQRouter mqTopic();

}
