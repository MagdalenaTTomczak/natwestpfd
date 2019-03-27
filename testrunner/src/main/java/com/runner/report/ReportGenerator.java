package com.runner.report;

import com.runner.annotations.Setup;
import com.runner.annotations.Setup.ReportType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * INTERNAL USE ONLY.
 * This class is the thread spawned on death of the test harness and is used to generate reports to the user.
 * @author ryandixon1993@gmail.com
 */
public class ReportGenerator extends Thread {

	private static Setup setup;
	
	private static String path = "";
	private static File outputFile;
	
	public static final HashMap<String, TestSuite> SUITES = new HashMap<String, TestSuite>();
	
	public ReportGenerator(Setup setup) {
		ReportGenerator.setup = setup;
	}
	
	@Override
	public synchronized void start() {
		// Detect Settings for Report Type
		System.out.println("Finished testing. getting reportable data from com.runner.annotations...");
		if (SUITES == null) {
			System.out.println("Something went wrong, could not generate com.runner.report.");
			return;
		} else {
			
		}
		
		System.out.println();
		System.out.println("Preparing com.runner.report");
		prepareReport();
		super.start();
	}

	private static void prepareFilePath(String reportName) {
		System.out.println("Preparing new com.runner.report");
		if (new File(path + reportName).exists()) {
			System.out.println("Found old test, deleting.");
			new File(path).delete();
		}
		System.out.println("Outputting com.runner.report to " + new File(path).getAbsolutePath() + File.separator + reportName);
		outputFile = new File(path + reportName);

		try {
			outputFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void prepareReport() {
		for (ReportType report : setup.reportType()) {
			switch (report) {
			case EXCEL:
				prepareFilePath("com.runner.report.xlsx");
				ExcelReport.generateExcelReport(outputFile);
				break;
			case JUNIT_XML:
			default:
				prepareFilePath("com.runner.report.xml");
				JUnitReport.generateJUnitReport(outputFile);
				break;
			}
		}
	}
	
	public static Setup getSetup() {
		return ReportGenerator.setup;
	}
	
}
