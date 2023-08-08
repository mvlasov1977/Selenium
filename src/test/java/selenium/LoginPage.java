package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final Long waiter = 5L; // seconds
    private final String pageName = "Swag Labs";
    private final String pageSign = "//*[@class=\"login_container\"]/*[@class=\"login_logo\"]";
    private boolean pageSuccess = false;
    private By usernameInputField = By.id("user-name");
    private By passwordInputField = By.id("password");
    private By loginButton = By.id("login-button");
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        WaitersUtil.explicitWait(driver, By.xpath(pageSign), waiter);
        if ( !driver.findElement(By.xpath(pageSign)).getText().equals(pageName)){
            throw new IllegalStateException("Wrong page ! Expected -> " + pageName);
        }else{
            pageSuccess = true;
        }
    }

    public boolean isPageSuccess() {
        return pageSuccess;
    }

    public LoginPage enterUserName(String userName){
        driver.findElement(usernameInputField).sendKeys(userName);
        return this;
    }
    public LoginPage enterPassword(String password){
        driver.findElement(passwordInputField).sendKeys(password);
        return this;
    }
    public MainPage clickOnLoginButton(){
        driver.findElement(loginButton).click();
        return new MainPage(driver);
    }
}
