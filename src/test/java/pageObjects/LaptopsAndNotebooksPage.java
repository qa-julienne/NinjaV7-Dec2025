package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LaptopsAndNotebooksPage extends BasePage
{
	//Constructor

	
		public LaptopsAndNotebooksPage(WebDriver driver)
		{
			super(driver); //initialization comes from BasePage
		}
		
		//Locators
		
		@FindBy(xpath = "//a[normalize-space()='HP LP3065']")
		WebElement link_Laptop;
		

		
		
		
		//Action Methods
		
		public void clickLaptop()
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", link_Laptop);
			try 
			 { Thread.sleep(500); } 
			 catch (InterruptedException e) 
			 {
			  e.printStackTrace(); }
			link_Laptop.click();
		}
		
	}

