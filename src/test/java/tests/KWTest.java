package tests;

import java.util.Properties;

import com.qa.hubspot.keyword.Engine.KeyWordEngine;

public class KWTest {
	
//	public static WebDriver driver;
	public static Properties prop;

	public static void main(String[] args) {

		
//		BasePage basePage = new BasePage();
//		prop = basePage.init_Properties();
//		driver = basePage.init_driver(prop.getProperty("browser"));
		//driver.get(prop.getProperty("url"));
		
		KeyWordEngine keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("login");
		
		
		
		
		
		
		
		
	}

}
