package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPagenInfoTest extends BaseTest {

	@BeforeClass
	public void ProductPagenInfoPagesetup() {
		accPage = loginPage.doLogin(prop.getProperty("username1").trim(), prop.getProperty("password1").trim());
	}

	@Test(dataProvider = "getProductImagesTestData")
	public void getProductImagesCountTest(String searchkey, String productName, int imagesCount) {
		searchPage = accPage.performSearch(searchkey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagescount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagescount, imagesCount);
	}

	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] { 
			{ "MacBook", "MacBook Air", 4 }, 
			{ "iMac", "iMac", 3 },
			{ "Apple", "Apple Cinema 30\"", 6 }, 
			{ "Samsung", "Samsung SyncMaster 941BW", 1 } 
		};
	}

	@DataProvider
	public Object[][] getProductContentTestData(){
		return new Object[][] {
			{"MacBook", "MacBook Air", "MacBook Air is ultrathin, ultraportable, and ultra unlike anything else. But you don’t lose inches and pounds overnight. It’s the result of rethinking conventions. Of multiple wireless innovations. And of breakthrough design. With MacBook Air, mobile computing suddenly has a new standard."},
			{"iMac", "iMac", "Just when you thought iMac had everything, now there´s even more. More powerful Intel Core 2 Duo processors. And more memory standard. Combine this with Mac OS X Leopard and iLife ´08, and it´s more all-in-one than ever. iMac packs amazing performance into a stunningly slim space."},
			{"Samsung", "Samsung SyncMaster 941BW", "Imagine the advantages of going big without slowing down. The big 19\" 941BW monitor combines wide aspect ratio with fast pixel response time, for bigger images, more room to work and crisp motion. In addition, the exclusive MagicBright 2, MagicColor and MagicTune technologies help deliver the ideal image in every situation, while sleek, narrow bezels and adjustable stands deliver style just the way you want it. With the Samsung 941BW widescreen analog/digital LCD monitor, it's not hard to imagine."}
		};
	}

	@Test(dataProvider = "getProductContentTestData")
	public void getProductContentTest(String searchkey, String productName, String expectedDesc) {
		searchPage= accPage.performSearch(searchkey);
		productInfoPage= searchPage.selectProduct(productName);
		String productContent= productInfoPage.getProductContent();
		Assert.assertEquals(productContent, expectedDesc);
	
}
	
	@Test
	public void productInfoTest() {
		searchPage= accPage.performSearch("MacBook");
		productInfoPage= searchPage.selectProduct("MacBook Air");
		Map<String, String> actProductInfoMap= productInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple11");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 17");
		softAssert.assertEquals(actProductInfoMap.get("productprice"), "$1,202.00");
		
		softAssert.assertAll();
	}
	
	
	@DataProvider
	public Object[][] getProductQtyTestData() {
		return new Object[][] { 
			{ "MacBook", 3 },
			{ "MacBook Air", 1 }
		};
	}
	
	@Test(dataProvider = "getProductQtyTestData")
	public void addToCartTest(String productName, int quantity) {
		searchPage= accPage.performSearch("MacBook");
		productInfoPage= searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(quantity);
		String actCartMesg= productInfoPage.addPrducttoCart();
		softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
		//softAssert.assertTrue(actCartMesg.indexOf("MacBook Air")>=0);
		
		softAssert.assertEquals(actCartMesg, "Success: You have added " + productName + " to your shopping cart!");
		softAssert.assertAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
