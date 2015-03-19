package com.hp.fedex.day.aafa.analyzer;

import com.hp.fedex.day.aafa.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhaok on 3/18/2015.
 */
public class AnalyzerHelper {

    private static final String testResultAName = "catalogManagement";
    private static final String testResultAFailedStep = "Edit the service offering and go to User options tab: choose the created Fulfillment plan";
    private static final String testResultAFailedStepCallStack = "Exception Occur: Failed to execute event: Checking Page Count = [1] of\n" +
            "Caused by: Could not located element!!! Expected page number to be [1] but was [0]\n" +
            "Stack Trace: \n" +
            "com.hp.maas.platform.ui.test.exception.MaasUIAutomationException: Could not located element!!! Expected page number to be [1] but was [0]\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.ExpectsImpl.verifyExpectedPagesCount(ExpectsImpl.java:458)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.ExpectsImpl.access$800(ExpectsImpl.java:24)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.ExpectsImpl$20.execute(ExpectsImpl.java:451)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.BaseDriverExecutorImpl$1.apply(BaseDriverExecutorImpl.java:107)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.BaseDriverExecutorImpl$1.apply(BaseDriverExecutorImpl.java:103)";
    private static final String testResultAQCId = "10905";
    private static DefectStatus testResultAQCStatus = DefectStatus.Open;
    private static final String testResultAQCTitle = "Check page count error happen on test:catalogManagement";

    private static final String testResultBName = "SelfServicePortalAdministrationThemeSettings";
    private static final String testResultBFailedStep = "start After - delete images";
    private static final String testResultBFailedStepCallStack = "Exception Occur: Failed to execute event: Clicking on\n" +
            "\n" +
            "Caused by: Unable to click not visible element\n" +
            "\n" +
            "Stack Trace: \n" +
            "com.hp.maas.platform.ui.test.exception.MaasUIAutomationException: Unable to click not visible element\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.ActionsImpl.atomicClick(ActionsImpl.java:134)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.ActionsImpl$4.execute(ActionsImpl.java:89)\n" +
            "     at com.hp.maas.platform.ui.test.selenium.wd.BaseDriverExecutorImpl$1.apply(BaseDriverExecutorImpl.java:107)";
    private static final String testResultBQCId = "11201";
    private static DefectStatus testResultBQCStatus = DefectStatus.Close;
    private static final String testResultBQCTitle = "Failed to delete Image Button on test:catalogManagement";

    private static final String testResultCName = "serviceLevelManagement";
    private static final String testResultCFailedStep = "Open Incident with High priority and open SLT tab";
    private static final String testResultCFailedStepCallStack = "FAILURE Checking Text Contains [3/11/2015 9:49:38 AM] in : 'LocatorData DATA_AID: next-target-time' in 'Incident Service Level Targets Section' in 'Incident SLTTab' in 'Service Level Targets Tab Content' in 'Tab' in 'Incident Entity Page'";
    private static final String testResultCQCId = null;

    public static List<TestResult> getTestResult() {

        TestResult testResultA = new TestResult();

        testResultA.setTestCaseName(testResultAName);
        testResultA.setFailedStep(testResultAFailedStep);
        testResultA.setFailedStepCallStack(testResultAFailedStepCallStack);
        testResultA.setFailed(true);

        TestResult testResultB = new TestResult();
        testResultB.setTestCaseName(testResultBName);
        testResultB.setFailedStep(testResultBFailedStep);
        testResultB.setFailedStepCallStack(testResultBFailedStepCallStack);
        testResultB.setFailed(true);

        TestResult testResultC = new TestResult();
        testResultC.setTestCaseName(testResultCName);
        testResultC.setFailedStep(testResultCFailedStep);
        testResultC.setFailedStepCallStack(testResultCFailedStepCallStack);
        testResultC.setFailed(true);

        List<TestResult> resultArrayList = new ArrayList<TestResult>();
        resultArrayList.add(testResultA);
        resultArrayList.add(testResultB);
        resultArrayList.add(testResultC);
        return  resultArrayList;

    }

    public static Map<TestResult, DefectResult> getDefectResult() {
        Map<TestResult, DefectResult> resultDefectResultMap = new HashMap<TestResult, DefectResult>();
        DefectResult defectResultA = new DefectResult();
        defectResultA.setId(testResultAQCId);
        defectResultA.setFailedStep(testResultAFailedStep);
        defectResultA.setFailedStepCallStack(testResultAFailedStepCallStack);
        defectResultA.setDefectStatus(testResultAQCStatus);
        defectResultA.setTitle(testResultAQCTitle);

        DefectResult defectResultB = new DefectResult();
        defectResultB.setId(testResultBQCId);
        defectResultB.setFailedStep(testResultBFailedStep);
        defectResultB.setFailedStepCallStack(testResultBFailedStepCallStack);
        defectResultB.setDefectStatus(testResultBQCStatus);
        defectResultB.setTitle(testResultBQCTitle);

        DefectResult defectResultC = new DefectResult();
        defectResultC.setId(testResultCQCId);
        defectResultC.setFailedStep(testResultCFailedStep);
        defectResultC.setFailedStepCallStack(testResultCFailedStepCallStack);


        resultDefectResultMap.put(getTestResult().get(0), defectResultA);
        resultDefectResultMap.put(getTestResult().get(1), defectResultB);
        resultDefectResultMap.put(getTestResult().get(2), defectResultC);

        return resultDefectResultMap;
    }

    public static List<AnalyzeResult> getAnalyzeResult() {
        List<AnalyzeResult> analyzeResults = new ArrayList<AnalyzeResult>();

        Map<TestResult, DefectResult> testResultDefectResultMap = getDefectResult();
        for(Map.Entry<TestResult, DefectResult> entry : testResultDefectResultMap.entrySet() ) {
            AnalyzeResult analyzeResultA = new AnalyzeResult();

            TestResult key = entry.getKey();
            DefectResult value = entry.getValue();
            analyzeResultA.setTestResult(key);
            analyzeResultA.setDefectResult(value);
            analyzeResults.add(analyzeResultA);

        }
        analyzeResults.get(0).setAnalyzeStatus(AnalyzeStatus.Success);
        analyzeResults.get(1).setAnalyzeStatus(AnalyzeStatus.Warn);
        analyzeResults.get(2).setAnalyzeStatus(AnalyzeStatus.Error);

        return analyzeResults;
    }



}
