package com.hp.fedex.day.aafa.reporter;

import com.hp.fedex.day.aafa.model.AnalyzeResult;
import com.hp.fedex.day.aafa.model.DefectResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuling on 3/18/2015.
 */


public class ReporterViewData {
    private String testCaseName;
    private String status;
    private String analysisResult;
    private String failedStep;
    private String callStack;

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getFailedStep() {
        return failedStep;
    }

    public void setFailedStep(String failedStep) {
        this.failedStep = failedStep;
    }

    public String getCallStack() {
        return callStack;
    }

    public void setCallStack(String callStack) {
        this.callStack = callStack;
    }

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
