package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkexception.FrameException;

/**
 * 
 * @author abhishekanand
 *
 */

public class ElementUtil {
	/**
	 * Creating a constructor so that driver reference used to call methods of
	 * ElementUtil class can be initialized with the driver value setup in
	 * BrowserUtil class. Making driver reference as private because no other class
	 * can have access to call methods of ElementUtil class.If at all we did not
	 * make it private and some other class has an access to this, then they will
	 * get NullPointerException. Utilizing concept of Encapsulation as private
	 * driver reference variable is initialized using public constructors.
	 */
	private WebDriver driver;
	private final int DEFAULT_TIME_OUT = 5;

	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		this.jsUtil = new JavaScriptUtil(this.driver);
	}

	/**
	 * This method is only called for those elements where DYNAMIC Wait is required
	 * Else we will call normal below getElement() without timeout
	 * 
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	public WebElement getElement(By locator, int timeout) {
		WebElement element = waitForElementVisible(locator, timeout);
		if (Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);

		}
		return element;
	}

	/**
	 * Creating a generic wrapper method on top of selenium scripts for finding an
	 * element using a By locator Handled 3 scenarios:- <i>If element is found ,
	 * immediately return WebElement <ii>If element is found after 5 seconds <iii>If
	 * element is not found, throw exception as NoSuchElementException
	 * 
	 * @param locator
	 * @return WebElement
	 */
	public WebElement getElement(By locator) {

		WebElement element = null;
		try {
			element = driver.findElement(locator);
			System.out.println("Element is found with this locator..." + locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found with this locator..." + locator);
			element = waitForElementVisible(locator, DEFAULT_TIME_OUT);// If element is found after 5 seconds
		}
		if (Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);

		}
		return element;
	}

	/**
	 * Creating a generic wrapper method on top of selenium scripts for entering
	 * values/sending keys to Textfield webelement Internally called doClear method
	 * and getElement method and performed action of send keys Custom exception
	 * thrown for Null values passed from user
	 * 
	 * @param locator
	 * @param value
	 */

	public void doSendKeys(By locator, String value) {
		if (value == null) {
			throw new FrameException("VALUESCANNOTBENULL");
		}
		doClear(locator);
		getElement(locator).sendKeys(value);
	}

	/**
	 * Creating a generic wrapper method on top of selenium scripts for clicking on
	 * a WebElement
	 * 
	 * @param locator
	 */

	public void doClick(By locator) {
		getElement(locator).click();
	}

	/**
	 * Click on element using webdriver wait
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void doClick(By locator, int timeOut) {
		checkElementClickable(locator, timeOut);

	}

	/**
	 * Creating a generic wrapper method on top of selenium scripts for clearing the
	 * contents on a WebElement
	 * 
	 * @param locator
	 */

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	/**
	 * This generic method is used to get/capture the text associated with the
	 * webelement
	 * 
	 * @param locator
	 * @return string
	 */

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	/**
	 * This generic method is used to check if the element is displayed on the
	 * webpage or not
	 * 
	 * @param locator
	 * @return boolean
	 */
	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	/**
	 * This generic method is used to get the attributeValue of particular attribute
	 * 
	 * @param locator
	 * @param attrName
	 * @return string
	 */

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	/**
	 * This method is used to get the count of list of WebElements
	 * 
	 * @param locator
	 * @return
	 */

	/**
	 * This method is used to find the list of WebElements
	 * 
	 * @param locator
	 * @return List<WebElement>
	 */
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * This method is used to return the size and of the list of WebElements
	 * 
	 * @param locator
	 * @return
	 */

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	/**
	 * This method is used to fetch the list of values of attributes associated with
	 * list of WebElements
	 * 
	 * @param locator
	 * @param attrName
	 * @return List<String>
	 */

	public List<String> getElementsAttributeValue(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrValuesList = new ArrayList<String>();// 0

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			// System.out.println(attrValue);
			eleAttrValuesList.add(attrValue);
		}
		return eleAttrValuesList;
	}

	/**
	 * This method is used to click on the specific element from the list of
	 * WebElements captured.
	 * 
	 * @param locator
	 * @param eleText
	 */

	public void clickElementFromPageSection(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(eleText)) {
				e.click();
				break;
			}
		}
	}

	/**
	 * This method is used to get the text of the list of WebElements captured
	 * 
	 * @param locator
	 * @return List<String>
	 */

	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementsLinksList = getElements(locator);
		List<String> elesTextList = new ArrayList<String>();

		for (WebElement e : elementsLinksList) {
			String text = e.getText();
			elesTextList.add(text);
		}
		return elesTextList;
	}

	/**
	 * This method is used to check if element/elements is present on the page or
	 * not
	 * 
	 * @param locator
	 * @return boolean
	 */

	public boolean IsElementDisplayed(By locator) {
		List<WebElement> eleList = getElements(locator);
		if (eleList.size() > 0) {
			System.out.println(locator + " element is present on the page");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to automate search field with suggestions. Handled below
	 * scenarios:- <i>If suggestion list size is greater than 0, and length of every
	 * suggestion is greater than 0 , then click after getting the suggestion name
	 * passed. <ii>If suggestion list size is greater than 0 but it contains blank
	 * values <iii>If suggestion list size is less than 0
	 * 
	 * @param searchKey
	 * @param searchLocator
	 * @param suggName
	 * @param suggestions
	 * @throws InterruptedException
	 */

	public void search(String searchKey, By searchLocator, String suggName, By suggestions)
			throws InterruptedException {

		doSendKeys(searchLocator, searchKey);
		Thread.sleep(3000);

		List<WebElement> suggList = getElements(suggestions);
		System.out.println("total suggestions: " + suggList.size());

		if (suggList.size() > 0) {

			for (WebElement e : suggList) {
				String text = e.getText();
				if (text.length() > 0) {
					System.out.println(text);
					if (text.contains(suggName)) {
						e.click();
						break;
					} else {
						System.out.println("sugg value " + suggName + " is not present");
					}
				} else {
					System.out.println("blank values -- but no sugeestions");
					break;
				}
			}
		} else {
			System.out.println("no search suggestions found");
		}

	}

	// *************DROPDOWN UTILS******************
	/**
	 * This method is used to select drop down values using select class and using
	 * by index value
	 * 
	 * @param locator
	 * @param index
	 */

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	/**
	 * This method is used to select drop down values using select class and using
	 * by visible text
	 * 
	 * @param locator
	 * @param index
	 */

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);

	}

	/**
	 * This method is used to select drop down values using select class and using
	 * by value attribute
	 * 
	 * @param locator
	 * @param index
	 */

	public void doSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);

	}

	/**
	 * This method is used to get all the drop-down options using getOptions method
	 * present in Select class
	 * 
	 * @param locator
	 * @return List<String> where all the drop-down values are returned
	 */

	public List<String> getAllDropDownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();
		System.out.println("total values : " + optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	/**
	 * This method is used to get the drop-down values count
	 * 
	 * @param locator
	 * @return integer size of the drop-down counts
	 */

	public int getDropDownValueCount(By locator) {
		return getAllDropDownOptions(locator).size();
	}

	/**
	 * This method is used to select and click on the specific drop-down value
	 * passed to the parameter
	 * 
	 * @param locator
	 * @param dropDownValue
	 */

	public void doSelectDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("total values : " + optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	/**
	 * This method is used to select drop down values without using select class and
	 * using only xpath locator strategy
	 * 
	 * @param locator
	 * @param index   returns boolean
	 */

	public boolean doSelectValueFromDropDownWithoutSelect(By locator, String value) {
		boolean flag = false;
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				flag = true;
				e.click();
				break;
			}
		}

		if (flag == false) {
			System.out.println(value + " is not present in the drop down " + locator);
		}

		return flag;

	}

	/**
	 * This method is used to de-select drop down values using select class and
	 * using by index value
	 * 
	 * @param locator
	 * @param index
	 */

	public void doDeSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	/**
	 * This method is used to de-select drop down values using select class and
	 * using by visible text
	 * 
	 * @param locator
	 * @param index
	 */

	public void doDeSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);

	}

	/**
	 * This method is used to de-select drop down values using select class and
	 * using by value attribute
	 * 
	 * @param locator
	 * @param index
	 */

	public void doDeSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);

	}

