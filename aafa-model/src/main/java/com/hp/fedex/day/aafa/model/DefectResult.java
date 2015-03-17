package com.hp.fedex.day.aafa.model;

import lombok.Data;

/**
 * Created by lizhaok on 3/17/2015.
 */
@Data
public class DefectResult {
    private String id;
    private String title;
    private DefectStatus defectStatus;
    private String failedStep;
    private String failedStepCallStack;

}
