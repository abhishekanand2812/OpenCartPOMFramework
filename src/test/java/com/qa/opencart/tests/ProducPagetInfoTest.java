package com.qa.opencart.tests;

import java.util.Map;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProducPagetInfoTest extends BaseTest {

	@BeforeClass
	public void accountPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test // In order to solve problem of hard assertion, we use SoftAssert
	public void ProductInfoTest() {

		resPage = accPage.doSearch("macbook");
		productInfoPage = resPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();
		System.out.println(productInfoMap);// hashmap order is random
		// {Brand=Apple, Availability=In Stock, Product Code=Product 18,
		// productname=MacBook Pro, Reward Points=800, productprice=$2,000.00,
		// exTaxPrice=$2,000.00}

		// O/p of linkedHashMap:It will maintain the order unlike HashMap
		// {Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In
		// Stock, productprice=$2,000.00, exTaxPrice=$2,000.00, productname=MacBook Pro}

		// O/P of treeMap: First capital , small and then numeric keys it will maintain
		// sorted order
		// {Availability=In Stock, Brand=Apple, Product Code=Product 18, Reward
		// Points=800, exTaxPrice=$2,000.00, productname=MacBook Pro,
		// productprice=$2,000.00}

		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertAll();// mandatory line to line in softAssert // if we dont write then even after
								// failure, TC will pass

	}

}
