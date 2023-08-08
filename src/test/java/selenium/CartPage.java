package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final Long waiter = 5L; // seconds
    private final String pageName = "Your Cart";
    private final String pageSign = "//*[@class=\"header_secondary_container\"]/span";
    private boolean pageSuccess = false;
    private WebDriver driver;
    private MenuPage menuPage;
    public CartPage(WebDriver driver) {
        this.driver = driver;

        WaitersUtil.explicitWait(driver, By.xpath(pageSign), waiter);
        if ( !driver.findElement(By.xpath(pageSign)).getText().equals(pageName) ) {
            throw new IllegalStateException("Wrong page ! Expected -> " + pageName);
        }else{
            pageSuccess = true;
            menuPage = new MenuPage(driver);
        }
    }
    public boolean isPageSuccess(){
        return pageSuccess;
    }

    public MenuPage getMenuPage() {
        return menuPage;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clearDriver(){
        driver.quit();
    }
}
