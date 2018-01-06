package com.catcher.javanium.p2p.server.subscribers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Cleaner implements Job{


	private static final String EVERY_5_MINUTE = "0 0/5 * 1/1 * ? *";
	private static final Logger log = Logger.getLogger(Cleaner.class.getName());


	public static void runJob() throws SchedulerException{
		JobDetail job = JobBuilder.newJob(Cleaner.class).build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(EVERY_5_MINUTE))
				.build(); 

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try(Connection connection = SubscriberDBHandler.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM SUBSCRIBERS WHERE LAST_SEEN < ?")) {

			long now = System.currentTimeMillis();
			long oldEntryTime = now - TimeUnit.MINUTES.toMillis(30);
			statement.setLong(1, oldEntryTime);

			statement.executeUpdate();
		} catch (SQLException e){
			log.severe("Failed to clean old entries");
		}
	}

}
