package com.example;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://economictimes.indiatimes.com/et-now/results");
        
        driver.findElement(By.linkText("Mutual Funds")).click();
        Thread.sleep(2000);

        JavascriptExecutor js= (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,800)", " ");
        
        WebElement amc=driver.findElement(By.id("amcSelection"));
        Select sel1=new Select(amc);
        sel1.selectByVisibleText("Canara Robeco");
        Thread.sleep(2000);
        WebElement sch=driver.findElement(By.name("schemenm"));
        Select sel2=new Select(sch);
        sel2.selectByValue("15765");
        driver.findElement(By.linkText("Get Details")).click();
        Thread.sleep(2000);

        ArrayList<String> windowhandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowhandles.get(1));
        
        driver.findElement(By.id("installment_type")).click();
        driver.findElement(By.xpath("//*[@id='installment_type']/li/ul/li[1]/span")).click();
        

        driver.findElement(By.xpath("//*[@id='installment_amt']/li/span")).click();
        driver.findElement(By.xpath("//*[@id='installment_amt']/li/ul/li[3]/span")).click();

        driver.findElement(By.xpath("//*[@id='installment_period']/li/span")).click();
        driver.findElement(By.xpath("//*[@id='installment_period']/li/ul/li[4]")).click();


        driver.findElement(By.xpath("//*[@id='mfNav']/div/ul/li[2]")).click();
        
        String res = driver.findElement(By.xpath("//*[@id='mfReturns']/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]")).getText();
        System.out.println(res);

    }
}

