package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

public class CheckoutPage extends BasePage
{

	//Constructor

	
	public CheckoutPage(WebDriver driver)
	{
		super(driver); //initialization comes from BasePage
	}
	
	//Locators
	
	
	@FindBy(xpath = "//strong[normalize-space()='login page']")
	WebElement link_Login;
	
	@FindBy(xpath = "//select[@id='input-shipping-address']")
	WebElement drp_SelectShippingAddress;
	
	@FindBy(xpath = "//button[@id='button-shipping-methods']")
	WebElement btn_ChooseShippingMethod;
	
	@FindBy (xpath = "//button[@id='button-shipping-method']")
	WebElement btn_ContinueShippingMethod;
	
	@FindBy(xpath = "//button[@id='button-payment-methods']")
	WebElement btn_ChoosePaymentMethod;
	
	@FindBy (xpath = "//button[@id='button-payment-method']")
	WebElement btn_ContinuePaymentMethod;
	
	@FindBy (xpath = "//button[@id='button-confirm']")
	WebElement btn_Confirm;

	@FindBy (xpath = "//h1[normalize-space()='Your order has been placed!']")
	WebElement confirmationText_OrderPlaced;


	
	
	//Action Methods

	public void goToLoginP2()
	{
		link_Login.click();
	}
	
	public void selectShippingAddress()
	{
		Select select = new Select(drp_SelectShippingAddress);
		select.selectByIndex(1);
	}
	
	public void chooseShippingMethod()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		btn_ChooseShippingMethod.click();
	}
	
	public void continueShippingMethod()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		btn_ContinueShippingMethod.click();
	}
	
	public void choosePaymentMethod()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		btn_ChoosePaymentMethod.click();
	}
	
	public void continuePaymentMethod()
	{
		btn_ContinuePaymentMethod.click();
	}
	
	
	public void selectConfirm()
	{
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", btn_Confirm);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		btn_Confirm.click();
		
	}
	
	public WebElement getOrderConfirmation()
	{
		return confirmationText_OrderPlaced;
	}
	
		
		
}
