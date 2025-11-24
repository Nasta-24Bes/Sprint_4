package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.*;

public class MainPage {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    //Кнопка куки
    private final By cookieAcceptButtonLocator = By.id("rcc-confirm-button");
    //Верхняя кнопка "Заказать"
    private final By buttonUpLocator = By.cssSelector("button.Button_Button__ra12g");
    //Нижняя кнопка "Заказать"
    private final By buttonDownLocator = By.xpath("(//button[text()='Заказать'])[2]");

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        acceptCookiesIfNeeded();
    }

    public void clickQuestion(int id) {
        By questionId = By.id(QUESTION_TEMPLATE + id);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(questionId));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
    }

    //Принятие куки
    public void acceptCookiesIfNeeded() {
        var buttons = webDriver.findElements(cookieAcceptButtonLocator); // поиск кнопки принятия куки
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }
    public String getAnswer(int id) {
        By answer = By.id(String.format(ANSWER_TEMPLATE + id));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answer)); // добавил ожидание текста ответа
        return webDriver.findElement(answer).getText();
    }

    public String clickAndGetAnswer(int id) {
        clickQuestion(id);
        return getAnswer(id);
    }

    //нажатие на верхнюю кнопку "Заказать"
    public void clickUpperOrderButton() {
        WebElement buttonUp = wait.until(ExpectedConditions.presenceOfElementLocated(buttonUpLocator));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", buttonUp);
    }

    //Нажатие на нижнюю кнопку "Заказать"
    public void clickDownOrderButton() {
        WebElement buttonDown = wait.until(ExpectedConditions.presenceOfElementLocated(buttonDownLocator));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", buttonDown);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", buttonDown);
    }
}
