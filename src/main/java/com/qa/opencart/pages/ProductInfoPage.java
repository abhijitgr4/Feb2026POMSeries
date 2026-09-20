package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productheader = By.tagName("h1");
	private By productimages = By.cssSelector("ul.thumbnails img");
	private By productcontent = By.cssSelector("div.tab-content");
	private By productMetadata = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][1]/li");
	private By productPricedata = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][2]/li");
	private By qty = By.id("input-quantity");
	private By AddtoCartBtn = By.id("button-cart");
	private By cartsuccessmsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");

	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productheader);
		System.out.println("product header:" + productHeaderVal);
		return productHeaderVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productimages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product images count: " + imagesCount);
		return imagesCount;

	}

	public String getProductContent() {
		WebElement text = eleUtil.waitForElementVisible(productcontent, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		String productDesc = text.getText();
		// System.out.println("Product description is: " + productDesc);
		return productDesc;
	}

	public Map<String, String> getProductInfo() {
		productInfoMap = new HashMap<String, String>();
		//productInfoMap = new LinkedHashMap<String, String>();
		//productInfoMap = new TreeMap<String, String>();
		// header
		productInfoMap.put("productName", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println(productInfoMap);
		return productInfoMap;

	}

	private void getProductMetaData() {
		// metaData
		List<WebElement> metaList = eleUtil.getElements(productMetadata);
		for (WebElement e : metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}

	private void getProductPriceData() {
		// priceData
		List<WebElement> priceList = eleUtil.getElements(productPricedata);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxval = exTax.split(":")[1];

		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", exTaxval);
	}
	
	public void enterQuantity(int quantity) {
		System.out.println("Product quantity is: " + quantity);
		eleUtil.doSendKeys(qty, String.valueOf(quantity));
	}
	
	public String addPrducttoCart() {
		eleUtil.doClick(AddtoCartBtn);
		String successmesg= eleUtil.waitForElementVisible(cartsuccessmsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		StringBuilder sb= new StringBuilder(successmesg);
		String mesg= sb.substring(0, successmesg.length()-1).replace("\n", "").toString();
		System.out.println("Cart success msg: "+ mesg);
		return mesg;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
