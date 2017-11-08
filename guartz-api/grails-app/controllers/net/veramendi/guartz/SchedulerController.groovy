package net.veramendi.guartz

import grails.converters.*
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.impl.triggers.CronTriggerImpl
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK

class SchedulerController {
	
    def start() {
        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory()
            schedulerFactory.initialize()
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob(PingJob.class).build()

            CronTriggerImpl trigger = new CronTriggerImpl()
            trigger.setName("ping job trigger")
            trigger.setStartTime(new Date())
            trigger.setCronExpression("0/30 * * * * ?")

            scheduler.scheduleJob(jobDetail, trigger)

            scheduler.start();

            Date now = new Date()
            println "scheduler running ....(${now})"

            response.status = OK.value()
            render([mensaje: 'scheduler running'] as JSON)
        } catch (Exception e) {
            e.printStackTrace()

            response.status = INTERNAL_SERVER_ERROR.value()
            render([mensaje: e.getMessage()] as JSON)
        }
    }

    def shutdown() {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.shutdown()

            Date now = new Date()
            println "scheduler shuting down ....(${now})"

            response.status = OK.value()
            render([mensaje: 'scheduler shutdown.' ] as JSON)
        } catch (Exception e) {
            e.printStackTrace()

            response.status = INTERNAL_SERVER_ERROR.value()
            render([mensaje: e.getMessage()] as JSON)
        }

    }
}
