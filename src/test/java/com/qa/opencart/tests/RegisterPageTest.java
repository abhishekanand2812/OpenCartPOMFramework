package com.qa.opencart.tests;

import org.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	public String getRandomEmailID() {
		return "testautomation" + System.currentTimeMillis() + "@gmail.com";
		// return "testautomation" + UUID.randomUUID()+"@gmail.com";

	}

	@DataProvider(name = "regData")
	public Object[][] getUserRegTestData() {
		return new Object[][] {
			{"abhi", "anand", "9876545678", "abhi@123", "yes"},
			{"robinson", "matinez", "9876545600", "robin@123", "no"},
			{"amber", "automation", "9876545998", "amber@123", "yes"},
		};
	}

	// USING EXCEL TO READ DATA:-
	// NOTE: - Prefix single ' before phone else exponential number will be
	// considered in telephone number field(i.e. 987876788E4)
//	@DataProvider(name = "regExcelData")
//
//	public Object[][] getRegExcelTestData() {
//
//		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
//		return regData;
//
//	}
//
	@Test(dataProvider = "regData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String pass, String subscribe) {
		String userRegMsg = registerPage.registerUser(firstName, lastName, getRandomEmailID(), telephone, pass,
				subscribe);
		Assert.assertEquals(userRegMsg, AppConstants.USER_REG_SUCCESS_MESG);

	}

}