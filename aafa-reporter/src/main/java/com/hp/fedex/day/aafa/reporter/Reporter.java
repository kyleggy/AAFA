package com.hp.fedex.day.aafa.reporter;

import com.hp.fedex.day.aafa.model.AnalyzeResult;

import java.util.List;

/**
 * Created by lizhaok on 3/17/2015.
 */
public interface Reporter {

    public void generateReport(List<AnalyzeResult> analyzeResultList);
}
