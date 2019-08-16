package me.june.bacth.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CustomExitCodeJobConfiguration.java
 * CustomExitCodeJobConfiguration
 * ==============================================
 *
 * @author PJY
 * @history 작성일            작성자     변경내용
 * @history 2019-08-16         PJY      최초작성
 * ==============================================
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomExitCodeJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customExitCodeJob () {
        return jobBuilderFactory.get("customExitCodeJob")
                .start(step01())
                    .on("FAILED")
                    .end()
                .from(step01())
                    .on("COMPLETED WITH SKIPS")
                    .to(step02())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    public Step step01 () {
        return stepBuilderFactory.get("step01")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> step01");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step step02 () {
        return stepBuilderFactory.get("step02")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> step02");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
