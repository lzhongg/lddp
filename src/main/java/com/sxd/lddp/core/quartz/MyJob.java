package com.sxd.lddp.core.quartz;

import com.sxd.lddp.core.utils.AppConfig;
import lombok.extern.log4j.Log4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Log4j
@DisallowConcurrentExecution
@Component("myJob")
public class MyJob extends QuartzJobBean{

    @Autowired
    private AppConfig appconfig;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
