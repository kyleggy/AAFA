package com.hp.fedex.day.aafa.jenkins;

import com.hp.fedex.day.aafa.model.TestResult;

import java.util.List;

/**
 * Created by lizhaok on 3/17/2015.
 */
public class JenkinsImpl implements Jenkins {

    @Override
    public List<TestResult> getFailureTestResults(String projectName, String buildId, String branchName) {
        return null;
    }
}
