package selenium;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;

public class TestDataClass {

    public ArrayList<ProductItem> myProductItems;
    public TestDataClass (){
        myProductItems = new ArrayList<>();
        myProductItems.add(new ProductItem("Sauce Labs Backpack",29.99, "$"));
        myProductItems.add(new ProductItem("Sauce Labs Bike Light",9.99, "$"));
        myProductItems.add(new ProductItem("Test.allTheThings() T-Shirt (Red)",15.99,"$"));
        myProductItems.add(new ProductItem("Sauce Labs Fleece Jacket",49.99, "$"));
        myProductItems.add(new ProductItem("Sauce Labs Onesie",7.99, "$"));
        myProductItems.add(new ProductItem("Sauce Labs Bolt T-Shirt",15.99,"$"));
    }
    public void sortByPriceThenName(ArrayList<ProductItem> inputArray){
        Comparator<ProductItem> comparator = Comparator.comparing(ProductItem::getPrice)
                .thenComparing(ProductItem::getName)
                .thenComparing(ProductItem::getCurrency);
        inputArray.sort(comparator);
    }
    public void SortByNameThenPrice(ArrayList<ProductItem> inputArray){
        Comparator<ProductItem> comparator = Comparator.comparing(ProductItem::getName)
                .thenComparing(ProductItem::getPrice)
                .thenComparing(ProductItem::getCurrency);
        inputArray.sort(comparator);
    }

    public boolean isSameDataSet(ArrayList<ProductItem> inputArrayFirst, ArrayList<ProductItem> inputArraySecond) {
        if (inputArrayFirst.size() != inputArraySecond.size()) {
            return false;
        }
            int myCounter = 0;
            boolean resultComparing;

            for (ProductItem myItem : inputArraySecond) {
                resultComparing = myItem.getName().equals(inputArrayFirst.get(myCounter).getName())
                        && myItem.getPrice().equals(inputArrayFirst.get(myCounter).getPrice());
                if (!resultComparing) {
                    return false;
                }
                myCounter++;
            }
            return true;
    }
    public void printInputData(ArrayList<ProductItem> inputArray, String myHeader){
        System.out.println("----------" + myHeader + "----------");
        for (ProductItem Item : inputArray) {
            System.out.println(Item.getName() + " - " + Item.getCurrency() + " - " + Item.getPrice());
        }
    }
    public ArrayList<ProductItem> grabProductComponentsFromProductsPage(ProductsPage inputProductsPage){
        ArrayList<ProductItem> productsArray = new ArrayList<>();

        int i = 0;
        for (WebElement webItem : inputProductsPage.products) {
            ProductComponent productComponent = new ProductComponent(webItem, i);
            i++;
            productsArray.add(new ProductItem(
                      productComponent.getProductName()
                    , productComponent.getProductPrice()
                    , productComponent.getProductCurrency()));
        }
        return productsArray;
    }
    public ArrayList<ProductItem> grabCartProductComponentsFromCartProductsPage(CartProductsPage inputCartProductsPage){
        ArrayList<ProductItem> productsArray = new ArrayList<>();

        int i = 0;
        for (WebElement webItem : inputCartProductsPage.products) {
            CartProductComponent productComponent = new CartProductComponent(webItem, i);
            i++;
            productsArray.add(new ProductItem(
                    productComponent.getProductName()
                    , productComponent.getProductPrice()
                    , productComponent.getProductCurrency()));
        }
        return productsArray;
    }
    public ArrayList<ProductItem> selectAndGrabSelectedProductComponentsFromProductsPageByLimitPrice(ProductsPage inputProductsPage, Double limitPrice){
        ArrayList<ProductItem> productsArray = new ArrayList<>();
        ArrayList<ProductComponent> tst = new ArrayList<>();

        int i = 0;
        for (WebElement webItem : inputProductsPage.products) {
            ProductComponent productComponent = new ProductComponent(webItem, i);
            i++;
            tst.add(productComponent);
            if ( productComponent.getProductPrice() < limitPrice ) {
                inputProductsPage
                        .getDriver()
                        .findElement(productComponent.getProductButton())
                        .click();
                productsArray.add(new ProductItem(
                        productComponent.getProductName()
                        , productComponent.getProductPrice()
                        , productComponent.getProductCurrency()));
            }
        }
        return productsArray;
    }

}
