package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage //HomePage is child of BasePage
{
	
	
	//Constructor
	
	
	public HomePage (WebDriver driver)
	{
		 super(driver);//initialization comes from BasePage
	}
	
	
	
	//Locators
	
	@FindBy(xpath = "//i[@class='fa-solid fa-user']")
	WebElement link_MyAccount;
	
	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement link_Login;
	
	@FindBy(xpath = "//a[normalize-space()='Laptops & Notebooks']")
	WebElement link_LaptopsAndNotebooks;
	
	@FindBy(xpath = "//a[normalize-space()='Show All Laptops & Notebooks']")
	WebElement link_ShowAll;
	

	
	//Action Methods
	
	public void clickMyAccount()
	{
		link_MyAccount.click();
	}
	
	public void goToLogin()
	{
		link_Login.click();
	}
	
	public void clickLaptopsAndNotebooks()
	{
		link_LaptopsAndNotebooks.click();
	}

	public void goToShowAll()
	{
		link_ShowAll.click();;
	}
}
