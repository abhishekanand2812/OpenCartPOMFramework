package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productInfoMap;

	private By productHeader = By.cssSelector("#content h1");
	private By productImages = By.xpath("//ul[@class='thumbnails']//img");
	private By productMetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");

	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By productQuantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getProductHeaderName() {
		return eleUtil.doGetElementText(productHeader);
	}

	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}

// Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	// Use HashMap to automate the scenario

	// this is private method because it's not returning anything to the user, so we
	// should not expose it to user.
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetadata);
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);

		}
	}

	// $2,000.00
	// Ex Tax: $2,000.00

	// this is private method because it's not returning anything to the user, so we
	// should not expose it to user.
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String priceValue = priceList.get(0).getText();// 2000
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPriceVal = exTaxPrice.split(":")[1].trim();// 2000
		productInfoMap.put("productprice", priceValue);
		productInfoMap.put("exTaxPrice", exTaxPriceVal);

	}

	public Map<String, String> getProductInfo() {
		//productInfoMap = new HashMap<String, String>();
		
		//Use linkedHashMap in order to maintain order
		//Both HashMap and linkedHashMap are the child classes of Map interface
		//productInfoMap = new LinkedHashMap<String, String>();
		
		//Use treeMap to store the data in sorted order:
		productInfoMap = new TreeMap<String, String>();
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;
	}
}
