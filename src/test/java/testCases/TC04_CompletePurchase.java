package testCases;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LaptopPage;
import pageObjects.LaptopsAndNotebooksPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC04_CompletePurchase extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC04_CompletePurchase.class);
    public Properties p;

    @Test(groups = {"sanity", "regression"}, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testCompletePurchase() {

        logger.debug("===== Starting TC04_CompletePurchase =====");

        try {
            // Load config
            logger.debug("Loading config.properties");
            FileReader file = new FileReader(".//src//test//resources//config.properties");
            p = new Properties();
            p.load(file);

            // Home Page
            logger.debug("Initializing HomePage");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Navigating to Laptops & Notebooks");
            hp.clickLaptopsAndNotebooks();
            hp.goToShowAll();

            try {
                Thread.sleep(500);
                logger.debug("Waited for product list to load");
            } catch (InterruptedException ie) {
                logger.warn("Thread sleep interrupted", ie);
                Thread.currentThread().interrupt();
            }

            // Product selection
            logger.debug("Initializing LaptopsAndNotebooksPage");
            LaptopsAndNotebooksPage lnp = new LaptopsAndNotebooksPage(getDriver());
            lnp.clickLaptop();

            logger.debug("Initializing LaptopPage");
            LaptopPage lp = new LaptopPage(getDriver());

            logger.debug("Setting delivery date");
            lp.setDeliveryDate();

            logger.debug("Adding product to cart");
            lp.clickAddToCart();

            logger.debug("Proceeding to checkout");
            lp.clickCheckout();

            // Checkout
            logger.debug("Initializing CheckoutPage");
            CheckoutPage cp = new CheckoutPage(getDriver());

            logger.debug("Navigating to login section in checkout");
            cp.goToLoginP2();

            logger.debug("Logging in during checkout");
            LoginPage login = new LoginPage(getDriver());
            login.setEmail("juliennemckee7@gmail.com");   // recommended from config
            login.setPwd("apple1");
            login.clickLogin();

            logger.debug("Selecting shipping address");
            cp.selectShippingAddress();

            logger.debug("Choosing shipping method");
            cp.chooseShippingMethod();
            cp.continueShippingMethod();

            logger.debug("Choosing payment method");
            cp.choosePaymentMethod();
            cp.continuePaymentMethod();

            logger.debug("Confirming order");
            cp.selectConfirm();

            boolean status = cp.getOrderConfirmation().isDisplayed();
            logger.debug("Order confirmation displayed: {}", status);

            try {
                AssertJUnit.assertTrue(status);
                logger.debug("Order confirmation assertion PASSED");

            } catch (AssertionError ae) {
                logger.error("Order confirmation assertion FAILED", ae);
                captureScreen("TC04_CompletePurchase");
                throw ae; // required for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during TC04_CompletePurchase execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("===== Finished TC04_CompletePurchase =====");
    }
}

