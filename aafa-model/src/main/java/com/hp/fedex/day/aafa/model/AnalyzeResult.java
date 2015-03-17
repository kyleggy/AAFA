package com.hp.fedex.day.aafa.model;

import lombok.Data;

/**
 * Created by lizhaok on 3/17/2015.
 */

@Data
public class AnalyzeResult {
    private TestResult testResult;
    private DefectResult defectResult;
    private AnalyzeStatus analyzeStatus;

}
