package com.example.demo.validate.impl;

import com.example.demo.exception.ParameterValidateException;

/**
 * 通用校验工具类,主要用于校验参数,简化异常的信息抛出代码
 *
 * @author xuweijian
 * @date 2019年2月25日
 */
public class ValidateUtils {
    private ValidateUtils() {
    }
    /**
     * 校验参数,,如果不通过,抛出ParameterValidateException异常
     *
     * @param condition 检查参数
     * @param throwMsg  抛出报错信息
     */
    public static void ifTrueThrowException(boolean condition, String throwMsg)  {
        if (condition) {
            throw new ParameterValidateException(throwMsg);
        }
    }
}
