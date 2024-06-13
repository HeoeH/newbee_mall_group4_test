package ltd.newbee.mall.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AdmingoodsTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

  @Before
  public void setUp() {
    System.setProperty("webdriver.gecko.driver", "E:\\桌面\\软件测试\\geckodriver-v0.34.0-win64\\geckodriver.exe");
    System.setProperty("webdriver.firefox.bin", "F:\\Firefox\\firefox.exe");
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void admingoods() {
    driver.get("http://localhost:28079/admin/goods");
    driver.manage().window().setSize(new Dimension(2062, 1118));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\31 0925 > .jqgrid-multibox")));

    driver.findElement(By.cssSelector("#\\31 0925 > .jqgrid-multibox")).click();
    driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    driver.findElement(By.id("tag")).click();
    driver.findElement(By.id("saveButton")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\31 0925 > .jqgrid-multibox")));

    driver.findElement(By.cssSelector("#\\31 0925 > .jqgrid-multibox")).click();
    driver.findElement(By.cssSelector(".btn-danger")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".btn-danger"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".swal-button--confirm")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();
    driver.findElement(By.id("jqg_jqGrid_10925")).click();
    driver.findElement(By.cssSelector(".btn-success:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".swal-button--confirm")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();
  }
}
