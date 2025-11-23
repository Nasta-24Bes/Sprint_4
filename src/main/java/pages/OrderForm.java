package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    private final By firstNameLocator = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationLocator = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroStationOptionLocator = By.xpath("//div[@class='select-search__select']//button[contains(., '%s')]");
    private final By phoneLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By dateLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By commentLocator = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By nextButtonLocator = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");
    private final By completeNextButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");
    private final By conformOrderButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Да']");
    private final By popupLocator = By.xpath("//div[contains(@class, 'Order_Modal__YZ-d3')]//div[contains(text(), 'Заказ оформлен')]");
    private final By textLocator = By.className("Order_Header__BZXOb");
    private final By dropdownControlLocator = By.className("Dropdown-control");
    private final By dropdownMenuLocator = By.className("Dropdown-menu");
    private final By dropdownOptionLocator = By.xpath("//div[contains(@class, 'Dropdown-option') and text()='%s']");

    public OrderForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
    }

    //Заполнение первой формы заказа
    public void fillFirstStep(String name, String surname, String addr, String metro, String phoneNum) {
        setFirstName(name);
        setLastName(surname);
        setAddress(addr);
        setMetroStation(metro);
        setPhone(phoneNum);
    }

    public void setFirstName(String name) {
        webDriver.findElement(firstNameLocator).sendKeys(name);
    }

    public void setLastName(String surname) {
        webDriver.findElement(lastNameLocator).sendKeys(surname);
    }

    public void setAddress(String address) {
        webDriver.findElement(addressLocator).sendKeys(address);
    }

    public void setPhone(String phone) {
        webDriver.findElement(phoneLocator).sendKeys(phone);
    }


    public void setMetroStation(String metro) {
        webDriver.findElement(metroStationLocator).sendKeys(metro);
        WebElement stationOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format(metro, metroStationOptionLocator))
                )
        );
        assert stationOption != null;
        stationOption.click();
    }

    //Кнопка для продолжения оформления заказа
    public void clickNextButton() {
        webDriver.findElement(nextButtonLocator).click();
    }

    //Кнопка завершения заказа
    public void completeOrderButton() {
        webDriver.findElement(completeNextButtonLocator).click();
    }

    //Кнопка подтверждения оформления заказа
    public void conformOrder() {
        webDriver.findElement(conformOrderButtonLocator).click();
    }

    //Проверка появления Поп-апа с заказом
    public boolean isCheckOrderCompletePopupDisplayed() {
        var finalPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
        assert finalPopup != null;
        return finalPopup.isDisplayed();
    }

    //Выбор чек-бокса цвета
    public void clickCheckBoxIfExists(String checkBoxId) {
        WebElement checkBox = webDriver.findElement(By.id(checkBoxId));
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    //Массив цвета
    public void selectColors(String[] colors) {
        for (String color : colors) {
            clickCheckBoxIfExists(color);
        }
    }

    //Заполнение второй формы заказа
    public void fillSecondStep(String date, String rendPeriod, String[] colorIds, String comment) {
        setDate(date);
        dummyClick(); //  или sendKeys(Keys.TAB)
        selectRentalPeriod(rendPeriod);
        selectColors(colorIds);
        setComment(comment);


    }

    public void setDate(String date) {
        WebElement inputDate = webDriver.findElement(dateLocator);
        inputDate.sendKeys(date);
    }

    //Клик вне даты
    public void dummyClick() {
        webDriver.findElement(textLocator).click();
    }

    public void setComment(String comment) {
        webDriver.findElement(commentLocator).sendKeys(comment);
    }

    //Выбор в выпадающем списке срока
    public void selectRentalPeriod(String periodText) {
        openDropdown();
        selectOption(periodText);
    }

    // Открытие списка
    public void openDropdown() {
        WebElement dropdownControl = wait.until(ExpectedConditions.elementToBeClickable(dropdownControlLocator));
        assert dropdownControl != null;
        dropdownControl.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                dropdownMenuLocator
        ));
    }

    //Выбор пункта по тексту
    public void selectOption(String optionText) {
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format(optionText, dropdownOptionLocator))
                )
        );
        assert option != null;
        option.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                dropdownMenuLocator
        ));
    }
}
