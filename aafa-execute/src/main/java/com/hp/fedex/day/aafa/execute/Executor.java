package com.hp.fedex.day.aafa.execute;

import com.hp.fedex.day.aafa.reporter.Reporter;
import com.hp.fedex.day.aafa.reporter.ReporterImpl;

/**
 * Created by lizhaok on 3/18/2015.
 */
public class Executor {


    public static void main( String[] args ) {
        Reporter reporter = new ReporterImpl();
        reporter.generateReport(DataGenerator.getAnalyzeResult());
    }


}
