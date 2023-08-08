package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class WaitersUtil {
    public static WebElement explicitWait(WebDriver webDriver, By by, Long secondsTimer){
        WebDriverWait explicitWait = new WebDriverWait(webDriver, Duration.ofSeconds(secondsTimer));
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
