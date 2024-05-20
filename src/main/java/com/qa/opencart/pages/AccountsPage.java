package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	// 1.declare private webdriver:

	private WebDriver driver;
	private ElementUtil elUtil;

	// 2.create constructor of the class
	public AccountsPage(WebDriver driver) {

		this.driver = driver;
		elUtil = new ElementUtil(driver);

	}

	// create private by locators:

	private By logout = By.linkText("Logout");
	private By myAccount = By.linkText("My Account");
	private By accHeaders = By.cssSelector("div#content h2");
	private By searchField = By.xpath("//input[@placeholder='Search']");
	private By searchIcon = By.cssSelector("div#search button");

	// 3. Page actions/methods:

	public String getAccountPageTitle() {
		String title = elUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE, AppConstants.MEDIUM_DEFAULT_WAIT);
		System.out.println("Accounts page title " + title);
		return title;

	}

	public boolean isLogoutLinkExist() {
		return elUtil.checkElementIsDisplayed(logout);

	}

	public List<String> getAccountPageHeadersList() {
		List<WebElement> headersList = elUtil.waitForElementsVisible(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> headersValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		return headersValueList;

	}

	public boolean isMyAccountLinkExist() {
		return elUtil.checkElementIsDisplayed(myAccount);

	}

	// This is called TDD Approach:-
	// Test driver development approach
	// According to the test, you have to design the java classes
	// We are creating and designing page classes based on our testcases. It's not
	// pre defined
	//

	public ResultsPage doSearch(String searchTerm) {

		elUtil.waitForElementVisible(searchField, AppConstants.MEDIUM_DEFAULT_WAIT);
		elUtil.doSendKeys(searchField, searchTerm);
		elUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}

}
