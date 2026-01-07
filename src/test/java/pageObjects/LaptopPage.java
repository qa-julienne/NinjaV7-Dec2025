package pageObjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.AssertJUnit;

public class LaptopPage extends BasePage 
{

	//Constructor

	
			public LaptopPage(WebDriver driver)
			{
				super(driver); //initialization comes from BasePage
			}
			
	//Locators
			
			
			@FindBy(id = "input-option-225")
			WebElement txt_DeliveryDate;
			
			@FindBy(xpath ="//button[@id='button-cart']")
			WebElement btn_AddToCart;
			
			@FindBy(xpath ="//div[@class='alert alert-success alert-dismissible']")
			WebElement confirmationText_ActualMessage;
			
			@FindBy(xpath = "//a[@title='Checkout']//i[@class='fa-solid fa-share']")
			WebElement btn_Checkout; 
			
			@FindBy(xpath = "//div//button//i[@class='fa-solid fa-heart']")
			WebElement btn_AddToWishList;
			
			@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
			WebElement confirmationText_SuccessMesssage;
			
		
			  
			
			
	//Action Methods
			
			public void setDeliveryDate()
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", txt_DeliveryDate);

				  LocalDate deliveryDate = LocalDate.now().plusDays(5);
				  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				  String dateValue = deliveryDate.format(formatter);

				  js.executeScript(
				      "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
				      txt_DeliveryDate,dateValue);
			
			}
			
			public void clickAddToCart()
			{
				btn_AddToCart.click();
			}
			
			public WebElement getMyActualMessageConfirmation()
			{
				return confirmationText_ActualMessage;
			}
			
			public void clickCheckout()
			{

				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn_Checkout);
				
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", btn_Checkout);
				
				try 
				 { Thread.sleep(500); } 
				 catch (InterruptedException e) 
				 {
				  e.printStackTrace(); }
				
				btn_Checkout.click();
			}
			
			public void clickAddtoWishList()
			{
				btn_AddToWishList.click();
			}
			
			public WebElement getSuccessMessage()
			{
				return confirmationText_SuccessMesssage;
			}
			
		}

