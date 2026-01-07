package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AffiliatePage extends BasePage
{
	//Constructor
	public AffiliatePage(WebDriver driver)
	{
		super(driver); //initialization comes from BasePage
	}
	
	//Locators
	
			@FindBy(xpath = "//input[@id='input-company']")
			WebElement txt_Company;
			
			@FindBy(xpath = "//input[@id='input-website']")
			WebElement txt_WebSite;
			
			@FindBy(xpath = "//input[@id='input-tax']")
			WebElement txt_TaxID;
			
			@FindBy(xpath = "//input[@id='input-cheque']")
			WebElement txt_Cheque;
			
			@FindBy(xpath = "//button[normalize-space()='Continue']")
			WebElement btn_Continue;
			
			@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
			WebElement confirmationText_AffiliateAccount;
			
			
			//Action Methods
			
			public void setCompany(String cname)
			{
				txt_Company.clear();
				txt_Company.sendKeys(cname);
			}
			
			public void setWebsite(String wname)
			{
				txt_WebSite.clear();
				txt_WebSite.sendKeys(wname);
			}
			
			public void setTaxID(String id)
			{
				txt_TaxID.clear();
				txt_TaxID.sendKeys(id);
			}
			
			public void setCheque(String cheq)
			{
				txt_Cheque.clear();
				txt_Cheque.sendKeys(cheq);
			}
			
			public void clickContinue()
			{
				btn_Continue.click();
			}
			
			public WebElement getAffiliateAccountConfirmation()
			{
				return confirmationText_AffiliateAccount;
			}
			
}

