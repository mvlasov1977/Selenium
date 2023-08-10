package selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.slf4j.MDC.put;

public class TestsClass {
    private final Double myLimitSelectPrice = 10.0;
    private final String myStartPageUrl = "https://www.saucedemo.com/";
    private final String myMainPageUrl = "https://www.saucedemo.com/inventory.html";
    private final String myCartPageUrl = "https://www.saucedemo.com/cart.html";
    private final String myUsername = "standard_user";
    private final String myPassword = "secret_sauce";
    private MainPage loggingIn(WebDriver chromeDriver, String login, String password){
        return new LoginPage(chromeDriver)
                .enterUserName(login)
                .enterPassword(password)
                .clickOnLoginButton();
    }
    private WebDriver initDriver(String myUrl) throws MalformedURLException {
        //WebDriver chromeDriver = new ChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "114.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "15m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});

            /* How to enable video recording */
            put("enableVNC", true);
        }});
        RemoteWebDriver chromeDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

        chromeDriver.manage().deleteAllCookies();
        chromeDriver.manage().window().maximize();
        chromeDriver.get(myUrl);
        return chromeDriver;
    }
    @Test
    public void verifyLogin() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);

        Assertions.assertTrue(myMainPage.isPageSuccess());

        myMainPage.clearDriver();
    }
    @Test
    public void verifyLogoutMainPage() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);

        boolean logoutIsSuccess = myMainPage
                .getMenuPage()
                .openMenuPage()
                .clickOnLogoutLink()
                .isPageSuccess();

        Assertions.assertTrue(logoutIsSuccess);

        myMainPage.clearDriver();
    }
    @Test
    public void verifyLogoutCartPage() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);
        myMainPage.getDriver().get(myCartPageUrl);
        CartPage myCartPage = new CartPage(myMainPage.getDriver());

        boolean logoutIsSuccess = myCartPage
                .getMenuPage()
                .openMenuPage()
                .clickOnLogoutLink()
                .isPageSuccess();

        Assertions.assertTrue(logoutIsSuccess);

        myCartPage.clearDriver();
    }
    @Test
    public void verifyAllProductsShowed() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);
        ProductsPage myProductPage = new ProductsPage(myMainPage.getDriver());

        TestDataClass myTestDataClass = new TestDataClass();
        ArrayList<ProductItem> productsArray = myTestDataClass.grabProductComponentsFromProductsPage(myProductPage);

        myTestDataClass.SortByNameThenPrice(productsArray);
        myTestDataClass.SortByNameThenPrice(myTestDataClass.myProductItems);

        Assertions.assertTrue(myTestDataClass.isSameDataSet(productsArray, myTestDataClass.myProductItems));

        myProductPage.clearDriver();
    }
    @Test
    public void verifyAllProductsSortedByPrice() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);
        myMainPage.clickOnPageSortContainer();
        ProductsPage myProductPage = new ProductsPage(myMainPage.getDriver());

        TestDataClass myTestDataClass = new TestDataClass();
        ArrayList<ProductItem> productsArray = myTestDataClass.grabProductComponentsFromProductsPage(myProductPage);

        myTestDataClass.sortByPriceThenName(productsArray);
        myTestDataClass.sortByPriceThenName(myTestDataClass.myProductItems);

        Assertions.assertTrue(myTestDataClass.isSameDataSet(productsArray, myTestDataClass.myProductItems));

        myProductPage.clearDriver();
    }
    @Test
    public void verifySelectedProductsShowedInCart() throws MalformedURLException {
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);
        ProductsPage myProductPage = new ProductsPage(myMainPage.getDriver());

        TestDataClass myTestDataClass = new TestDataClass();
        ArrayList<ProductItem> selectedProductsArray = myTestDataClass
                .selectAndGrabSelectedProductComponentsFromProductsPageByLimitPrice(myProductPage, myLimitSelectPrice );

        CartPage myCartPage = new CartPage(myMainPage.clickToShoppingCart());

        CartProductsPage myCartProductPage = new CartProductsPage(myMainPage.getDriver());
        ArrayList<ProductItem> cartProductsArray = myTestDataClass
                .grabCartProductComponentsFromCartProductsPage(myCartProductPage);

        Assertions.assertTrue(myTestDataClass.isSameDataSet(selectedProductsArray, cartProductsArray));

        myCartProductPage.clearDriver();
    }
}
