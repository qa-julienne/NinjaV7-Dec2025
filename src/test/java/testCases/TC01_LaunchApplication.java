package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApplication extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC01_LaunchApplication.class);

    @Test(groups = {"sanity", "regression"}, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testLauchApplication() {

        logger.debug("===== Starting TC01_LaunchApplication =====");

        try {
            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Fetching application title");
            String actualTitle = getDriver().getTitle();
            String expectedTitle = "Your store of fun";

            logger.debug("Actual Title: {}", actualTitle);
            logger.debug("Expected Title: {}", expectedTitle);

            try {
                AssertJUnit.assertEquals(actualTitle, expectedTitle);
                logger.debug("Title assertion PASSED");

            } catch (AssertionError ae) {
                logger.error("Title assertion FAILED", ae);
                captureScreen("TC01_LaunchApplication");
                throw ae; // required so TestNG marks it as failed and retry works
            }

        } catch (Exception e) {
            logger.error("Exception occurred during test execution", e);
            AssertJUnit.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("===== Finished TC01_LaunchApplication =====");
    }
}

	


