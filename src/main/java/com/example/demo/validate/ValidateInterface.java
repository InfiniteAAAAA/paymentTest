package com.example.demo.validate;


import com.example.demo.exception.ParameterValidateException;

/**
 * 校验的接口.统一管理各种校验的方法与调用.
 * 策略模式.有此接口提供统一的校验方法.由实现类自行校验对应的参数
 *
 * @author xuweijian
 * @date 2019年2月3日
 */
public interface ValidateInterface<T> {

    /**
     * 统一校验接口
     *
     * @param requestParamObj 需要校验对象,包含需要校验的数据
     * @throws ParameterValidateException 校验错误后,抛出异常
     */
    void validate(T requestParamObj) throws ParameterValidateException;
}
