package com.iodigital.tedtalks.controller;

import com.iodigital.tedtalks.batch.talks.job.ImportTalksBatchJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
@Slf4j
public class BatchJobController {

    private final JobLauncher jobLauncher;

    private final ApplicationContext applicationContext;


    /**
     *
     * @param jobName String
     * @param fileName String
     * @return String
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobParametersInvalidException
     * @throws JobRestartException
     */
    @GetMapping("/run/{jobName}")
    public String run(@PathVariable String jobName, @RequestParam String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        final Job job = (Job) applicationContext.getBean(jobName);

        log.info("Starting the batch job: {}", ImportTalksBatchJob.NAME);
        String status = "";
        try {
            JobParameters jobParameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis()))
                    .addString("FILE_NAME", fileName)
                    .toJobParameters();
            final JobExecution execution = jobLauncher.run(job, jobParameters);
            status = execution.getStatus().name();
            log.info("Job Status : {}", execution.getStatus());
        } catch (final Exception e) {
            log.error("Job failed {}", e.getMessage());
        }
        return status;
    }
}
