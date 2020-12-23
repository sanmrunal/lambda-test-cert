package selenium.parent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumBaseClass {

	public WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver",
				"/Users/mrunal/automation/learn/downloads/seleniumDrivers/chromedriver");
		WebDriver chromeDriver = new ChromeDriver();
		return chromeDriver;
	}

	public WebDriver getfirefoxDriver() {
		System.setProperty("webdriver.gecko.driver",
				"/Users/mrunal/automation/learn/downloads/seleniumDrivers/geckodriver");
		WebDriver firefoxDriver = new FirefoxDriver();
		return firefoxDriver;
	}

	public WebDriver getSafariDriver() {
		WebDriver safariDriver = new SafariDriver();
		return safariDriver;
	}

}
