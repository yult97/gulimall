package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author yubo
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    /**
     * MethodArgumentNotValidException 参数校验异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) {
        Map<String, String> errorHashMap = new HashMap<>();
        log.error("数据校验出现问题:{},异常类型:{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(item -> {
            errorHashMap.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMessage()).put("data", errorHashMap);
    }

    /**
     * 全部异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public R handler(Throwable e) {
        log.error("数据校验出现问题:{},异常类型:{}", e.getMessage(), e.getClass());
        return R.error();
    }
}
