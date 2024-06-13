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
public class AdmincarouselsTest {
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
  public void admincarousels() {
    driver.get("http://localhost:28079/admin/carousels");
    driver.manage().window().setSize(new Dimension(2062, 1118));
    driver.findElement(By.cssSelector("#\\31 3 > .jqgrid-multibox")).click();
    driver.findElement(By.cssSelector(".btn-info:nth-child(2)")).click();
    driver.findElement(By.id("carouselRank")).click();
    driver.findElement(By.id("carouselRank")).sendKeys("1");
    driver.findElement(By.id("saveButton")).click();
    driver.findElement(By.cssSelector(".swal-button")).click();
  }
}
