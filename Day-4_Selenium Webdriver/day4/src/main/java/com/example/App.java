package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();

        /*WebDriverManager.edgedriver().setup();
        WebDriver driver1=new EdgeDriver();

        WebDriverManager.firefoxdriver().setup();
        WebDriver driver2=new FirefoxDriver();*/
        
        driver.manage().window().maximize();
        
        driver.get("http://www.shoppersstop.com");

        driver.findElement(By.id("profileImage")).click();

    }
}
