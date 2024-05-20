package com.qa.opencart.tests;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//two below annotation coming from Allure
@Epic("Epic 100:Login Page Design")
@Story("US 101:Design login page for open cart app with title, url, forgot password, user is able to login")
public class LoginPageTest extends BaseTest {

	// below three annotation coming from Allure
	@Severity(SeverityLevel.MINOR)
	@Description("Checking login page title test")
	@Feature("Only for title test")

	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);

	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("Checking forgot pass link test")
	@Feature("Only for forgot pass link test")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());

	}

	@Severity(SeverityLevel.NORMAL)
	@Description("Checking login page url test")
	@Feature("Only for login page url link test")
	@Test
	public void loginPageURLTest() {
		String actUrl = loginPage.getLoginPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));

	}

//	@Test
//	public void newCustomerHeaderTest() {
//		String actHeader = loginPage.newCustomerHeader();
//		Assert.assertEquals(actHeader, "New Customer");
//
//	}
//
//	@Test
//	public void returningCustomerHeaderTest() {
//		String actHeader = loginPage.returningCustomerHeader();
//		Assert.assertEquals(actHeader, "Returning Customer");
//
//	}

	@Test
	public void searchFieldExistTest() {
		Assert.assertTrue(loginPage.IsSearchFieldExist());

	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("Checking login test")
	@Feature("Only for login test")

	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
		// Assert.assertTrue(accPage.getAccountPageTitle().equals("My Account"));

	}

}
