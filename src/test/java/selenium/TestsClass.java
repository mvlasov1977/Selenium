package selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;

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
    private WebDriver initDriver(String myUrl){
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().deleteAllCookies();
        chromeDriver.manage().window().maximize();
        chromeDriver.get(myUrl);
        return chromeDriver;
    }
    @Test
    public void verifyLogin(){
        MainPage myMainPage = loggingIn(initDriver(myStartPageUrl), myUsername, myPassword);

        Assertions.assertTrue(myMainPage.isPageSuccess());

        myMainPage.clearDriver();
    }
    @Test
    public void verifyLogoutMainPage(){
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
    public void verifyLogoutCartPage(){
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
    public void verifyAllProductsShowed(){
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
    public void verifyAllProductsSortedByPrice(){
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
    public void verifySelectedProductsShowedInCart(){
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
