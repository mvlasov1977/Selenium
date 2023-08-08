package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductComponent {

    private WebElement parentElement;
    private int buttonIndex;
    private String xpathButtonPattern = "//*[@class=\"inventory_item\"]//button[@id=\"ID\"]";
    //*[@class="inventory_item"]//button[@id="add-to-cart-sauce-labs-bike-light"]
    private By name = By.className("inventory_item_name");
    private By price = By.className("inventory_item_price");
    private By productItemButton = By.xpath("//*[@class=\"inventory_item\"]//button");


    public ProductComponent(WebElement parentElement, int buttonIndex){
        this.parentElement = parentElement;
        this.buttonIndex = buttonIndex;
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
    public boolean isProductButtonSelected(){
        if ( parentElement.findElement(productItemButton)
                .getAttribute("data-test")
                .equals("remove-sauce-labs-backpack") ){
            return true;
        }
        return false;
    }
}
