package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MenuPage {
    private final Long waiter = 5L; // seconds
    private final String pageName = "";
    private final String pageSign = "//*[@class=\"bm-menu-wrap\"]";
    private boolean pageSuccess = false;
    private WebDriver driver;
    private By hiddenMenu = By.xpath("//*[@class=\"bm-menu-wrap\"]");
    private By logoutLink = By.id("logout_sidebar_link");
    private By actionButton = By.className("bm-burger-button");
    public MenuPage(WebDriver driver) {
        this.driver = driver;

        //WaitersUtil.explicitWait(driver, By.xpath(pageSign), waiter);
        if ( !driver.findElement(By.xpath(pageSign)).getText().equals(pageName) ) {
            throw new IllegalStateException("Wrong page ! Expected -> " + pageName);
        }else{
            pageSuccess = true;
        }
    }
    public boolean isPageSuccess(){
        return pageSuccess;
    }
    public MenuPage openMenuPage(){
        if ( this.isHiddenMenuStatus()) {
            driver.findElement(actionButton).click();
        }
        return this;
    }
    public MenuPage closeMenuPage(){
        if ( !this.isHiddenMenuStatus()) {
            driver.findElement(actionButton).click();
        }
        return this;
    }
    public LoginPage clickOnLogoutLink(){
        this.openMenuPage();
        WaitersUtil.explicitWait(driver, logoutLink, waiter);
        driver.findElement(logoutLink).click();
        return new LoginPage(driver);
    }
    public boolean isHiddenMenuStatus(){
        if ( driver.findElement(hiddenMenu)
                .getAttribute("aria-hidden")
                .toLowerCase().equals("true")){
            return true;
        }else{
            return false;
        }
    }
    public void clearDriver(){
        driver.quit();
    }
}
