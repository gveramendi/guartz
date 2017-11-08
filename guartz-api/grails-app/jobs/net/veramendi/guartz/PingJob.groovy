package net.veramendi.guartz

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class PingJob implements Job {

//    static triggers = {
//        cron name: 'pingTrigger', cronExpression: "0/30 * * * * ?"
//    }

    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        Date now = new Date()
        println "quartz corriendo .... (${now})"
    }
}