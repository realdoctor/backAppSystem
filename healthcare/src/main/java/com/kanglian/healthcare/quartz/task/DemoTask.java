package com.kanglian.healthcare.quartz.task;

import com.kanglian.healthcare.quartz.common.AbstractTask;

/**
 * 
 * @author xl.liu
 */
public class DemoTask extends AbstractTask {

    public DemoTask(){
        this.taskName = "demoTask";
    }

    @Override
    public void process() {
        System.err.println("=====================================");
    }

    @Override
    public boolean canProcess() {
        return true;
    }
}
