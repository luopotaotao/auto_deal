package com.tt.schedule;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tt on 2016/12/26.
 */
public abstract class SuspendableTask {
    protected AtomicBoolean status=new AtomicBoolean();

    abstract void run() throws Exception;
    public void pause(){
        status.set(false);
    }
    public void resume(){
        status.set(true);
    }
    @Scheduled(cron = "0 * * * * ?")
    public void execute() throws Exception{
        if(status.get()){
            run();
        }
    }
}
