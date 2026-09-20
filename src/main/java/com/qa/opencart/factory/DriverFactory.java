package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.qa.opencart.exception.FrameworkException;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String BrowserName = prop.getProperty("browser").toLowerCase().trim();

		if (BrowserName.equalsIgnoreCase("chrome")) {
			// driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver());
		} else if (BrowserName.equalsIgnoreCase("firefox")) {
			// driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver());
		} else {
			System.out.println("Please pass the correct BrowserName..." + BrowserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}

	// get local thread copy of driver
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);
		try {
			if (envName == null) {
				System.out.println("no env is passed...Running test cases on existing env...");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			}

			else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				default:
					System.out.println("Wrong env is passed...No need to run test cases.");
					throw new FrameworkException("Wrong env is passed.");
					//break;
				}
			}
		} catch (FileNotFoundException e) {

		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	// takes screenshot
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}

}
