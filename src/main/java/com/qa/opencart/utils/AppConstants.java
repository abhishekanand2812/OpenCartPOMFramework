package com.qa.opencart.utils;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	// Using static keyword here because no need to create object old AppConstants
	// No issue with parallel execution
	// using class name , we can call the variable , i.e,
	// AppConstants.LOGIN_PAGE_TITLE_VALUE

	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	public static final String ACCOUNTS_PAGE_TITLE_VALUE = "My Account";

	public static final String LOGIN_PAGE_URL_FRACTION_VALUE = "route=account/login";

	public static final int SHORT_DEFAULT_WAIT = 5;
	public static final int MEDIUM_DEFAULT_WAIT = 5;
	public static final int LARGE_DEFAULT_WAIT = 5;

	public static final List<String> EXP_ACCOUNT_HEADERS_LIST = Arrays.asList("My Account", "My Orders",
			"My Affiliate Account", "Newsletter");

	public static final String LOGIN_ERROR_MESSAGE = "No match for E-Mail Address and/or Password.";
	public static final String USER_REG_SUCCESS_MESG = "Your Account Has Been Created!";
	
	//***********EXCEL SHEET NAME***************
	public static final String REGISTER_SHEET_NAME = "register";

}
