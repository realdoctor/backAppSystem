package com.realdoctor.quartz.task;

import com.realdoctor.quartz.common.AbstractTask;

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
