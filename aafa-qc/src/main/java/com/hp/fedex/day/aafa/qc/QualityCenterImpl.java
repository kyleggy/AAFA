package com.hp.fedex.day.aafa.qc;

import com.hp.fedex.day.aafa.model.DefectResult;
import com.hp.fedex.day.aafa.model.TestResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhaok on 3/17/2015.
 */
public class QualityCenterImpl implements QualityCenter {

    @Override
    public Map<TestResult, DefectResult> getDefectResults(List<TestResult> testResultList) {
        return new HashMap<TestResult, DefectResult>();
    }
}
