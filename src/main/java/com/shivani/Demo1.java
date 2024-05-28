package com.shivani;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//shivani is super capable she needs to work hard though, its new for her 
public class Demo1 {
	
	WebDriver driver;
	String user;
	String pass;
	
	@BeforeTest
	public void open_the_chrome_and_launch_fb()
	{
		System.setProperty("webdriver.chrome.driver", "/Users/shivanigujare/selenium webdriver/ChromeDriver/chromedriver-mac-x64/chromedriver");
		driver = new ChromeDriver();
		
		driver.get("https://www.facebook.com");
		driver.manage().window().maximize();
		String actualTitle = driver.getTitle();
		String expectedTitle = "Facebook - log in or sign up";
		assertEquals(actualTitle, expectedTitle);
		System.out.println("Login page loaded");
	}
	
	@Test
	public void connect_to_db() throws ClassNotFoundException, SQLException
	{
		String dBURL = "jdbc:mysql://localhost:3306/dbtesting";
		String dbuser = "root";
		String dbpassword = "sanskriti";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("jdbc driver created");
		
		Connection conn = null;
		Statement stmt = null;
		
		conn = DriverManager.getConnection(dBURL, dbuser, dbpassword);
		System.out.println("driver made db connection");
		
		stmt = conn.createStatement();
		
		String sqlQuery = "select * from fb_login where username='justanothersoul3@gmail.com'";
		
		ResultSet rs = stmt.executeQuery(sqlQuery);
		
		while (rs.next())
		{
			user = rs.getString(1);
			pass = rs.getString(2);
		}
			
		conn.close();
		stmt.close();
	}

	
	@Test
	public void enter_username_and_password()
	{
		driver.findElement(By.name("email")).sendKeys(user);
		driver.findElement(By.name("pass")).sendKeys(pass);
		driver.findElement(By.name("login")).click();
	}
	
	@Test
	public void user_login_successful()
	{
		String title = driver.getTitle();
		String Expectedtitle = "Facebook";
		assertEquals(title, Expectedtitle);
		Assert.assertEquals(title, Expectedtitle);
		
	}
	
	@AfterTest
	public void browser_quit() throws InterruptedException
	{
		driver.manage().window().maximize();
		Thread.sleep(5000);
		driver.close();
	}
}
	


