package com.linus.demo.java.quartz.jobs;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public class GetPromotionJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.err.println("Begin to get promotions");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		Date beginTime = cal.getTime();

		Trigger trigger = newTrigger().withIdentity("setprice", "promotionId")
				.startAt(beginTime).forJob("setprice", "promotion")
				.usingJobData("promotionId", "linus123").build();
		
		try {
			if (!context.getScheduler().checkExists(trigger.getKey())) {
				context.getScheduler().scheduleJob(trigger);
			}
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Trigger trigger2 = newTrigger().withIdentity("revertprice", "promotionId")
				.startAt(beginTime).forJob("revertprice", "promotion")
				.usingJobData("promotionId", "linus123").build();
		
		try {
			if (!context.getScheduler().checkExists(trigger2.getKey())) {
				context.getScheduler().scheduleJob(trigger2);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.err.println("End to get promotions");
	}
}
