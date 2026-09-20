package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.SearchPage;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPagesetup() {
		accPage = loginPage.doLogin(prop.getProperty("username1").trim(), prop.getProperty("password1").trim());
	}
	
	@Test
	public void AccpageTitleTest() {
		String actTitle = accPage.getAccpageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACC_PAGE_TITLE_VALUE);
	}
	@Test
	public void AccntPageUrlTest() {
		String actURL= accPage.getAccntPageUrl();
		Assert.assertTrue(actURL.contains(AppConstants.ACC_PAGE_URL_FRACTION_VALUE));
	}
	@Test
	public void isLogoutlinkExistsTest() {
		Assert.assertTrue(accPage.isLogoutlinkExists());
	}
	
	@Test
	public void isSearchExistsTest() {
		Assert.assertTrue(accPage.isSearchExists());
	}
	
	@Test
	public void getAccountsPageHeadersListTest() {
		List<String> actualAccPageHeadersList= accPage.getAccountsPageHeadersList();
		System.out.println("Account Page Headers list: "+ actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchkey) {
		searchPage= accPage.performSearch(searchkey);
		Assert.assertTrue(searchPage.getSearchProductsCount()>0);
	}
	
	@Test(dataProvider = "getProductTestData")
	public void searchProductTest(String searchkey, String productName) {
		searchPage= accPage.performSearch(searchkey);
		if(searchPage.getSearchProductsCount()>0) {
			productInfoPage= searchPage.selectProduct(productName);
			String actProductHeader= productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
	}
	
	
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"MacBook", "MacBook"},
			{"MacBook", "MacBook Air"},
			{"MacBook", "MacBook Pro"},
			{"iMac", "iMac"},
			{"Apple", "Apple Cinema 30\""},
			{"Samsung", "Samsung SyncMaster 941BW"}
		};
	}
	

}
