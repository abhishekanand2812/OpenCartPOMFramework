package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// 1.declare private webdriver:

	private WebDriver driver;
	private ElementUtil elUtil;

	// 2.create constructor of the class
	public LoginPage(WebDriver driver) {

		this.driver = driver;
		elUtil = new ElementUtil(this.driver);

	}

	// 3.create all the private by locators:
	// private because no other class can use these by locators.
	// only this class can access in public methods- classic example of
	// encapsulation
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.xpath("//a[text()='Forgotten Password']");
	private By footerLinks = By.xpath("//footer//a");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By searchField = By.xpath("//input[@placeholder='Search']");
	private By newCustomerHeader = By.xpath("//h2[text()='New Customer']");
	private By returningCustomerHeader = By.xpath("//h2[text()='Returning Customer']");
	private By loginErrorMsg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");

	private By registerLink = By.linkText("Register");

	// 4.create public page actions/methods:

	@Step("Getting login page title...")
	public String getLoginPageTitle() {
		String title = elUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE,
				AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page title " + title);
		return title;

	}

	@Step("getting login page url")

	public String getLoginPageURL() {

		return elUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE,
				AppConstants.SHORT_DEFAULT_WAIT);

	}

	@Step("checking forgot pass link exists or not ")

	public boolean isForgotPwdLinkExist() {
		return elUtil.checkElementIsDisplayed(forgotPwdLink);

	}

	@Step("checking logo exists or not ")
	public boolean isLogoExist() {
		return elUtil.checkElementIsDisplayed(logo);

	}

	@Step("checking customer header")

	public String newCustomerHeader() {
		return elUtil.getElement(newCustomerHeader).getText();

	}

	public String returningCustomerHeader() {
		return elUtil.getElement(returningCustomerHeader).getText();

	}

	public boolean IsSearchFieldExist() {
		return elUtil.getElement(searchField).isDisplayed();

	}

	@Step("getting footer links ")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = elUtil.waitForElementsVisible(footerLinks, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}

	@Step("login with username {0) and password {1}")
	public AccountsPage doLogin(String usrName, String pwd) {
		elUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		elUtil.doSendKeys(emailId, usrName);
		elUtil.doSendKeys(password, pwd);
		elUtil.doClick(loginBtn);
		// should return next landing page:AccountsPage: page chaining model
		// POM says whenver you are using click(), then its method's responsibility to
		// give next landing page object

		return new AccountsPage(driver);

	}

	@Step("login with incorrect username {0) and password {1}")

	public boolean doLoginWithWrongCredentials(String usrName, String pwd) {
		System.out.println("Wrong creds are: username " + usrName + "and password " + pwd);

		elUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		elUtil.doSendKeys(emailId, usrName);
		elUtil.doSendKeys(password, pwd);
		elUtil.doClick(loginBtn);
		String errorMsg = elUtil.doGetElementText(loginErrorMsg);
		System.out.println(errorMsg);
		if (errorMsg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		} else {
			return false;
		}
	}

	public RegisterPage navigateToRegisterPage() {
		elUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
