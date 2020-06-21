package com.yang.proxy;

public class AliPay implements Pay{
    public void pay(){
        System.out.println("Use the Alibaba to pay");
    }

    @Override
    public String dueDate() {
        return "2020-5-520";
    }
}
