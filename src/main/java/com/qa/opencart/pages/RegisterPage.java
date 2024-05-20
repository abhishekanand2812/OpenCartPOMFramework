package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil elUtil;

	public RegisterPage(WebDriver driver) {

		this.driver = driver;
		elUtil = new ElementUtil(this.driver);
	}

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By telePhone = By.id("input-telephone");
	private By pass = By.id("input-password");
	private By cnfPass = By.id("input-confirm");

	private By subscribeYesRadioButton = By.xpath("(//input[@type='radio' and @value='1'])[last()]");
	private By subscribeNoRadioButton = By.xpath("//input[@type='radio' and @value='0']");

	private By agreeChkBox = By.xpath("//input[@type='checkbox' and @name='agree']");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");

	private By userRegSuccessMsg = By.xpath("//div[@id='content']/h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public String registerUser(String firstName, String lastName, String emailId, String telePhone, String pass,
			String subscibe) {

		elUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT);

		elUtil.doSendKeys(this.firstName, firstName);
		elUtil.doSendKeys(this.lastName, lastName);
		elUtil.doSendKeys(this.emailId, emailId);
		elUtil.doSendKeys(this.telePhone, telePhone);
		elUtil.doSendKeys(this.pass, pass);
		elUtil.doSendKeys(this.cnfPass, pass);

		doSubscibe(subscibe);

		elUtil.getElement(agreeChkBox).click();
		elUtil.getElement(continueBtn).click();

		String userRegSuccessMsg = elUtil
				.waitForElementVisible(this.userRegSuccessMsg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();
		System.out.println(userRegSuccessMsg);

		elUtil.doClick(logoutLink);
		elUtil.doClick(registerLink);

		return userRegSuccessMsg;

	}

	private void doSubscibe(String subscribe) {
		if (subscribe.equalsIgnoreCase("yes")) {
			elUtil.doClick(subscribeYesRadioButton);
		} else {
			elUtil.doClick(subscribeNoRadioButton);

		}

	}

}
