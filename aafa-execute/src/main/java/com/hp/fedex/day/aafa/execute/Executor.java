package com.hp.fedex.day.aafa.execute;

import com.hp.fedex.day.aafa.analyzer.Analyzer;
import com.hp.fedex.day.aafa.analyzer.AnalyzerImpl;
import com.hp.fedex.day.aafa.jenkins.Jenkins;
import com.hp.fedex.day.aafa.jenkins.JenkinsImpl;
import com.hp.fedex.day.aafa.model.AnalyzeResult;
import com.hp.fedex.day.aafa.model.DefectResult;
import com.hp.fedex.day.aafa.model.TestResult;
import com.hp.fedex.day.aafa.qc.QualityCenter;
import com.hp.fedex.day.aafa.qc.QualityCenterImpl;
import com.hp.fedex.day.aafa.reporter.Reporter;
import com.hp.fedex.day.aafa.reporter.ReporterImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhaok on 3/18/2015.
 */
public class Executor {

    public static void main( String[] args ) {
        Jenkins jenkins = new JenkinsImpl();
        List<TestResult> jenkinsTestResults = jenkins.getFailureTestResults("ess-saw", "24058", "master");

        QualityCenter qualityCenter = new QualityCenterImpl();
        Map<TestResult, DefectResult> testResultDefectResultMap = qualityCenter.getDefectResults(jenkinsTestResults);

        Analyzer analyzer = new AnalyzerImpl();
        List<AnalyzeResult> analyzeResults = analyzer.analyzeTestData(testResultDefectResultMap);

        Reporter reporter = new ReporterImpl();
        reporter.generateReport(analyzeResults);
    }


}
