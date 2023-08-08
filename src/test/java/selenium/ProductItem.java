package selenium;

public class ProductItem {
    private String name;
    private Double price;
    private String currency;
    public ProductItem(String name, Double price, String currency){
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }
}
