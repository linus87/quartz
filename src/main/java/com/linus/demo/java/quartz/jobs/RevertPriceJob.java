package com.linus.demo.java.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RevertPriceJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Promotion ID is :" + context.getMergedJobDataMap().get("promotionId"));
		
		System.err.println("Hello World!  Revert price job is executing.");
	}

}
