package com.qa.opencart.tests;

import java.util.List;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.utils.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accountsPageTitleTest() {

		String actTitle = accPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);

	}

	@Test
	public void logoutLinkExistTest() {

		Assert.assertTrue(accPage.isLogoutLinkExist());

	}

	@Test
	public void myAccountLinkExistTest() {

		Assert.assertTrue(accPage.isMyAccountLinkExist());

	}

	@Test
	public void accPageHeadersCountTest() {

		List<String> actAccPageHeaders = accPage.getAccountPageHeadersList();
		int actHeaderCount = actAccPageHeaders.size();
		Assert.assertEquals(actHeaderCount, 4);

	}

	@Test
	public void accPageHeadersTest() {

		List<String> actAccPageHeaders = accPage.getAccountPageHeadersList();
		Assert.assertEquals(actAccPageHeaders, AppConstants.EXP_ACCOUNT_HEADERS_LIST);
		;

	}

}
