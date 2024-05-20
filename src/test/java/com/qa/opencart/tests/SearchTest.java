package com.qa.opencart.tests;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.dataproviders.ProductDataProvider;

public class SearchTest extends BaseTest {

	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}
	// Data provider will provide data in 2-d Object array
	// Data driven testing:- source of data is dataprovider

	// What is DDT/parameterization of your test?
	// Advantage is we dont need to maintain seperate test case for different test
	// data
	// When we supply data from data source and passing those data in the form of
	// parameters in @Test annotation

	// What is delta testing approach?
	// If we have 1000 products and if application is working fine for 5-10
	// products, then it would work well with 1000 too without testing

	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchResultsProductCountTest(String searchKey) {
		resPage = accPage.doSearch(searchKey);
		int actProdCount = resPage.getProductResultsCount();
		Assert.assertTrue(actProdCount > 0);

	}

	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchHeaderTitleTest(String searchKey) {

		resPage = accPage.doSearch(searchKey);
		String actSearchTitle = resPage.getResultsPageTitle(searchKey);
		System.out.println("Search page title: " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + searchKey);

	}

	@Test(dataProvider = "productDataWithName", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(String searchKey, String productName) {
		resPage = accPage.doSearch(searchKey);
		productInfoPage = resPage.selectProduct(productName);
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual product name : " + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, productName);

	}

	@Test(dataProvider = "productDataWithImage", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(String searchKey, String productName, int expectedImagesCount) {
		resPage = accPage.doSearch(searchKey);
		productInfoPage = resPage.selectProduct(productName);
		int actProdImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual product images count : " + actProdImagesCount);
		Assert.assertEquals(actProdImagesCount, expectedImagesCount);

	}

}
