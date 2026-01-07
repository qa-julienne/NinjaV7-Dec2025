package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    // Log4j Logger
    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties p;

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = {"sanity", "regression", "datadriven"})
    @Parameters({"os", "browser"})
    public void openApp(String os, String br) {

        logger.debug("===== Starting openApp() =====");
        logger.debug("OS: {}, Browser: {}", os, br);

        try {
            FileReader file = new FileReader(".//src//test//resources//config.properties");
            p = new Properties();
            p.load(file);
            logger.debug("Config properties loaded successfully");
            

            WebDriver localDriver = null;
            
            
            if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();

				// os
				if (os.equalsIgnoreCase("windows")) {
					capabilities.setPlatform(Platform.WIN11);
				} else if (os.equalsIgnoreCase("mac")) {
					capabilities.setPlatform(Platform.MAC);
				} else {
					System.out.println("No matching os");
					return;
				}

				String gridURL = "http://localhost:4444/wd/hub"; // Update if needed
				//String gridURL = "http://10.0.0.178:4444/wd/hub"; // this will also work
				
				switch (br.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), chromeOptions.merge(capabilities));
					break;

				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), firefoxOptions.merge(capabilities));
					break;

				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), edgeOptions.merge(capabilities));
					break;

				default:
					logger.error("No matching browser found: {}", br);
					return;
				}

            }
			
			
			
			if (p.getProperty("execution_env").equalsIgnoreCase("local")) 
			{

            switch (br.toLowerCase()) {
                case "chrome":
                    logger.debug("Launching Chrome browser");
                    localDriver = new ChromeDriver();
                    break;

                case "edge":
                    logger.debug("Launching Edge browser");
                    localDriver = new EdgeDriver();
                    break;

                case "firefox":
                    logger.debug("Launching Firefox browser");
                    localDriver = new FirefoxDriver();
                    break;

                default:
                    logger.error("Invalid browser name: {}", br);
                    throw new RuntimeException("Invalid Browser: " + br);
            }
			}
			
            driver.set(localDriver);
            logger.debug("WebDriver instance set to ThreadLocal");

            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            logger.debug("Implicit wait applied");

            String appUrl = p.getProperty("appURL");
            logger.debug("Navigating to URL: {}", appUrl);

            getDriver().get(appUrl);
            getDriver().manage().window().maximize();

            logger.debug("Browser launched and maximized successfully");

        } catch (IOException e) {
            logger.error("Failed to load config.properties file", e);
            throw new RuntimeException(e);

        } catch (Exception e) {
            logger.error("Exception occurred while opening application", e);
            throw new RuntimeException(e);
        }
        }

    @AfterClass(groups = {"sanity", "regression", "datadriven"})
    public void closeApp() {

        logger.debug("===== Starting closeApp() =====");

        try {
            if (getDriver() != null) {
                getDriver().quit();
                logger.debug("Browser closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error occurred while closing the browser", e);
        } finally {
            driver.remove();
            logger.debug("ThreadLocal WebDriver removed");
        }
    }

    public String captureScreen(String tname) {

        logger.debug("Capturing screenshot for test: {}", tname);

        String targetFilePath = null;

        try {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

            targetFilePath = System.getProperty("user.dir")
                    + "//screenshots//"
                    + tname + "_" + timeStamp + ".png";

            File targetFile = new File(targetFilePath);
            sourceFile.renameTo(targetFile);

            logger.debug("Screenshot saved at: {}", targetFilePath);

        } catch (Exception e) {
            logger.error("Failed to capture screenshot for test: " + tname, e);
        }

        return targetFilePath;
    }
}

