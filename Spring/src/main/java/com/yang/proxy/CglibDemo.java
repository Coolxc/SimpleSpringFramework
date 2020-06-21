package com.yang.proxy;

import com.yang.proxy.cglib.AliPayInterceptor;
import com.yang.proxy.utils.CglibUtil;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibDemo {
    public static void main(String[] args) {
        AliPay aliPay = new AliPay();
        MethodInterceptor aliPayInterceptor = new AliPayInterceptor();
        AliPay aliPay1 = CglibUtil.creatProxy(aliPay, aliPayInterceptor);
        aliPay1.pay();
        System.out.println(aliPay1.dueDate());;
    }
}
