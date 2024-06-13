package ltd.newbee.mall.controller.admin;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class SeckillTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.gecko.driver", "C:\\Users\\cjh\\Downloads\\geckodriver-v0.34.0-win64\\geckodriver.exe");
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void seckill() {
    driver.get("http://localhost:28079/admin/seckill");
    driver.manage().window().setSize(new Dimension(1170, 1098));
    driver.findElement(By.cssSelector(".btn-info:nth-child(1)")).click();
    driver.findElement(By.id("goodsId")).click();
    driver.findElement(By.id("goodsId")).sendKeys("10005");
    driver.findElement(By.cssSelector(".modal-content")).click();
    driver.findElement(By.id("seckillForm")).click();
    driver.findElement(By.id("seckillPrice")).click();
    driver.findElement(By.id("seckillPrice")).sendKeys("1");
    driver.findElement(By.id("seckillNum")).click();
    driver.findElement(By.id("seckillNum")).sendKeys("1");
    driver.findElement(By.cssSelector(".btn-outline-info:nth-child(2)")).click();
    driver.findElement(By.id("seckillBegin")).click();
    driver.findElement(By.cssSelector(".daterangepicker:nth-child(14) .applyBtn")).click();
    driver.findElement(By.id("seckillEnd")).click();
    driver.findElement(By.cssSelector(".daterangepicker:nth-child(15) .applyBtn")).click();
    driver.findElement(By.id("seckillRank")).click();
    driver.findElement(By.id("seckillRank")).sendKeys("1");
    driver.findElement(By.cssSelector(".modal-footer")).click();
    driver.findElement(By.id("saveButton")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();
    driver.findElement(By.id("jqg_jqGrid_28")).click();
    driver.findElement(By.cssSelector(".btn-info:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.id("seckillNum"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("seckillNum"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("seckillNum"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("seckillNum")).click();
    driver.findElement(By.id("seckillNum")).sendKeys("11");
    driver.findElement(By.id("saveButton")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();
  }
}
