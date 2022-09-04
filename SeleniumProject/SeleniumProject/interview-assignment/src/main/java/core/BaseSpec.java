package core;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import utils.ApiUtils;
import utils.TestUtils;

public class BaseSpec {

	public static TestUtils testUtils = new TestUtils();
	ApiUtils apiUtils = new ApiUtils();
	Properties prop = new Properties();
	WebDriverFactory factory = new WebDriverFactory();
	String environment, userName, password, browser, testType, dataFile;
	File destinationFolder = new File(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator);

	/*
	 * @method : initializationAndBrowserInstantiation()
	 * 
	 * @description: All the actions those needs to be performed before start of
	 * each Scenario will be handled by this method.
	 * 
	 * @param : scenario : Current Scenario Object
	 */

	@Before
	public void initializationAndBrowserInstantiation(Scenario scenario) {
		try {
			// Load the properties
			prop.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "testProp.properties"));

			// Get the annotations and read the data file content
			List<String> scenarioLevelTags = (List<String>) scenario.getSourceTagNames();
			scenarioLevelTags.forEach(annotation -> {
				if (annotation.startsWith("@Data")) {
					dataFile = annotation.split("=")[1];
				}
				if (annotation.startsWith("@Type")) {
					testType = annotation.split("=")[1];
				}
			});
			if (!dataFile.equals("")) {
				testUtils.parseTestDataFile(System.getProperty("user.dir") + File.separator + "src" + File.separator
						+ "test" + File.separator + "resources" + File.separator + "testData" + File.separator
						+ dataFile);
			}
			System.out.println("Test Data is:" + testUtils.getTestData("Customer Details").get("FirstName"));
			System.out.println("Test Data is:" + testUtils.getTestData("Customer Details").get("LastName"));

			// Set the Login details
			environment = prop.getProperty("environment");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");
			browser = prop.getProperty("browser");

			// Launch the browser
			if (testType.equalsIgnoreCase("UI")) {
				if (browser.equals("chrome")) {
					testUtils.setWebDriver(factory.initializeChromeDriver(environment));
				} else if (browser.equals("firefox")) {
					testUtils.setWebDriver(factory.initializeFireFoxDriver(environment));
				}
			} else if (testType.equalsIgnoreCase("API")) {
				RestAssured.baseURI = "https://demoqa.com/Account/v1";
				apiUtils.login(userName, password);
			}
		} catch (Exception e) {
			System.out.println("Exception occured while initializing the driver" + e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * @method : captureScreenshot()
	 * 
	 * @description: All the actions those needs to be performed after each step in
	 * Scenario will be handled by this method.
	 */

	@AfterStep
	public void captureScreenshot(Scenario scenario) {
		try {
			TakesScreenshot screenShot = ((TakesScreenshot) testUtils.getWebDriver());
			File sourceFile = screenShot.getScreenshotAs(OutputType.FILE);
			if (!destinationFolder.exists()) {
				destinationFolder.mkdir();
			}
			FileUtils.copyFileToDirectory(sourceFile, destinationFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @method : closeDriver() : All the actions those needs to be performed after
	 * each Scenario will be handled by this method.
	 * 
	 * @param : scenario : Current Scenario Object
	 */

	@After
	public void closeDriver(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				captureScreenshot(scenario);
			}
			testUtils.getWebDriver().close();
		} catch (Exception e) {
			System.out.println("Exception Occured during Close Driver" + e.toString());
			e.printStackTrace();
		}
	}
}