package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class firstcry {
    WebDriver driver;
    ExtentReports reports;
    ExtentTest test;

    @BeforeTest
    public void before() 
    {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\Users\\91822\\Desktop\\Testing\\Model_Lab\\report\\report.html");
        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
    }
    
    @Test
    public void TestCase1() throws IOException
    {
        test = reports.createTest("Test 1", "Toys");

        FileInputStream fs = new FileInputStream("C:\\Users\\91822\\Desktop\\Testing\\Model_Lab\\modelLab_firstcry.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.firstcry.com");
        driver.manage().window().maximize();

        WebElement searchBar = driver.findElement(By.xpath("//*[@id='search_box']"));
        searchBar.click();
        searchBar.sendKeys(row.getCell(0).getStringCellValue(), Keys.ENTER);

        String url=driver.getCurrentUrl();
        SoftAssert assert1=new SoftAssert();
        assert1.assertTrue(url.contains("kids-toys"));
    }
    @Test
    public void TestCase2() throws IOException, InterruptedException
     {
        test = reports.createTest("Test 2", "Clothes");

        FileInputStream fs = new FileInputStream("C:\\Users\\91822\\Desktop\\Testing\\Model_Lab\\modelLab_firstcry.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.firstcry.com");
        driver.manage().window().maximize();

        WebElement searchBar = driver.findElement(By.xpath("//*[@id='search_box']"));
        searchBar.click();
        searchBar.sendKeys(row.getCell(1).getStringCellValue(), Keys.ENTER);

        driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/ul/li[4]")).click();
        Thread.sleep(2000);

        String label1=driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/div[2]/div[1]/div[1]/h1")).getText();
        SoftAssert assert1=new SoftAssert();
        assert1.assertTrue(label1.contains("Ethnic Wear"));
    }
    
    @Test
    public void test3() throws Exception
     {
        test = reports.createTest("Test 2", "Clothes");

        FileInputStream fs = new FileInputStream("C:\\Users\\91822\\Desktop\\Testing\\Model_Lab\\modelLab_firstcry.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.firstcry.com");
        driver.manage().window().maximize();

        WebElement searchBar = driver.findElement(By.xpath("//*[@id='search_box']"));
        searchBar.click();
        searchBar.sendKeys(row.getCell(2).getStringCellValue(), Keys.ENTER);

        String title=driver.findElement(By.xpath("//*[@id='maindiv']/div[1]/div/div[1]/div[2]/a")).getText();
        driver.findElement(By.xpath("//*[@id='maindiv']/div[1]/div/div[1]/div[2]/a")).click();
        Thread.sleep(4000);

        Set<String>s=driver.getWindowHandles();
        for(String it:s)
        {
           if(!it.equals(driver.getWindowHandle()))
           {
              driver.switchTo().window(it);
              break;
           }
        }
        String product=driver.findElement(By.xpath("//*[@id=\"prod_name\"]")).getText();

        SoftAssert assert1=new SoftAssert();
        assert1.assertEquals(title,product);
    }

    @AfterMethod
    public void afterTest(ITestResult result) throws Exception
    {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\91822\\Desktop\\Testing\\Model_Lab\\report\\"+result.getName()+".png";
        FileUtils.copyFile(screenshot,new File(path));
        test.addScreenCaptureFromPath(path);

        if(result.getStatus()==ITestResult.FAILURE)
        {
            test.log(Status.FAIL, "TestCase Failed: "+result.getName());
            test.log(Status.FAIL, "Testcase Failed Reason: "+result.getThrowable());

        }
        else if (result.getStatus()==ITestResult.SUCCESS)
        { test.log(Status.PASS, "Test CAse Passed: "+result.getName());
        }
      else if (result.getStatus()==ITestResult.SKIP)
        { test.log(Status.SKIP, "Test CAse Skipped: "+result.getName());
        }
    }

    @AfterTest
    public void close()
    {
        reports.flush();
    }
}
