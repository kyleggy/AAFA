package com.hp.fedex.day.aafa.analyzer;

import com.hp.fedex.day.aafa.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhaok on 3/17/2015.
 */
public class AnalyzerImpl implements Analyzer {

    @Override
    public List<AnalyzeResult> analyzeTestData(Map<TestResult, DefectResult> resultDefectTestMap) {
        List<AnalyzeResult> analyzeResults = new ArrayList<AnalyzeResult>();
        //Inject Mock Result
        resultDefectTestMap = AnalyzerHelper.getDefectResult();
        for(Map.Entry<TestResult, DefectResult> entry : resultDefectTestMap.entrySet() ) {
            TestResult key = entry.getKey();
            DefectResult value = entry.getValue();
            analyzeResults.add(analyzeResult(key, value));
        }
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
        }
        return analyzeResults;
    }

    private AnalyzeResult analyzeResult(TestResult testResult, DefectResult defectResult) {
        AnalyzeResult result = new AnalyzeResult();
        if(defectResult.getId() == null) {
            result.setAnalyzeStatus(AnalyzeStatus.Error);
        } else {
            if ( (testResult.getFailedStep() == defectResult.getFailedStep()) && testResult.getFailedStepCallStack() == defectResult.getFailedStepCallStack()) {
                if (defectResult.getDefectStatus() == DefectStatus.New || defectResult.getDefectStatus() == DefectStatus.Open) {
                    result.setAnalyzeStatus(AnalyzeStatus.Success);
                } else {
                    result.setAnalyzeStatus(AnalyzeStatus.Warn);
                }
            } else {
                result.setAnalyzeStatus(AnalyzeStatus.Error);
            }
        }

        result.setTestResult(testResult);
        result.setDefectResult(defectResult);
        return result;
    }
}
