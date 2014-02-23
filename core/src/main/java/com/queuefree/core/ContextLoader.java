package com.queuefree.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextLoader {
	public static void main(String[] args) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				"META-INF/applicationContext.xml");
		
		waitForShutdown(context);
		

	}

	private static void waitForShutdown(final ApplicationContext context) {
		Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            	synchronized (context) {
            		((ConfigurableApplicationContext)context).close();
            		context.notifyAll();
				}
            }
        });

		try {
			synchronized (context) {
				context.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
