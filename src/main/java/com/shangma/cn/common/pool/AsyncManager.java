package com.shangma.cn.common.pool;

import com.shangma.cn.common.container.SpringBeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
public class AsyncManager {

    private  ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private static AsyncManager asyncManager;
    private AsyncManager(){
        threadPoolTaskExecutor = SpringBeanUtils.getBean(ThreadPoolTaskExecutor.class);
    }
    public static AsyncManager getInstance(){
        if(asyncManager==null){
            return new AsyncManager();
        }
        return asyncManager;
    }
    /**
     * 执行异步操作
     * @param runnable
     */
    public void execute(Runnable runnable){
        threadPoolTaskExecutor.execute(runnable);
    }
    public void shutdown(){
        threadPoolTaskExecutor.shutdown();
    }
}
