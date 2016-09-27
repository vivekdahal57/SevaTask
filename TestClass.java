

import java.util.concurrent.TimeUnit;


import com.google.common.base.Predicate;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82325 on 9/27/2016.
 */
public class TestClass {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    public static final String CHROME_DRIVER_PATH = "src/test/resource/drivers/chromedriver.exe";
    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public String courseLinkValue = null;
    public String mainMenu;
    public String subMenu;

    public TestClass fullLoad() {
        new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class)
                .until(new Predicate<WebDriver>() {
                    public boolean apply(WebDriver arg0) {
                        return true;
                    }
                });
        return this;
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        baseUrl = "https://broadwayinfosys.com/";
        courseLinkValue = "All Courses";
        mainMenu = "DOT NET";
        subMenu = "Asp.Net";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testToGetTitle() {
        driver.get(baseUrl);
        try {
            assertEquals("Computer Training Institute in Kathmandu, Nepal | IT Training Nepal – Computer Learning Center in Nepal", driver.getTitle());
            logger.info("Page Title is a Match" + driver.getTitle());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }

    }

    @Test
    public void testToGetToAllCourseList() {
        driver.get(baseUrl);
        driver.findElement(By.linkText(courseLinkValue)).click();
        try {
            assertEquals("IT Courses in Nepal | View Computer Courses from Broadway Infosys Nepal", driver.getTitle());
            logger.info("Page Title is a Match. Navigation Successful for \"" + courseLinkValue + "\" page");
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testToGetToAllNavigationLinksByHoverAction() {
        driver.get(baseUrl);
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.linkText(mainMenu));
        action.moveToElement(element).perform();
        Actions innerAction = new Actions(driver);
        WebElement innerElement = driver.findElement(By.linkText(subMenu));
        innerAction.moveToElement(innerElement).click().perform();
        logger.info(""+mainMenu+"=>>"+subMenu+" Clicked");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}
