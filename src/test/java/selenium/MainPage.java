package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MainPage {
    private final Long waiter = 5L; // seconds
    private final String pageName = "Products";
    private final String pageSign = "//*[@class=\"header_secondary_container\"]/span";
    private By pageSortContainer = By.xpath("//*[@class=\"product_sort_container\"]");
    private By shoppingCartContainer = By.xpath("//*[@id=\"shopping_cart_container\"]");
    private boolean pageSuccess = false;
    private WebDriver driver;
    private MenuPage menuPage;
    public MainPage(WebDriver driver) {
        this.driver = driver;

        WaitersUtil.explicitWait(driver, By.xpath(pageSign), waiter);
        if ( !driver.findElement(By.xpath(pageSign)).getText().equals(pageName) ) {
            throw new IllegalStateException("Wrong page ! Expected -> " + pageName);
        }else{
            pageSuccess = true;
            menuPage = new MenuPage(driver);
        }
    }
    public void clickOnPageSortContainer(){
        Select mySelect = new Select(driver.findElement(pageSortContainer));
        mySelect.selectByValue("lohi");
    }
    public WebDriver clickToShoppingCart(){
        driver.findElement(shoppingCartContainer).click();
        return driver;
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
