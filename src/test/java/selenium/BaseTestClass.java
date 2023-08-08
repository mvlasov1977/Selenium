package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTestClass {
    public WebDriver chromeDriver;
    @BeforeEach
    public void init(){
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
    }
    @AfterEach
    public void clear(){chromeDriver.quit();}

}
