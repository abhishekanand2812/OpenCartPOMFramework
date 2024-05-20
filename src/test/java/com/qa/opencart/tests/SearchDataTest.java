package com.qa.opencart.tests;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.dataproviders.ProductDataProvider;
import com.qa.opencart.pojo.Product;

public class SearchDataTest extends BaseTest {

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

	// below line of code moved to Product data class in dataprovider package
//	@DataProvider(name="productData")
//	public Object[][] getProductTestData(){
//		
//		return new Object[][] {
//			{new Product("macBook","MacBook Pro",4)},
//			{new Product("imac","iMac",3)},
//			{new Product("samsung", "Samsung SyncMaster 941BW", 1)},
//			{new Product( "samsung", "Samsung Galaxy Tab 10.1", 7 )},
//
//		};
//		
//		
//	}

	// now we have to tell this dataProvider = "productData" is coming from other
	// class, so syntax is: append with comma and dat

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void searchResultsProductCountTest(Product product) {
		resPage = accPage.doSearch(product.getSearchKey());
		int actProdCount = resPage.getProductResultsCount();
		Assert.assertTrue(actProdCount > 0);

	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void searchHeaderTitleTest(Product product) {

		resPage = accPage.doSearch(product.getSearchKey());
		String actSearchTitle = resPage.getResultsPageTitle(product.getSearchKey());
		System.out.println("Search page title: " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + product.getSearchKey());

	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(Product product) {
		resPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resPage.selectProduct(product.getProductName());
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual product name : " + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, product.getProductName());

	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(Product product) {
		resPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resPage.selectProduct(product.getProductName());
		int actProdImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual product images count : " + actProdImagesCount);
		Assert.assertEquals(actProdImagesCount, product.getProductImages());

	}
}
