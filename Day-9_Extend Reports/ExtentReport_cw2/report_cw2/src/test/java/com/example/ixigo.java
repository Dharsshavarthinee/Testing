package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class ixigo
{
    
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
    @BeforeMethod
    public void setup()
    {
        ExtentSparkReporter sparkReporter=new ExtentSparkReporter("C:\\Users\\91822\\Desktop\\Testing\\Day-9_Extend Reports\\ExtentReport_cw2\\reportCW2.html");
        report=new ExtentReports();
        report.attachReporter(sparkReporter);
        test=report.createTest("test2", "testing description");
        
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test(dataProvider = "dat1")
    public void test1(String from,String to,String departure,String return1,String tc)
    {
        driver.get("https://www.ixigo.com/");
        WebElement element1=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div/div/div/p"));
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].innerText = arguments[1];",element1, from); 
        WebElement element2=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div/p"));
        js.executeScript("arguments[0].innerText = arguments[1];", element2,to);
        WebElement element3=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div/div/div/div/p[2]"));
        js.executeScript("arguments[0].innerText = arguments[1];", element3,departure);
        WebElement element4=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div/div/div/p"));
        js.executeScript("arguments[0].innerText = arguments[1];", element4,return1);
        WebElement element5=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div/div/div/div/p[2]"));
        js.executeScript("arguments[0].innerText = arguments[1];", element5,tc);
         driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/button")).click();
    }
    @Test
    public void test2()
    {
        driver.get("https://www.ixigo.com/");
        driver.findElement(By.xpath("/html/body/main/div[3]/div[2]/div/div[2]/div[1]/p[1]")).click();
       Set<String> id=driver.getWindowHandles();
       for(String it:id)
       {
        if(!it.equals(driver.getWindowHandle()))
        {
            driver.switchTo().window(it);
            break;
        }
       }
        Assert.assertTrue(driver.getCurrentUrl().contains("about"));
    }

    @AfterMethod
    public void am(ITestResult result) throws IOException
    {
        if(result.getStatus()==ITestResult.FAILURE)
        {
            test.log(Status.FAIL,"testcase failed:"+result.getName());
            test.log(Status.FAIL, "testcase failed reason:"+result.getThrowable());
            File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String path="C:\\Users\\91822\\Desktop\\Testing\\Day-9_Extend Reports\\ExtentReport_cw2\\"+result.getName()+".png";
            FileUtils.copyFile(file,new File(path));
            test.addScreenCaptureFromPath(path);
        }
        else if(result.getStatus()==ITestResult.SUCCESS)
        {
            test.log(Status.PASS,"Testcase passed: "+result.getName());
        }
        else if(result.getStatus()==ITestResult.SKIP)
        {
             test.log(Status.SKIP,"testcase skipped:"+result.getName());
        }
    }
        
    @DataProvider(name="dat1")
    public Object[][] dp1() throws IOException
    {
        FileInputStream fs=new FileInputStream("C:\\Users\\91822\\Desktop\\Testing\\Day-9_Extend Reports\\ExtentReport_cw2\\report_cw2.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(fs);
        XSSFSheet sheet=workbook.getSheetAt(0);
        int rowcount=sheet.getLastRowNum();
        int colcount=sheet.getRow(0).getLastCellNum();
        Object[][] obj=new Object[rowcount][colcount];
        for(int i=0;i<rowcount;i++)
        {
            Row row=sheet.getRow(i+1);
            for(int j=0;j<colcount;j++)
            {
                 obj[i][j]=row.getCell(j).getStringCellValue();
            }
        }
        return obj;
    }
}
