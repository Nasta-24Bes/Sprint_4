package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory; //Укажите свой путь, если класс лежит в другом месте

import java.time.Duration;

public class BaseTest {

    protected WebDriver webDriver; // Сделаем доступным для дочерних классов

    @Before
    public void setUp() {
        webDriver = WebDriverFactory.createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
