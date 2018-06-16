package shop;

import java.text.DecimalFormat;

public class Product {
    public String PID;
    public String artist;
    public String title;
    public String description;
    public int price;
    public String thumbnail;
    public String fullimage;

    public Product(
            String PID, String artist, String title,
            String description, int price, String thumbnail, String fullimage) {
        this.PID = PID;
        this.artist = artist;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
        this.fullimage = fullimage;
    }


    public String getPrice(int price) {
        DecimalFormat df = new DecimalFormat("0.00");
        double priceD = (double)price / 100;
        String cost = df.format(priceD);
        return "£" + cost;
    }
}
