package com.hp.fedex.day.aafa.jenkins;

import com.hp.fedex.day.aafa.model.TestResult;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lizhaok on 3/17/2015.
 */
public class JenkinsImpl implements Jenkins {

    @Override
    public List<TestResult> getFailureTestResults(String projectName, String buildId, String branchName) {
        String root = String.format("http://mydtbld0021.isr.hp.com:8080/jenkins/job/MaaS-Job-Tests-UI/GIT_BRANCH=%s,GIT_PROJECT=ESS,GIT_REPOSITORY=%s,TEST=ui,TEST_TYPE=e2e,USER_LOCALE=en,jdk=java-7u10/%s/artifact/target/reports/",
                branchName, projectName, buildId);
        ArrayList<TestResult> list = new ArrayList<TestResult>();
        HTMLParser parser = new HTMLParser(root);
        Hashtable<String, String> testCases = parser.retrieveFailedTestCases();
        Iterator<String> keySetIterator = testCases.keySet().iterator();
        while (keySetIterator.hasNext()) {

            String key = keySetIterator.next();
            TestResult testResult = new TestResult();
            testResult.setFailed(true);
            testResult.setTestCaseName(key);
            String url = parser.getDetailUrl(testCases.get(key));
            testResult.setFailedStep(parser.getFailedStep(url));
            testResult.setFailedStepCallStack(parser.getStackTrace(url));
            list.add(testResult);
        }

        return list;
    }
}
