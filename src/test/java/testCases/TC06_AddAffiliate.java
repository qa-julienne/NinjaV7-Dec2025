package testCases;

import java.io.FileReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_AddAffiliate extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC06_AddAffiliate.class);
    public Properties p;

    @Test(groups = {"regression"}, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testAddAffiliate() {

        logger.debug("===== Starting TC06_AddAffiliate =====");

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
            lp.setEmail("juliennemckee7@gmail.com");
            lp.setPwd("apple1");
            lp.clickLogin();

            try {
                Thread.sleep(500);
                logger.debug("Waited after login");
            } catch (InterruptedException ie) {
                logger.warn("Thread sleep interrupted after login", ie);
                Thread.currentThread().interrupt();
            }

            // Account & Affiliate
            logger.debug("Initializing AccountPage");
            AccountPage ap = new AccountPage(getDriver());

            logger.debug("Navigating to Affiliate page");
            ap.goToAffiliate();

            logger.debug("Initializing AffiliatePage");
            AffiliatePage afp = new AffiliatePage(getDriver());

            logger.debug("Entering affiliate details");
            afp.setCompany("123 ABC");
            afp.setWebsite("www.123abc.com");
            afp.setTaxID("123456");
            afp.setCheque("Julienne McKee");

            logger.debug("Submitting affiliate form");
            afp.clickContinue();

            boolean status = afp.getAffiliateAccountConfirmation().isDisplayed();
            logger.debug("Affiliate confirmation displayed: {}", status);

            try {
                AssertJUnit.assertTrue(status);
                logger.debug("Add Affiliate assertion PASSED");

            } catch (AssertionError ae) {
                logger.error("Add Affiliate assertion FAILED", ae);
                captureScreen("TC06_AddAffiliate");
                throw ae; // required for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during TC06_AddAffiliate execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("===== Finished TC06_AddAffiliate =====");
    }
}
