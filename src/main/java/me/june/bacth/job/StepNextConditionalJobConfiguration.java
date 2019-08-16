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
 * StepNextConditionalJobConfiguration.java
 * StepNextConditionalJobConfiguration
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
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextConfigurationJob () {
        return jobBuilderFactory.get("stepNextConfigurationJob")
                .start(conditionalJobStep1())
                    .on("FAILED") // 실패시
                    .to(conditionalJobStep3()) // step3
                    .on("*") // 결과 무관
                    .end() // step3 종료시 Flow 종료
                .from(conditionalJobStep1())
                    .on("*") // FAILED 외 모든 경우
                    .to(conditionalJobStep2()) // step2
                    .next(conditionalJobStep3()) //step2 정상 종료시 step3
                    .on("*") // step3 결과 무관
                    .end() // Flow 종료
                .end() // job 종료
                .build();
    }

    @Bean
    public Step conditionalJobStep1 () {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>> this is step 1");
                    //contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalJobStep2 () {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>> this is step 2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalJobStep3 () {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>> this is step 3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}