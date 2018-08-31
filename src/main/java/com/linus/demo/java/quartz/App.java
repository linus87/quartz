package com.linus.demo.java.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.linus.demo.java.quartz.jobs.RevertPriceJob;
import com.linus.demo.java.quartz.jobs.SetPriceJob;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SchedulerException
    {

    	  // Grab the Scheduler instance from the Factory
    	  Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    	  
    	  if (!scheduler.checkExists(jobKey("setprice", "promotion"))) {
    		// define the job and tie it to our MyJob class
        	  JobDetail job = newJob(SetPriceJob.class)
        	      .withIdentity("setprice", "promotion")
        	      .storeDurably()
        	      .build();
        	  scheduler.addJob(job, true);
    	  }
    	  
    	  if (!scheduler.checkExists(jobKey("reverprice", "promotion"))) {
          	  JobDetail job = newJob(RevertPriceJob.class)
              	      .withIdentity("reverprice", "promotion")
              	      .storeDurably()
              	      .build();
          	  scheduler.addJob(job, true);
      	  }
    	  
    	  Calendar cal = Calendar.getInstance();
    	  cal.add(Calendar.MINUTE, 1);
    	  Date beginTime = cal.getTime();
    	  cal.add(Calendar.MINUTE, 1);
    	  Date endTime = cal.getTime();
    	  
    	  Trigger setTrigger = newTrigger()
    			    .withIdentity("setprice", "promotionID")
    			    .startAt(beginTime)
    			    .forJob("setprice", "promotion")
    			    .build();
    	  
    	  Trigger revertTrigger = newTrigger()
  			    .withIdentity("reverprice", "promotionID")
  			    .startAt(endTime)
  			    .forJob("reverprice", "promotion")
  			    .build();

    	  scheduler.scheduleJob(setTrigger);
    	  scheduler.scheduleJob(revertTrigger);
    	  // and start it off
    	  scheduler.start();
    	  
       System.out.println( "Hello World!" );
    }
}
