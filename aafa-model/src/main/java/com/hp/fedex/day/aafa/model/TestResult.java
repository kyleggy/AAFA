package com.hp.fedex.day.aafa.model;

import lombok.Data;

/**
 * Created by lizhaok on 3/17/2015.
 */
@Data
public class TestResult {
    private String testCaseName;
    private boolean isFailed;
    private String failedStep;
    private String failedStepCallStack;
}
