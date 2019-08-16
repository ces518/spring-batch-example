package me.june.bacth.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * SkipCheckingListener.java
 * SkipCheckingListener
 * ==============================================
 *
 * @author PJY
 * @history 작성일            작성자     변경내용
 * @history 2019-08-16         PJY      최초작성
 * ==============================================
 */

public class SkipCheckingListener extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        // FAILED가 아닌경우
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
            stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        } else {
            return null;
        }
    }
}
