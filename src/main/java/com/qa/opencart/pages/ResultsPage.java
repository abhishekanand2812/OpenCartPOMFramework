package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By resultProducts = By.cssSelector("div.product-layout.product-grid");// static locator

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// page actions
	public String getResultsPageTitle(String searchKey) {
		return eleUtil.waitForTitleIsAndCapture(searchKey, AppConstants.SHORT_DEFAULT_WAIT);
	}

	public int getProductResultsCount() {

		int resultCount = eleUtil.waitForElementsVisible(resultProducts, AppConstants.MEDIUM_DEFAULT_WAIT).size();
		System.out.println("product search result count= " + resultCount);
		return resultCount;

	}

	// TDD approach:
	// On the basis of test, we are doing development

	public ProductInfoPage selectProduct(String productName) {

		By productNameLocator = By.linkText(productName);// dynamic locators
		eleUtil.doClick(productNameLocator);
		return new ProductInfoPage(driver);
	}
}