package com.hp.fedex.day.aafa.reporter;

import com.hp.fedex.day.aafa.model.AnalyzeResult;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuling on 3/18/2015.
 */
public class ReporterImpl implements Reporter {
    @Override
    public void generateReport(List<AnalyzeResult> analyzeResultList) {
        build(analyzeResultList);
    }

    private void build(List<AnalyzeResult> analyzeResultList) {
        StyleBuilder boldStyle = DynamicReports.stl.style().bold();
        StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle)
                                            .setBorder(DynamicReports.stl.pen1Point())
                                            .setBackgroundColor(Color.LIGHT_GRAY)
                                            .setFontSize(12);
        StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setFontSize(25);

        TextColumnBuilder<String> analyzeStatusColumn = DynamicReports.col.column("Analysis Status", "status", DynamicReports.type.stringType()).setStyle(boldStyle);
        ConditionalStyleBuilder condition1 = DynamicReports.stl.conditionalStyle(DynamicReports.cnd.equal(analyzeStatusColumn, "Success"))
                                                .setBackgroundColor(Color.green);
        ConditionalStyleBuilder condition2 = DynamicReports.stl.conditionalStyle(DynamicReports.cnd.equal(analyzeStatusColumn, "Warn"))
                .setBackgroundColor(Color.yellow);
        ConditionalStyleBuilder condition3 = DynamicReports.stl.conditionalStyle(DynamicReports.cnd.equal(analyzeStatusColumn, "Error"))
                .setBackgroundColor(Color.red);

        JasperReportBuilder report=DynamicReports.report();
        try {
            report.columns(
                    DynamicReports.col.column("Test Name", "testCaseName", DynamicReports.type.stringType()),
                    analyzeStatusColumn,
                    DynamicReports.col.column("Analysis Result", "analysisResult", DynamicReports.type.stringType()),
                    DynamicReports.col.column("Failed Step", "failedStep", DynamicReports.type.stringType()),
                    DynamicReports.col.column("Call Stack", "callStack", DynamicReports.type.stringType()))
                    .setColumnTitleStyle(columnTitleStyle)
                    .detailRowHighlighters(condition1, condition2, condition3)
                    .title(DynamicReports.cmp.text("Analysis Report").setStyle(titleStyle).setFixedHeight(60))
                    .pageFooter(DynamicReports.cmp.pageXofY().setStyle(boldCenteredStyle))
                    .setPageFormat(1200, 1500, PageOrientation.PORTRAIT)
                    .setDataSource(createDataSource(analyzeResultList))
                    .export(DynamicReports.export.htmlExporter("C:/temp/test.html"));
//                    .show();
            java.awt.Desktop.getDesktop().open(new File("C:/temp/test.html"));
        } catch (DRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource(List<AnalyzeResult> analyzeResultList) {
        DRDataSource dataSource = new DRDataSource("testCaseName", "status", "analysisResult", "failedStep", "callStack");
//        for(AnalyzeResult analyzeResult : analyzeResultList) {
//            ReporterViewData reporterViewData = new ReporterViewData();
//            reporterViewData.build(analyzeResult);
//            dataSource.add(reporterViewData.getTestCaseName(), reporterViewData.getStatus(), reporterViewData.getAnalysisResult(),
//                    reporterViewData.getFailedStep(), reporterViewData.getCallStack());
//        }

        dataSource.add("Test A", "Success", "analysisResult", "failedStep" , "callStack");
        dataSource.add("Test B", "Warn", "analysisResult", "failedStep", "callStack");
        dataSource.add("Test C", "Error", "analysisResult", "failedStep", "callStack");
        dataSource.add("Test D", "Success", "analysisResult", "failedStep", "callStack");
        return dataSource;
    }

    public static void main( String[] args ) {
        List list = new ArrayList();
        Reporter reporter = new ReporterImpl();
        reporter.generateReport(list);
    }


}
