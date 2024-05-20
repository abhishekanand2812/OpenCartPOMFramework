package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {
	WebDriver driver;
	OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	// Utilizing of ThreadLocal in order to solve problems in parallel execution
	// It says give me a local copy of driver for each and every thread without
	// deadlock condition
	// Each thread will get the same individual copy of the driver
	// It's a java feature
	// 2methods: get and set

	public static String highlightElement;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();

		// String browserName = System.getProperty("browser");//if we want to read from
		// maven command line
		System.out.println("Browser name is: " + browserName);

		highlightElement = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;

		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));

			break;

		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));

			break;

		default:
			System.out.println("Please pass the right browser: " + browserName);
			throw new FrameException("NOROWSERFOUNDEXCEPTION");

		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	// synchronized keyword because all the thread will get same copy in sync
	// benefit is while taking screenshot , we can call this method by using class
	// name,that's why it is static
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {

		// mvn clean install -Denv="qa"
		Properties prop = new Properties();// created object of Properties class
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		try {
			if (envName == null) {
				System.out.println("No env given... Hence,running testcases on QA environment");
				ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
			} else {
				System.out.println("Running testcases on the environment: " + envName);

				switch (envName.toLowerCase().trim()) {

				case "qa":
					ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/main/resources/config/dev.config.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/main/resources/config/stage.config.properties");
					break;

				case "uat":
					ip = new FileInputStream("./src/main/resources/config/uat.config.properties");
					break;

				default:
					System.out.println("Please pass the right environment name..." + envName);
					throw new FrameException("NOVALIDENVIRONMENTGIVEN");

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// if file is not available, it will give FileNotFoundException
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;

	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
