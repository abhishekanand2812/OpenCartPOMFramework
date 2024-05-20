package com.qa.opencart.tests;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageNegativeTest extends BaseTest {

	@DataProvider()
	public Object[][] incorrectLoginTestData() {
		return new Object[][] { { "automation123@gmail.com", "e37yeudgh" }, { "teswqwt@qw@gmail.com", "123456" },
				{ "automa", "e37yeudgh@3" }, { "^&&g", "GYGYG7" }, };

	}

	@Test(dataProvider = "incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String usrName, String password) {
		Boolean actFlag = loginPage.doLoginWithWrongCredentials(usrName, password);
		Assert.assertTrue(actFlag);

	}

}
