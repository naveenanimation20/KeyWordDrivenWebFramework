package com.qa.hubspot.tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.keyword.Engine.KeyWordEngine;

public class LoginTest {
	
	@Test
	@Parameters("sheetname")
	public void loginTest(String sheetName){
		System.out.println(sheetName);
		KeyWordEngine keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution(sheetName);
	}
	
	
	
	

}
