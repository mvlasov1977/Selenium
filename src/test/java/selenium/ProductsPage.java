package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    @FindAll({@FindBy (xpath = "//*[@class=\"inventory_item\"]")})
    public List<WebElement> products;
    public ProductsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public WebDriver getDriver(){
        return driver;
    }
    public void clearDriver(){
        driver.quit();
    }
}
