package com.hp.fedex.day;


import com.hp.fedex.day.aafa.model.AnalyzeResult;
import com.hp.fedex.day.aafa.model.AnalyzeStatus;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setAnalyzeStatus(AnalyzeStatus.Success);
                System.out.println(analyzeResult.getAnalyzeStatus());
    }
}
