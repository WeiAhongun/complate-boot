package com.shangma.cn.common.pool;

import com.shangma.cn.common.utils.EmailUtils;
public class AsyncFactory {
    public static Runnable sendEmail(String from, String to, String subject, String templateStr){
        return ()->{
            System.out.println(Thread.currentThread().getName());
            EmailUtils.sendEmail(from,to,subject,templateStr);
        };
    }
}
