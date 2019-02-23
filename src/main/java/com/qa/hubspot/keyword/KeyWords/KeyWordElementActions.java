package com.qa.hubspot.keyword.KeyWords;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import com.qa.hubspot.keyword.Base.BasePage;

public class KeyWordElementActions extends BasePage {
	
	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Select select;

	public KeyWordElementActions(WebDriver driver){
		this.driver = driver;
	}
	
	
	/**
	 * 
	 * @param Selector
	 * @return
	 */
	public By getCSS(@NotNull String Selector) {
		return By.cssSelector(Selector);
	}

	/**
	 * 
	 * @param Selector
	 * @return
	 */
	public By getXPATH(@NotNull String Selector) {
		return By.xpath(Selector);
	}

	/**
	 * 
	 * @param Selector
	 * @return
	 */
	public By getId(@NotNull String Selector) {
		return By.id(Selector);
	}

	/**
	 * 
	 * @param Selector
	 * @return
	 */
	public By getLinkText(@NotNull String Selector) {
		return By.linkText(Selector);
	}

	/**
	 * 
	 * @param URL
	 */
	public void launchUrl(@NotNull String URL) {
		try {
			driver.get(URL);
		} catch (Exception e) {
			throw new TestException("URL did not load");
		}

	}

	/**
	 * 
	 * @param URL
	 */
	public void navigateToURL(@NotNull String URL) {
		try {
			driver.navigate().to(URL);
		} catch (Exception e) {
			throw new TestException("URL did not load");
		}
	}

	/**
	 * 
	 * @return url
	 */
	public String getPageTitle() {
		try {
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	/**
	 * 
	 * @param selector
	 * @return selector
	 */
	public WebElement getElement(@NotNull By selector) {
		try {
			 WebElement ele = driver.findElement(selector);
			 return ele;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param selector
	 * @return List of WebElements
	 */
	public List<WebElement> getElements(@NotNull By selector) {
		try {
			return driver.findElements(selector);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param selector
	 * @return selector
	 */
	public String getElementText(@NotNull By selector) {
		waitForElementToBeVisible(selector);
		try {
			return getElement(selector).getText().trim();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param selector
	 * @param value
	 */
	public void sendKeys(@NotNull By selector, @NotNull String value) {
		WebElement element = getElement(selector);
		clearField(element);
		try {
			element.sendKeys(value);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}
	}

	/**
	 * 
	 * @param element
	 */
	public void clearField(@NotNull WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param element
	 */
	public void clearField(@NotNull By selector) {
		try {
			getElement(selector).clear();
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param selector
	 */
	public void click(@NotNull By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			if (element != null)
				element.click();
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}

	/**
	 * 
	 * @param selector
	 */
	public void submit(@NotNull By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			element.submit();
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}
	
	/**
	 * This method will keep retrying to find the element in case of 
	 * StaleElementReferenceException or in case of element got deleted or updated in DOM
	 * @param selector
	 * @return
	 */
	public WebElement retryingFindElement(By selector) {
		WebElement element = null;
        int attempts = 0;
        while(attempts < 4) {
            try {
            	element = getElement(selector);
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return element;
}

	/**
	 * 
	 * @param selector
	 */
	public void waitForElementToBeClickable(@NotNull By selector) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(selector));
		} catch (Exception e) {
			throw new TestException("The following element is not clickable: " + selector);
		}
	}

	/**
	 * 
	 * @param selector
	 * 
	 */
	public void waitForElementToBeVisible(@NotNull By selector) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		} catch (Exception e) {
		}
	}
	
	/**
	 * quit browser
	 */
	public void quitBrowser(){
		try{
			driver.quit();
		}catch(Exception e){
			
		}
		
	}

	
}
