package com.runner.runner;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.runner.interfaces.EnhancedTestInterface;

/**
 * INTERNAL USE ONLY.
 * The Runner used by the EnhancedSuite. This object should not be modified in any way.
 * @author ryandixon1993@gmail.com
 */
public class EnhancedTestRunner extends BlockJUnit4ClassRunner {

	private EnhancedTestInterface eti;

	public EnhancedTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	protected void addTestInterface(EnhancedTestInterface eti) {
		this.eti = eti;
	}
	
	@Override
	public void run(RunNotifier arg0) {
		super.run(arg0);
	}
	
	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description = describeChild(method);

		if (method.getAnnotation(Ignore.class) != null) {
			if (eti != null)
				eti.onTestIgnored(description.getClassName(), description.getMethodName());
			notifier.fireTestIgnored(description);
		} else {
			// Pass in notifier to retain native result parsing to the IDE
			if (eti != null)
				runLeaf(methodBlock(method), description, new EnhancedTestNotifier(eti, notifier));
			else
				runLeaf(methodBlock(method), description, new EnhancedTestNotifier(notifier));
		}
	}

}
