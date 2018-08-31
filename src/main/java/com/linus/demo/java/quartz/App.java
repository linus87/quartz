package com.linus.demo.java.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.linus.demo.java.quartz.jobs.GetPromotionJob;
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
    	  
    	  if (!scheduler.checkExists(jobKey("revertprice", "promotion"))) {
          	  JobDetail job = newJob(RevertPriceJob.class)
              	      .withIdentity("revertprice", "promotion")
              	      .storeDurably()
              	      .build();
          	  scheduler.addJob(job, true);
      	  }
    	  
    	  if (!scheduler.checkExists(jobKey("getpromotions", "promotion"))) {
          	  JobDetail job = newJob(GetPromotionJob.class)
              	      .withIdentity("getpromotions", "promotion")
              	      .storeDurably()
              	      .build();
          	  scheduler.addJob(job, true);
      	  }
    	  
    	  // Trigger the job to run now, and then repeat every 40 seconds
    	  Trigger trigger = newTrigger()
    	      .withIdentity("getpromotions", "promotion")
    	      .forJob("getpromotions", "promotion")
    	      .startNow()
    	      .withSchedule(simpleSchedule()
    	              .withIntervalInSeconds(60)
    	              .repeatForever())
    	      .build();
    	  
    	  if (!scheduler.checkExists(trigger.getKey())) {
    		  scheduler.scheduleJob(trigger);
    	  }
    	  
    	  // and start it off
    	  scheduler.start();
    	  
       System.out.println( "Hello World!" );
    }
    
    
}
