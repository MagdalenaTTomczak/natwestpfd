package com.runner.runner;

import com.runner.annotations.SuiteInformation;
import com.runner.annotations.TestInformation;
import com.runner.annotations.TestInformation.TestPriority;
import com.runner.annotations.TestInformation.TestType;
import com.runner.interfaces.EnhancedTestInterface;
import com.runner.report.ReportGenerator;
import com.runner.report.TestCase;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * INTERNAL USE ONLY. This is attached to the original notifier to abstract and
 * deliver the EnhancedTestInterface to the end user.
 * 
 * @author ryandixon1993@gmail.com
 */
public class EnhancedTestNotifier extends RunNotifier {

	private EnhancedTestInterface eti;

	private AtomicBoolean result;
	private String className;
	private String methodName;
	private long testStarted;

	private RunNotifier nativeNotifier;

	/*
	 * On Failure
	 */
	private String stack = "";
	private String message = "";
	
	public EnhancedTestNotifier(EnhancedTestInterface eti, RunNotifier notifier) {
		this.eti = eti;
		this.nativeNotifier = notifier;
		result = new AtomicBoolean(true);
		testStarted = System.currentTimeMillis();
	}

	public EnhancedTestNotifier(RunNotifier notifier) {
		this.nativeNotifier = notifier;
		result = new AtomicBoolean(true);
	}

	@Override
	public void fireTestStarted(Description description) throws StoppedByUserException {
		this.className = description.getClassName();
		this.methodName = description.getMethodName();

		if (eti != null) {
			try {
				eti.onTestStarted(className, methodName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		nativeNotifier.fireTestStarted(description);
		super.fireTestStarted(description);
	}

	@Override
	public void fireTestFailure(Failure failure) {
		result.set(false);

		if (eti != null)
			eti.onTestFailure(className, methodName, failure.getMessage(), failure.getTrace());

		this.message = failure.getMessage();
		this.stack = failure.getTrace();
		
		nativeNotifier.fireTestFailure(failure);
		super.fireTestFailure(failure);
	}

	@Override
	public void fireTestAssumptionFailed(Failure failure) {
		result.set(false);
		nativeNotifier.fireTestAssumptionFailed(failure);
		super.fireTestAssumptionFailed(failure);
	}

	@Override
	public void fireTestFinished(Description description) {
		if (eti != null)
			eti.onTestFinished(result.get(), className, methodName);

		if (result.get())
			fireTestPassed();
		nativeNotifier.fireTestFinished(description);

		if (result.get())
			processTestCase(className, methodName, "Passed");
		else
			processTestCase(className, methodName, "Failed");

		super.fireTestFinished(description);
	}

	@Override
	public void addFirstListener(RunListener listener) {
		nativeNotifier.addFirstListener(listener);
		super.addFirstListener(listener);
	}

	@Override
	public void addListener(RunListener listener) {
		nativeNotifier.addListener(listener);
		super.addListener(listener);
	}

	@Override
	public void fireTestIgnored(Description description) {
		if (eti != null)
			eti.onTestIgnored(className, methodName);

		nativeNotifier.fireTestIgnored(description);

		processTestCase(className, methodName, "Not Executed");
		super.fireTestIgnored(description);
	}

	@Override
	public void fireTestRunFinished(Result result) {
		nativeNotifier.fireTestRunFinished(result);
		super.fireTestRunFinished(result);
	}

	@Override
	public void fireTestRunStarted(Description description) {
		nativeNotifier.fireTestRunStarted(description);
		super.fireTestRunStarted(description);
	}

	@Override
	public void pleaseStop() {
		nativeNotifier.pleaseStop();
		super.pleaseStop();
	}

	@Override
	public void removeListener(RunListener listener) {
		nativeNotifier.removeListener(listener);
		super.removeListener(listener);
	}

	/*
	 * Additional Methods
	 */

	public void fireTestPassed() {
		if (eti != null)
			eti.onTestPassed(className, methodName);
	}

	private void processTestCase(String className, String methodName, String result) {
		try {
			Method[] method = Class.forName(className).getMethods();
			for (Method meth : method) {
				if (meth.getName().equals(methodName)) {
					if (meth.isAnnotationPresent(TestInformation.class)) {
						TestInformation testInformation = meth.getAnnotation(TestInformation.class);
						addToSuites(new TestCase(className, testInformation.testName(), testInformation.testDescription(),
								testInformation.expectedBehaviour(), testInformation.type(), testInformation.priority(),
								result, testStarted, message, stack), className);
					} else {
						System.out.println("No TestInformation annotation found for " + className + ":" + methodName
								+ ", using method name instead");
						addToSuites(new TestCase(className, methodName, "", "", TestType.AUTOMATIC, TestPriority.MEDIUM, result,
								testStarted, message, stack), className);
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addToSuites(TestCase testCase, String className) {
		try {
			if (Class.forName(className).isAnnotationPresent(SuiteInformation.class)) {
				SuiteInformation suiteInformation = Class.forName(className).getAnnotation(SuiteInformation.class);
				if (ReportGenerator.SUITES.containsKey(suiteInformation.suiteName())) {
					ReportGenerator.SUITES.get(suiteInformation.suiteName()).addTestCase(testCase);
				}
			} else {
				if (ReportGenerator.SUITES.containsKey(className)) {
					ReportGenerator.SUITES.get(className).addTestCase(testCase);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
