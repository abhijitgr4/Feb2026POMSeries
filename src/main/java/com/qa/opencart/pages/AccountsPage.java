package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By logoutLink = By.linkText("Logout");
	private By accsHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil= new ElementUtil(driver);
	}

	public String getAccpageTitle() {
		String title= eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACC_PAGE_TITLE_VALUE);
		System.out.println("Account Page Title is: " + title);
		return title;
	}

	public String getAccntPageUrl() {
		String url= eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT, AppConstants.ACC_PAGE_URL_FRACTION_VALUE);
		System.out.println("Account Page URL is: " + url);
		return url; 
	}

	public boolean isLogoutlinkExists() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();

	}

	public boolean isSearchExists() {
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();

	}

	public List<String> getAccountsPageHeadersList() {
		
		List<WebElement>accHeaderList= eleUtil.waitForElementsVisible(accsHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> accheadersvalList= new ArrayList<String>();

		for(WebElement e: accHeaderList) {
			String text= e.getText();
			accheadersvalList.add(text);
		}
		return accheadersvalList;

	}
	
	public SearchPage performSearch(String searchkey) {
		if(isSearchExists()) {
			eleUtil.doSendKeys(search, searchkey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not present on the page...");
			return null;
		}
		
	}

}
