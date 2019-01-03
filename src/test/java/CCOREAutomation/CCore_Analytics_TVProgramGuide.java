package CCOREAutomation;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//It is possible to attach screenshots. To add a screen-shot, simply call addScreenCapture. 
//This method returns the HTML with  tag which can be used anywhere in the log details.

public class CCore_Analytics_TVProgramGuide {
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	SendEMail objSendEMail = new SendEMail();
	private static int invalidImageCount;
	public static String chromeDriverPath = "/usr/bin/chromedriver";

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
		String Loginpage =CCore_Analytics_TVProgramGuide.Login(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(8000);

		// Methods
		String TVGuide = CCore_Analytics_TVProgramGuide.TvProgramGuideMethod(driver);
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String TVmbc = CCore_Analytics_TVProgramGuide.MBCTvProgramGuideMethod(driver);
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String zeealfam = CCore_Analytics_TVProgramGuide.ZeeAflamTvProgramGuideMethod(driver);
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String zeeAlwan = CCore_Analytics_TVProgramGuide.ZeeAlwanTvProgramGuideMethod(driver);
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String zeeliving = CCore_Analytics_TVProgramGuide.ZeeLivingTvProgramGuideMethod(driver);
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// validation
		if (Loginpage.equals("fail") || TVGuide.equals("fail") || TVmbc.equals("fail") || zeeAlwan.equals("fail") || zeealfam.equals("fail")
				|| zeeliving.equals("fail")) {
			Assert.assertEquals(Loginpage, "verifying TVGuide");
			Assert.assertEquals(TVGuide, "verifying TVGuide");
			Assert.assertEquals(TVmbc, "verifying TVGuide");
			Assert.assertEquals(zeeAlwan, "verifying TVGuide");
			Assert.assertEquals(zeealfam, "verifying TVGuide");
			
			logger.log(LogStatus.FAIL, "Test Case (failTest) Status is TVGuide");
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
	// to check whether image have any invalid/broken images
	public static String TvProgramGuideMethod(WebDriver driver) throws InterruptedException {

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
			// Automation Check for Module
			System.out.println("Tv Program Guide");
			driver.findElement(By.xpath("/html/body/app-root/app-ui-custom/app-navbar/div/div/div[2]")).click();// main
																												// menu
			Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.findElement(By.xpath("/html/body/app-root/app-ui-custom/app-sidebar/div/div/ul[5]/li[2]/a/span"))
					.click();// tv program
			// guide
			Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement FromDate = driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[1]/div/input"));// change
																													// date
			Thread.sleep(3000);
			FromDate.click();
			Thread.sleep(3000);
			FromDate.clear();
			Thread.sleep(3000);
			FromDate.sendKeys("13/12/2018");
			Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			WebElement TODate = driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[2]/div/div/input"));// change
																														// date
			Thread.sleep(3000);
			TODate.click();
			Thread.sleep(3000);
			TODate.clear();
			Thread.sleep(3000);
			TODate.sendKeys("13/12/2018");
			Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.findElement(
					By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[1]/span")).click();
			Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[2]/ul[2]/li[1]/div")).click();// citruss Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/button[1]")).click();// submit
																								// Thread.sleep(8000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			try {
				Boolean getstatus = driver.findElements(By.xpath(
						"/html/body/app-root/app-ui-custom/app-tv-program-guide/div/div/div/div/div/div/div/div/app-list-programs/div/div/div/div/div[1]/label"))
						.size()> 0;
			
				System.out.println(getstatus);
				if (getstatus.equals(true)) {

					System.out.println("Citruss TV Data Exists");

				} else {
					String shippingText = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]")).getText();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(5000);
					if (shippingText.equals("There is no data related to tv program guide")) {
						driver.findElement(By.xpath("/html/body/div/div/div[3]/button[1]")).click();
						Thread.sleep(5000);
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						System.out.println("Citruss TV No Data");
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "fail";
		}
		return "sucess";
	}

	public static String MBCTvProgramGuideMethod(WebDriver driver) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[1]/span"))
				.click();
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(
				By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[2]/ul[2]/li[2]/div"))
				.click();// MBC
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/button[1]")).click();// submit
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {

			Boolean getstatus = driver.findElements(By.xpath(
					"/html/body/app-root/app-ui-custom/app-tv-program-guide/div/div/div/div/div/div/div/div/app-list-programs/div/div/div/div/div[1]/label"))
					.size()> 0;

			System.out.println(getstatus);
			if (getstatus.equals(true) ) {

				System.out.println("MBC Data Exists");

			} else {
				String shippingText = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]")).getText();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(5000);
				if (shippingText.equals("There is no data related to tv program guide")) {

					driver.findElement(By.xpath("/html/body/div/div/div[3]/button[1]")).click();
					Thread.sleep(5000);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println("MBC No Data");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "fail";
		}
		return "sucess";
	}

	public static String ZeeAflamTvProgramGuideMethod(WebDriver driver) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[1]/span"))
				.click();
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(
				By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[2]/ul[2]/li[3]/div"))
				.click();// MBC
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/button[1]")).click();// submit
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			Boolean getstatus = driver.findElements(By.xpath(
					"/html/body/app-root/app-ui-custom/app-tv-program-guide/div/div/div/div/div/div/div/div/app-list-programs/div/div/div/div/div[1]/label"))
					.size()> 0;

			if (getstatus.equals(true) ) {

				System.out.println(" ZeeAflam Data Exists");

			} else {
				String shippingText = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]")).getText();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(5000);
				if (shippingText.equals("There is no data related to tv program guide")) {
					driver.findElement(By.xpath("/html/body/div/div/div[3]/button[1]")).click();
					Thread.sleep(5000);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println(" ZeeAflam No Data");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "fail";
		}
		return "sucess";
	}

	public static String ZeeAlwanTvProgramGuideMethod(WebDriver driver) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[1]/span"))
				.click();
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(
				By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[2]/ul[2]/li[4]/div"))
				.click();// ZeeAlwan
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/button[1]")).click();// submit
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			Boolean getstatus = driver.findElements(By.xpath(
					"/html/body/app-root/app-ui-custom/app-tv-program-guide/div/div/div/div/div/div/div/div/app-list-programs/div/div/div/div/div[1]/label"))
					.size()> 0;

			if (getstatus.equals(true) ) {

				System.out.println(" ZeeAlwan Data Exists");

			} else {
				String shippingText = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]")).getText();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(5000);
				if (shippingText.equals("There is no data related to tv program guide")) {
					driver.findElement(By.xpath("/html/body/div/div/div[3]/button[1]")).click();
					Thread.sleep(5000);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println(" ZeeAlwan No Data");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "fail";
		}
		return "sucess";
	}

	public static String ZeeLivingTvProgramGuideMethod(WebDriver driver) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[1]/span"))
				.click();
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(
				By.xpath("//*[@id=\"form-dashboard\"]/div/div[3]/ng-multiselect-dropdown/div/div[2]/ul[2]/li[5]/div"))
				.click();// MBC
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form-dashboard\"]/div/button[1]")).click();// submit
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		try {
			Boolean getstatus = driver.findElements(By.xpath(
					"/html/body/app-root/app-ui-custom/app-tv-program-guide/div/div/div/div/div/div/div/div/app-list-programs/div/div/div/div/div[1]/label"))
					.size()> 0;

			if (getstatus.equals(true) ) {

				System.out.println(" ZeeLiving Data Exists");

			} else {
				String shippingText = driver.findElement(By.xpath("//*[@id=\"swal2-content\"]")).getText();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(5000);
				if (shippingText.equals("There is no data related to tv program guide")) {
					driver.findElement(By.xpath("/html/body/div/div/div[3]/button[1]")).click();
					Thread.sleep(5000);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println(" ZeeLiving No Data");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
			String screenshotPath = CCore_Analytics_TVProgramGuide.getScreenhot(driver, result.getName());
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