package com.hp.fedex.day.aafa.reporter;

import com.hp.fedex.day.aafa.model.AnalyzeResult;
import com.hp.fedex.day.aafa.model.DefectResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuling on 3/18/2015.
 */

@Data
public class ReporterViewData {
    private String testCaseName;
    private String status;
    private String analysisResult;
    private String failedStep;
    private String callStack;

    public void build(AnalyzeResult analyzeResult) {
        this.testCaseName = analyzeResult.getTestResult().getTestCaseName();
        this.status = analyzeResult.getAnalyzeStatus().toString();
        if(analyzeResult.getDefectResult().getId() != null) {
            this.analysisResult = "DefectID: " + analyzeResult.getDefectResult().getId()
                    + " Title: " + analyzeResult.getDefectResult().getTitle();
        } else {
            this.analysisResult = "N/A";
        }

        this.failedStep = analyzeResult.getDefectResult().getFailedStep();
        this.callStack = analyzeResult.getDefectResult().getFailedStepCallStack();
    }

}
