package gov.nih.nci.cadsrapi.util;

import gov.nih.nci.cadsrapi.dao.orm.CleanerDAO;

/**
 * This is just a test runner and should not be used in production.
 * @comment created specifically for https://tracker.nci.nih.gov/browse/CADSRAPI-208
 * 
 * Setup:
 * 
 * Please add cadsr-objectcart/objectCart/software/src/resources/conf/system/web/WEB-INF/classes 
 * into the classpath before running this runner.
 */
public class TestRunner {
	private static int sleepTime = 60;

	public static void main(String[] args) {
		CleanerDAO cleaner = new CleanerDAO();
		try {
			int temp = Integer.valueOf(PropertiesLoader.getProperty("cart.cleaner.sleep.minutes"));  //Get property value
			sleepTime = temp;  //set to sleep time
		} catch (Exception ae) {
			ae.printStackTrace();
		}
		
		while (true) {
			try {
				System.out.println("Cleaner running");
				cleaner.clean();
				System.out.println("Cleaner stopped (sleeping...)");
				Thread.sleep(sleepTime*60*1000);  //Put thread to sleep, converting minutes to milliseconds
			} catch (InterruptedException ie){
				System.out.println(ie.getMessage());
				ie.printStackTrace();	
			}		
		}
		
	}
}
