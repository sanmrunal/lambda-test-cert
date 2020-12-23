package certification;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WindowsChromeSeleniumGrid {

	private WebElement everyMonthElement, customerServiceElement, dropDownElement, checkboxElement, sliderElement,
			jenkinsImageElement, imageUploadElement, loginElement;
	private String actualPercentage, formattedOutput, toastMessageLocator, parentWindowHandle, childWindowHandle,
			jenkinsImageXpah, jenkinsImageURL, downloadedImageName, imageUploadCssLocator, downloadedImagePath;
	private WebDriverWait webDriverWait;
	private JavascriptExecutor javaScriptDriver;
	private RemoteWebDriver driver = null;
	private String username = "mrunal.sanganabhatla";
	private String accessKey = "RMrOsC2mFyQYI4puVMsiY4vcejXMvU0KfwZWfk1BHnth2T324A";

	@BeforeMethod
	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version", "87.0"); // If this cap isn't specified, it will just get the any
														// available one
		capabilities.setCapability("resolution", "1024x768");
		capabilities.setCapability("build", "Chrome Test");
		capabilities.setCapability("name", "Windows + Chrome");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {
			driver = new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		}

		driver.manage().window().maximize();

		webDriverWait = new WebDriverWait(driver, 5);
		javaScriptDriver = ((JavascriptExecutor) driver);
	}

	@Test
	public void loginTest() {
		/*
		 * Start by opening LambdaTest Selenium Playground from
		 * https://www.lambdatest.com/automation-demos/
		 */
		driver.get("https://www.lambdatest.com/automation-demos/");
		driver.findElement(By.cssSelector("input#username")).sendKeys("lambda");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("lambda123");

		// driver.findElement(By.className("applynow")).click();
		loginElement = driver.findElement(By.className("applynow"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(loginElement));
		javaScriptDriver.executeScript("arguments[0].click()", loginElement);

		/*
		 * Log in using the following credentials and mark the login test Passed once
		 * the login success toast disappears
		 */
		toastMessageLocator = "//div[@class='toast jam']";
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toastMessageLocator)));

		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(toastMessageLocator)));

	}

	@Test(dependsOnMethods = "loginTest")
	public void formPageValidation() throws IOException {

		/*
		 * Once you are on the form, fill in your registered email address in the first
		 * field and click on populate
		 */
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
		driver.findElement(By.name("email")).sendKeys("mrunal.sanganabhatla@gmail.com");
		driver.findElement(By.id("populate")).click();

		/* Engage with the alert pop-up */
		webDriverWait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();

		/*
		 * Answer the remaining questions on the feedback form engaging with the radio
		 * buttons, checkboxes, and dropdown.
		 */
		everyMonthElement = driver.findElement(By.id("month"));
		if (!everyMonthElement.isSelected()) {
			everyMonthElement.click();

		}
		Assert.assertTrue(everyMonthElement.isSelected(), "The every month radio button is NOT selected.");

		customerServiceElement = driver.findElement(By.name("customer-service"));
		if (!customerServiceElement.isSelected()) {
			customerServiceElement.click();

		}
		Assert.assertTrue(customerServiceElement.isSelected(), "The customer service checkbox is NOT enabled.");

		dropDownElement = driver.findElement(By.id("preferred-payment"));
		Select select = new Select(dropDownElement);
		select.selectByVisibleText("Wallets");

		/*
		 * Enable the rating scale and feedback text field from the respective checkbox
		 */
		checkboxElement = driver.findElement(By.cssSelector("input#tried-ecom"));
		if (!checkboxElement.isSelected()) {
			checkboxElement.click();

		}
		Assert.assertTrue(checkboxElement.isSelected(), "The checkbox is NOT enabled.");

		/*
		 * In the rating scale, set the ratings to 9 and assert if the selected position
		 * of the slider is as required
		 */
		sliderElement = driver.findElement(By.cssSelector(
				".ui-slider-handle.ui-slider-handle.ui-corner-all.ui-state-default.ui-corner-all.ui-state-default"));
		sliderElement.click();
		for (int i = 1; i < 9; i++) {
			sliderElement.sendKeys(Keys.RIGHT);
		}
		actualPercentage = sliderElement.getAttribute("style").replace("left: ", "").replace("%;", "");

		DecimalFormat decimalFormat = new DecimalFormat("#");
		decimalFormat.setRoundingMode(RoundingMode.CEILING);
		formattedOutput = decimalFormat.format(Double.parseDouble(actualPercentage)).toString();
		AssertJUnit.assertEquals(formattedOutput, "89", "slider mismatch happened");

		/*
		 * Open ​https://www.lambdatest.com/selenium-automation page in a new browser
		 * tab
		 */
		javaScriptDriver.executeScript("window.open()");
		Set<String> windowhandles = driver.getWindowHandles();

		Assert.assertEquals(windowhandles.size(), 2, "The second window did NOT open.");

		Iterator<String> windowhandlesIterator = windowhandles.iterator();
		parentWindowHandle = (String) windowhandlesIterator.next();
		childWindowHandle = (String) windowhandlesIterator.next();

		driver.switchTo().window(childWindowHandle);
		driver.get("https://www.lambdatest.com/selenium-automation");

		/*
		 * Wait for all the elements on the page to load completely Find the Jenkins
		 * logo image that appears on the CI/CD tools integration section and download
		 * the image file (please refer to the screenshot below)
		 */
		jenkinsImageXpah = "//img[@alt='LambdaTest Jenkins integration']";
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(jenkinsImageXpah)));
		jenkinsImageElement = driver.findElement(By.xpath(jenkinsImageXpah));

		jenkinsImageURL = jenkinsImageElement.getAttribute("src");
		downloadedImageName = "jenkins.svg";
		downloadedImagePath = System.getProperty("user.dir") + "/" + downloadedImageName;

		File downloadedImageFile = new File(downloadedImagePath);

		if (downloadedImageFile.exists()) {
			Assert.assertTrue(downloadedImageFile.delete(), "File delete Failed");
		}

		InputStream inputStream = new URL(jenkinsImageURL).openStream();
		Files.copy(inputStream, Paths.get(downloadedImagePath));

		/* Navigate back to the previous tab that has the Selenium playground open */
		driver.switchTo().window(parentWindowHandle);

		/* Upload the same image that you just downloaded from the automation page */
		imageUploadCssLocator = "label#img";
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(imageUploadCssLocator)));
		imageUploadElement = driver.findElement(By.xpath("//form[@action='/action_page.php']"));
		javaScriptDriver.executeScript("arguments[0].click()", imageUploadElement);

		driver.setFileDetector(new LocalFileDetector());

		driver.findElement(By.xpath("//input[@id='file']")).sendKeys(downloadedImagePath);

		/*
		 * Assert if the image is uploaded successfully and has the same name as the
		 * downloaded one from the console. Mark the upload test as Passed if the
		 * correct image uploads successfully
		 */
		webDriverWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(driver.switchTo().alert().getText().equalsIgnoreCase("your image upload sucessfully!!"),
				"Image upload failed.");
		driver.switchTo().alert().accept();

	}

	@Test(dependsOnMethods = "formPageValidation")
	public void submitForm() {
		/*
		 * Click on submit the form and assert that the form gets submitted Mark the
		 * submit test as Passed once the form gets submitted
		 */
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#submit-button")));
		javaScriptDriver.executeScript("arguments[0].click()",
				driver.findElement(By.cssSelector("button#submit-button")));
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[normalize-space()='You have successfully submitted the form.']"))
						.isDisplayed(),
				"Form Submission Failed.");
	}

	@AfterClass()
	public void afterClass() {
		/* Close browser tab and session */
		driver.close();
		driver.quit();
	}

}