//*****************Actions class utils********************************************//

	public void doDragAndDrop(By sourceLocator, By targetLocator) {

		WebElement source = getElement(sourceLocator);
		WebElement target = getElement(targetLocator);
		Actions action = new Actions(driver);

		// build method will build all the methods like dragAndDrop etc and perform
		// method will perform action
		// 1st way
		// action.dragAndDrop(source, target).build().perform();

		// 2nd way:- click and hold the source and then release to the target
		action.clickAndHold(source).moveToElement(target).release().build().perform();

	}

	public void doContextClick(By rightElementLocator) {

		Actions action = new Actions(driver);
		action.contextClick(getElement(rightElementLocator)).build().perform();
	}

	public void doMoveToElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator)).build().perform();

	}

	/**
	 * 
	 * @param parentMenu
	 * @param childMenu
	 */
	public void handleTwoLevelMenu(By parentMenu, By childMenu) {

		doMoveToElement(parentMenu);

		doClick(childMenu);
	}

	public void handleTwoLevelMenu(By parentMenu, String childMenuText) {

		// mouse-over on contact menu
		doMoveToElement(parentMenu);

		WebElement articleMenu = getElement(By.linkText(childMenuText));
		// click on article menu
		articleMenu.click();
	}

	public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
			String level4LinkText) throws InterruptedException {

		WebElement level1 = getElement(parentMenuLocator);

		Actions act = new Actions(driver);

		doMoveToElement(parentMenuLocator);
		Thread.sleep(1000);

		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(1000);

		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(1000);

		doClick(By.linkText(level4LinkText));
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(locator), value).build().perform();

	}

	public void doActionsClick(By locator) {
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}

	/**
	 * doActionsClick using webdriver wait
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void doActionsClick(By locator, int timeout) {
		Actions action = new Actions(driver);
		action.click(checkElementClickable(locator, timeout)).build().perform();
	}

//******************WAIT UTILS***************************************************//

	public Alert waitForAlertJsPopupWithFluentWait(By locator, int timeOut, int pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(NoAlertPresentException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime)).withMessage("---timeout done--- No alert found");

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public Alert waitForAlertJsPopup(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String alertJSGetText(int timeOut) {
		return waitForAlertJsPopup(timeOut).getText();
	}

	public void alertAccept(int timeOut) {
		waitForAlertJsPopup(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlertJsPopup(timeOut).dismiss();
	}

	public void enterAlertValue(int timeOut, String value) {
		waitForAlertJsPopup(timeOut).sendKeys(value);
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param titleFraction
	 * @param timeOut
	 * @return String
	 */

	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("Title is not present within the given timeout:" + timeOut);
			return null;
		}
	}

	/**
	 * An expectation for checking the title of a page.Compares exact value of title
	 * and then captures
	 * 
	 * @param titleVal
	 * @param timeOut
	 * @return
	 */

	public String waitForFullTitleAndCapture(String titleVal, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleIs(titleVal))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("Title is not present within the given timeout:" + timeOut);
			return null;
		}
	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param urlFraction
	 * @param timeOut
	 * @return String
	 */

	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("URL is not present within the given timeout:" + timeOut);
			return null;
		}
	}

	/**
	 * An expectation for the URL of the current page to be a specific url.
	 * 
	 * @param urlValue
	 * @param timeOut
	 * @return
	 */

	public String waitForURLAndCapture(String urlValue, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("URL is not present within the given timeout:" + timeOut);
			return null;
		}
	}

	/**
	 * Helps in validating totalWindowsToBe
	 * 
	 * @param totalWindowsToBe
	 * @param timeOut
	 * @return
	 */

	public Boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible on the page.
	 * limitation is we are bothered about element present on page and not on the
	 * page
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementPresence(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));// selenium 4.x.x
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if (Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0. ALWAYS USE
	 * THIS METHOD Default interval timeOut=500 ms.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		if (Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;// selenium 4.x.x

	}

	/**
	 * Here we can pass interval time also like in how many seconds frequency we are
	 * going to hit the server for finding element
	 * 
	 * @param locator
	 * @param timeOut
	 * @param intervalTime
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));// selenium
																														// 4.x.x
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * ALWAYS USE THIS METHOD
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page. NEVER USE THIS METHOD
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */

	public void clickElementWhenReady(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return WebElement
	 */

	public WebElement checkElementClickable(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// Frames with wait:

	public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(NoSuchFrameException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime)).withMessage("---timeout done--- No frame found");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void waitForFrameAndSwitchToItByFrameLoctor(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

//*******************FLUENTWaitUtil*******************************

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(ElementNotInteractableException.class)
				.ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("---timeout done--- No element found");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(ElementNotInteractableException.class)
				.ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("---timeout done--- No element found");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

//*********CustomWait using thread.sleep()*******************************

	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement ele = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				ele = getElement(locator);
				System.out.println("element is found..." + locator + " after attempt: " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found" + locator + " after attempt: " + attempts);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			attempts++;

		}
		if (ele == null) {
			System.out.println("Element is not found... tried for timeout: " + timeOut + " with interval of 500 ms"
					+ " after attempt: " + attempts);
		}
		return ele;

	}

	public WebElement retryingElement(By locator, int timeOut) {

		WebElement ele = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				ele = getElement(locator);
				System.out.println("element is found..." + locator + " after attempt: " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found" + locator + " after attempt: " + attempts);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			attempts++;

		}
		if (ele == null) {
			System.out.println("Element is not found... tried for timeout: " + timeOut + " with interval of 500 ms"
					+ " after attempt: " + attempts);
		}
		return ele;

	}

//******************Wait for page load timeout*****************//

	public void waitForPageLoad(int timeOut) {

		long endTime = System.currentTimeMillis() + timeOut;

		while (endTime < endTime) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String pageState = js.executeScript("return document.readyState").toString();
			if (pageState.equals("complete")) {
				System.out.println("page dom is loaded");
				break;
			} else {
				System.out.println("page dom is not loaded..." + pageState);

			}
		}

	}

	public Boolean isPageLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \'complete\'"))
				.toString(); // "true"
		return Boolean.parseBoolean(flag);// true
	}
}
