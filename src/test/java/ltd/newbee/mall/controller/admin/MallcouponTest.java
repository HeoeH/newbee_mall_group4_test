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

import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MallcouponTest {
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
  public void mallcoupon() {
    driver.get("http://localhost:28079/index.html");
    driver.manage().window().setSize(new Dimension(2576, 1408));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // 执行登录操作
    login(wait);

    // 等待并点击“我的优惠券”
    WebElement myCoupons = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("我的优惠券")));
    myCoupons.click();

    // 等待并点击第一个优惠券
    WebElement firstCoupon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".quan-item:nth-child(1) > .q-lable")));
    firstCoupon.click();

    // 等待并点击关闭按钮
    WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("X")));
    closeButton.click();

    // Hover over the close button
    Actions builder = new Actions(driver);
    builder.moveToElement(closeButton).perform();

    // Move to the top left corner of the body
    WebElement bodyElement = driver.findElement(By.tagName("body"));
    builder.moveToElement(bodyElement, 0, 0).perform();

    // 等待并点击确认按钮
    WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal-button--confirm")));
    confirmButton.click();

    // 等待并点击“新蜂商城”
    WebElement newBeeMall = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("新蜂商城")));
    newBeeMall.click();

    // 等待并点击“优惠券”
    WebElement coupons = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("优惠券")));
    coupons.click();

    // 等待并点击第二个优惠券的操作按钮
    WebElement secondCouponOpButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".quan-item:nth-child(2) > .q-opbtns")));
    secondCouponOpButton.click();

    // 等待并点击通用确认按钮
    WebElement generalConfirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal-button")));
    generalConfirmButton.click();
  }

  private void login(WebDriverWait wait) {
    // 等待并点击登录按钮，假设页面有一个登录按钮
    WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("登录")));
    loginButton.click();

    // 等待并填写用户名
    WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    usernameField.sendKeys("17857144877"); // 替换为你的用户名

    // 等待并填写密码
    WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
    passwordField.sendKeys("1243761810"); // 替换为你的密码

    // 等待并点击提交按钮
    WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
    submitButton.click();

    // 可选：等待登录完成，检查某个登录成功后的元素
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("我的优惠券")));
  }
}
