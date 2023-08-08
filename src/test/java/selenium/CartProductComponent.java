package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartProductComponent {
    private WebElement parentElement;
    private By name = By.className("inventory_item_name");
    private By price = By.className("inventory_item_price");
    private int buttonIndex;
    private String xpathButtonPattern = "//*[@class=\"inventory_item\"]//button[@id=\"ID\"]";
    private By productItemButton = By.xpath("//*[@class=\"cart_item\"]//button");

    public CartProductComponent(WebElement parentElement, int buttonIndex) {
        this.parentElement = parentElement;
    }
    private String extractCurrencyFromPrice(String price){
        return price.substring(0,1);
    }
    private Double extractPriceValueFromPrice(String price){
        return Double.valueOf(price.substring(1));
    }
    public String getProductName(){
        return parentElement.findElement(name).getText();
    }
    public Double getProductPrice(){
        return extractPriceValueFromPrice(parentElement.findElement(price).getText());
    }
    public String getProductCurrency(){
        return extractCurrencyFromPrice(parentElement.findElement(price).getText());
    }
    public By getProductButton(){
        return By.xpath(xpathButtonPattern.replaceAll("ID", parentElement.findElements(productItemButton).get(buttonIndex).getAttribute("id")));
    }

}
