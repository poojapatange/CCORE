package CCOREAutomation;

//pooja 02-01-19

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CCore_CallCenterTools_Pilot {
	ExtentReports extent;
	ExtentTest logger;
	static WebDriver driver;
	SendEMail objSendEMail = new SendEMail();
	private static int invalidImageCount;
	public static String chromeDriverPath = "/usr/bin/chromedriver";
	static WebElement searchfordata;

	@BeforeTest
	public void startReport() {
		// ExtentReports(String filePath,Boolean replaceExisting)
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/STMExtentReport.html", true);
		// extent.addSystemInfo("Environment","Environment Name")
		extent.addSystemInfo("Host Name", "Citruss").addSystemInfo("Environment", "Automation Testing")
				.addSystemInfo("User Name", "Pooja PS");
		extent.loadConfig(new File(System.getProperty("user.dir") + "//extent-config.xml"));
	}

	// This method is to capture the screenshot and return the path of the
	// screenshot.

	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@Test
	public void passTest() {
		// extent.startTest("TestCaseName", "Description")

		logger = extent.startTest("passTest");
		Assert.assertTrue(true);
		// To generate the log when the test case is passed
		logger.log(LogStatus.PASS, "Test Case Passed is passTest");
	}

	@Test
	public void failTest() throws InterruptedException {

		logger = extent.startTest("failTest");
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize(); // Maximize screen
		driver.get("https://citool2.ctv-it.net/"); // Website URL
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Login page with valid Credentials
		
		String Loginpage =CCore_CallCenterTools_Pilot.Login(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(8000);


		String NewCustomer = CCore_CallCenterTools_Pilot.AddingNewCustomermethod(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(8000);

		String OldCustomer = CCore_CallCenterTools_Pilot.ExistingCustomermethod(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(8000);

		// validation
		if (NewCustomer.equals("fail") || OldCustomer.equals("fail") || Loginpage.equals("fail")) {
			Assert.assertEquals(NewCustomer, "verifying PilotV1");
			logger.log(LogStatus.FAIL, "Test Case (failTest) Status is failed");
		}

		
	}

	@Test
	public static String  Login(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		
		try {
		driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[1]/input"))
				.sendKeys("pooja.patange@citruss.com");
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[2]/input")).sendKeys("Welcome1!");
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/button")).click();
		Thread.sleep(8000);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "fail";
		}
		return "sucess";
			
		}
		
	

	@Test
	public static String AddingNewCustomermethod(WebDriver driver) throws InterruptedException {
		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			// System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);

				}
			}
			System.out.println("Total no. of invalid images are " + invalidImageCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("i came to pilotv1");
			driver.findElement(By.xpath("/html/body/app-root/app-ui-custom/app-navbar/div/div/div[2]")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);
			driver.findElement(By.xpath("/html/body/app-root/app-ui-custom/app-sidebar/div/div/ul[3]/li[3]/a/span"))
					.click();// pilots
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);
			driver.findElement(By.xpath(
					"/html/body/app-root/app-ui-custom/app-pilot/div/div/div/div/div/div/div/div/div/div[2]/app-customer/div/ul/li[3]/a/i"))
					.click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"phonenumber\"]")).sendKeys("971568974568");
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"countryCodeAdd\"]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"countryCodeAdd\"]/option[2]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"lang\"]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"lang\"]/option[3]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"firstname\"]")).sendKeys("Dubai");
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"lastname\"]")).sendKeys("citruss");
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"countrycode\"]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"countrycode\"]/option[6]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"currency\"]")).click();// add
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"currency\"]/option[3]")).click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id=\"formCust\"]/form/div[7]/div/button[1]")).click();
			Thread.sleep(6000);

			driver.findElement(
					By.xpath("/html/body/modal-container/div/div/app-modal-new-customer/div/div[3]/button[1]")).click();
			Thread.sleep(6000);
			System.out.println("new customer added sucessfully ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "sucess";
	}

	private static String ExistingCustomermethod(WebDriver driver2) {

		try {
			WebDriverWait wait=new WebDriverWait(driver, 20);
			driver.findElement(By.xpath(
					"/html/body/app-root/app-ui-custom/app-pilot/div/div/div/div/div/div/div/div/div/div[2]/app-customer/div/ul/li[1]/a"))
					.click();// click on phone to add phone number in address
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);
			WebElement ExistingNumber = driver
					.findElement(By.xpath("//*[@id=\"tblSearchCustomer\"]/tbody/tr[1]/td[2]/input"));
			Thread.sleep(5000);
			ExistingNumber.sendKeys("971589837434");
			ExistingNumber.sendKeys(Keys.ENTER);
			searchfordata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/app-ui-custom/app-pilot/div/div/div/div/div/div/div/div/div/div[3]/app-account/div/div/div/div/div[1]/div/div/div[1]/button[1]")));
			searchfordata.click();
			WebElement Search = driver.findElement(By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[1]/div[1]/div/div[2]/div/input"));
			Thread.sleep(8000);
			Search.click();
			Thread.sleep(8000);
			Search.sendKeys("balzano");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);
			Search.sendKeys(Keys.ENTER);			
			searchfordata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[4]/app-product-table/div/div/div[1]/div[1]/div[1]/img")));
			searchfordata.click();
			
			driver.findElement(By.xpath("//*[@id=\"onoffswitch-pilot\"]/div/div/div[3]/div/div/label/span[2]")).click();// visible
																														// products
			
			searchfordata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[4]/app-product-table/div/div/div[1]/div[1]/div[2]")));
			searchfordata.click();
			
			driver.findElement(
					By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[3]/button[4]"))//stock
					.click();// stock
			searchfordata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[4]/app-product-table/div/div/div[1]/div[2]/div/div[1]/app-availability/div/div[2]")));
			searchfordata.click();
			
			driver.findElement(
					By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[3]/button[2]"))//export
					.click();// export
			Thread.sleep(8000);
			driver.findElement(By.xpath("//*[@id=\"productSearch\"]/div/div/div/app-serach-block/div/div/div[3]/button[6]/i"))
					.click();// List view/grid view
			Thread.sleep(15000);
			

		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "sucess";
	}

	public static void verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			org.apache.http.HttpResponse response = client.execute(request);
			// verifying response code he HttpStatus should be 200 if not,
			// increment as invalid images count
			if (response.getStatusLine().getStatusCode() != 200)
				invalidImageCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void skipTest() {
		logger = extent.startTest("skipTest");
		// throw new SkipException("Skipping - This is not ready for testing ");
	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent reports using
			// "logger.addScreenCapture" method.
			String screenshotPath = CCore_Catalogue_Products.getScreenhot(driver, result.getName());
			System.out.println("Taken screenshot");
			objSendEMail.emailsend(screenshotPath);// send email
			System.out.println("Sent To Mail ID"); // To add it in the extent report
			// To add it in the extent report
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		// ending test
		// endTest(logger) : It ends the current test and prepares to create HTML report
		extent.endTest(logger);
	}

	@AfterTest
	public void endReport() {
		// writing everything to document
		// flush() - to write or update test information to your report.
		extent.flush();
		// Call close() at the very end of your session to clear all resources.
		// If any of your test ended abruptly causing any side-affects (not all logs
		// sent to ExtentReports, information missing), this method will ensure that the
		// test is still appended to the report with a warning message.
		// You should call close() only once, at the very end (in @AfterSuite for
		// example) as it closes the underlying stream.
		// Once this method is called, calling any Extent method will throw an error.
		// close() - To close all the operation
		extent.close();
	}
}
