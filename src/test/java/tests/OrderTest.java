package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderForm;
import utils.Constants;
import utils.WebDriverFactory;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private MainPage mainPage;
    private OrderForm orderForm;
    private final String orderButtonType;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String[] colors;
    private final String comment;

    public OrderTest(String orderButtonType, String name, String surname, String address,
                     String metroStation, String phoneNumber, String deliveryDate,
                     String rentalPeriod, String[] colors, String comment) {
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.colors = colors;
        this.comment = comment;
    }

    @org.junit.Before
    public void setUpTest() {
        webDriver.get(Constants.MAIN_URL);
        mainPage = new MainPage(webDriver);
        orderForm = new OrderForm(webDriver);
    }

    @Parameterized.Parameters(name = "Тест {index}: {0} {1} {2}")
    public static Object[][] data() {
        return new Object[][]{
                {
                        "верхняя кнопка",
                        "Иван", "Иванов", "ул. Потапова д 1", "Кожуховская", "89999999999",
                        "30.11.25", "трое суток", new String[]{"black"}, "позвонить заранее"
                },
                {
                        "нижняя кнопка",
                        "Вася", "Васильев", "ул. Анкина д 12", "Аннино", "89888888888",
                        "25.11.25", "двое суток", new String[]{"grey"}, "скорее"
                }
        };
    }

    @Test
    public void orderFormTest() {
        mainPage.acceptCookiesIfNeeded();
        if ("верхняя кнопка".equals(orderButtonType)) {
            mainPage.clickUpperOrderButton();
        } else {
            mainPage.clickDownOrderButton();
        }

        orderForm.fillFirstStep(name, surname, address, metroStation, phoneNumber);
        orderForm.clickNextButton();
        orderForm.fillSecondStep(deliveryDate, rentalPeriod, colors, comment);
        orderForm.completeOrderButton();
        orderForm.conformOrder();
        assertTrue("Окно о создании заказа не появилось", orderForm.isCheckOrderCompletePopupDisplayed());
    }
}
