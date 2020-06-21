package com.yang.proxy;

import com.yang.proxy.utils.DynamicProxyUtil;

import java.lang.reflect.Proxy;

public class ProxyDemo {

    public static void main1(String[] args) {
        Pay aliPay = new AliPay();
        PayInvocationHandler h = new PayInvocationHandler(aliPay);
        Pay aliPay1 = DynamicProxyUtil.newInstance(aliPay, h);
        aliPay1.pay();
        System.out.println("\n" + "====================");
        aliPay1.dueDate();
    }

}
