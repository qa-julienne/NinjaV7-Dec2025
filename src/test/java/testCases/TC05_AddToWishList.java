package testCases;

import java.io.FileReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LaptopPage;
import pageObjects.LaptopsAndNotebooksPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC05_AddToWishList extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC05_AddToWishList.class);
    public Properties p;

    @Test(groups = {"regression"}, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testAddToWishList() {

        logger.debug("===== Starting TC05_AddToWishList =====");

        try {
            // Load config
            logger.debug("Loading config.properties");
            FileReader file = new FileReader(".//src//test//resources//config.properties");
            p = new Properties();
            p.load(file);

            // Login flow
            logger.debug("Initializing HomePage");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Navigating to Login page");
            hp.clickMyAccount();
            hp.goToLogin();

            logger.debug("Initializing LoginPage");
            LoginPage lp = new LoginPage(getDriver());

            logger.debug("Entering login credentials");
            lp.setEmail("test@email.com");
            lp.setPwd("apple1");
            lp.clickLogin();

            try {
                Thread.sleep(500);
                logger.debug("Waited after login");
            } catch (InterruptedException ie) {
                logger.warn("Thread sleep interrupted after login", ie);
                Thread.currentThread().interrupt();
            }

            // Navigate to product
            logger.debug("Navigating to Laptops & Notebooks");
            hp.clickLaptopsAndNotebooks();
            hp.goToShowAll();

            try {
                Thread.sleep(500);
                logger.debug("Waited for product list to load");
            } catch (InterruptedException ie) {
                logger.warn("Thread sleep interrupted on product list", ie);
                Thread.currentThread().interrupt();
            }

            // Select product
            logger.debug("Initializing LaptopsAndNotebooksPage");
            LaptopsAndNotebooksPage lnp = new LaptopsAndNotebooksPage(getDriver());
            lnp.clickLaptop();

            logger.debug("Initializing LaptopPage");
            LaptopPage lp1 = new LaptopPage(getDriver());

            logger.debug("Clicking Add to Wish List");
            lp1.clickAddtoWishList();

            String actualMessage = lp1.getSuccessMessage().getText();
            logger.debug("Wishlist success message: {}", actualMessage);

            try {
                AssertJUnit.assertTrue(actualMessage.contains("Success"));
                logger.debug("Add to Wishlist assertion PASSED");

            } catch (AssertionError ae) {
                logger.error("Add to Wishlist assertion FAILED", ae);
                captureScreen("TC05_AddToWishList");
                throw ae; // required for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during TC05_AddToWishList execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("===== Finished TC05_AddToWishList =====");
    }
}
