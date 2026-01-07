package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LaptopPage;
import pageObjects.LaptopsAndNotebooksPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC03_AddToCart extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC03_AddToCart.class);

    @Test(groups = {"sanity", "regression"}, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testAddtoCart() {

        logger.debug("===== Starting TC03_AddToCart =====");

        try {
            // Home page actions
            logger.debug("Initializing HomePage");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking Laptops & Notebooks menu");
            hp.clickLaptopsAndNotebooks();

            logger.debug("Clicking Show All");
            hp.goToShowAll();

            try {
                Thread.sleep(500);
                logger.debug("Waited for product list to load");
            } catch (InterruptedException ie) {
                logger.warn("Thread sleep interrupted", ie);
                Thread.currentThread().interrupt();
            }

            // Laptops listing page
            logger.debug("Initializing LaptopsAndNotebooksPage");
            LaptopsAndNotebooksPage lnp = new LaptopsAndNotebooksPage(getDriver());

            logger.debug("Clicking on a laptop");
            lnp.clickLaptop();

            // Laptop detail page
            logger.debug("Initializing LaptopPage");
            LaptopPage lp = new LaptopPage(getDriver());

            logger.debug("Setting delivery date");
            lp.setDeliveryDate();

            logger.debug("Clicking Add to Cart");
            lp.clickAddToCart();

            // Validation
            String actualMessage = lp.getMyActualMessageConfirmation().getText();
            logger.debug("Add to Cart confirmation message: {}", actualMessage);

            try {
                AssertJUnit.assertTrue(actualMessage.contains("Success"));
                logger.debug("Add to Cart assertion PASSED");

            } catch (AssertionError ae) {
                logger.error("Add to Cart assertion FAILED", ae);
                captureScreen("TC03_AddToCart");
                throw ae; // required for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during TC03_AddToCart execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("===== Finished TC03_AddToCart =====");
    }
}

