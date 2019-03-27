package com.runner.runner;

import java.util.LinkedList;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import com.runner.annotations.Setup;
import com.runner.annotations.SuiteInformation;
import com.runner.annotations.SuiteInformation.SuitePriority;
import com.runner.defaults.DefaultSetup;
import com.runner.interfaces.EnhancedTestInterface;
import com.runner.report.ReportGenerator;
import com.runner.report.TestSuite;

/**
 * A custom version of the JUnit Suite package. This must be called with RunWith to get the functionality out of this solution.
 * @author ryandixon1993@gmail.com
 */
public class EnhancedSuite extends Suite {

	private static EnhancedTestInterface eti;

	private static final String HEADER = "--- EJ4TR Runner - Version 1.2.0 Beta ---";
	
	private static boolean setupFound = false;
	private static boolean interfaceFound = false;

	public EnhancedSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(null, getRunners(getAnnotatedClasses(klass)));
	}

	private static Class<?>[] getAnnotatedClasses(Class<?> klass) throws InitializationError {
		System.out.println(HEADER);
		System.out.println("Performing initial configuration check.");

		Suite.SuiteClasses annotation = klass.getAnnotation(Suite.SuiteClasses.class);
		if (annotation == null) {
			throw new InitializationError(
					String.format("class '%s' must have a SuiteClasses annotation", klass.getName()));
		}

		// Test if class implements test interface
		// If so, bind to com.runner.runner
		if (EnhancedTestInterface.class.isAssignableFrom(klass)) {
			System.out.println("Found test interface! Binding to com.runner.runner.");
			interfaceFound = true;
			try {
				eti = (EnhancedTestInterface) klass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// Test if class implements Setup annotation
		if (klass.isAnnotationPresent(Setup.class)) {
			System.out.println("Found setup annotation! Binding to reporter.");
			Runtime.getRuntime().addShutdownHook(new ReportGenerator(klass.getAnnotation(Setup.class)));
			setupFound = true;
		}

		System.out.println();
		return annotation.value();
	}

	public static List<Runner> getRunners(Class<?>[] classes) throws InitializationError {
		// If setup is not passed in, then set default
		if (!setupFound) {
			Runtime.getRuntime().addShutdownHook(new ReportGenerator(DefaultSetup.class.getAnnotation(Setup.class)));
			System.out.println("No setup annotation found. Using com.runner.defaults.");
		}
		if (!interfaceFound) {
			System.out.println("No test interface found...");
		}

		List<Runner> runners = new LinkedList<Runner>();

		System.out.println("Searching for suite classes...");
		for (Class<?> klazz : classes) {
			System.out.println("Found new test suite: " + klazz.getName());
			getSuiteInformation(klazz);
			EnhancedTestRunner etr = new EnhancedTestRunner(klazz);
			if (eti != null) {
				etr.addTestInterface(eti);
			}
			runners.add(etr);
		}

		System.out.println();
		System.out.println("Starting suites...");
		System.out.println();
		return runners;
	}

	private static void getSuiteInformation(Class<?> klazz) {
		if (ReportGenerator.SUITES == null) {
			System.out.println("An unknown error occured.");
		} else {
			if (klazz.isAnnotationPresent(SuiteInformation.class)) {
				SuiteInformation suite = klazz.getAnnotation(SuiteInformation.class);
				if (!ReportGenerator.SUITES.containsKey(suite.suiteName())) {
					System.out.println("Found suite information! Binding to reporter.");
					ReportGenerator.SUITES.put(suite.suiteName(), new TestSuite(suite.suiteName(),
							suite.suiteDescription(), suite.priority(), suite.suiteAcceptanceCriteria()));
				}
			} else {
				if (!ReportGenerator.SUITES.containsKey(klazz.getName())) {
					System.out.println("No SuiteInformation annotation found in " + klazz.getName()
							+ ", using class name instead with com.runner.defaults.");
					ReportGenerator.SUITES.put(klazz.getName(),
							new TestSuite(klazz.getName(), "", SuitePriority.MEDIUM, new String[] {}));
				}
			}
		}
	}

}
