package shop;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Basket {

    Map<String, Integer> items;
    ShopDB db;

    public static void main(String[] args) {
        Basket b = new Basket();
        b.addItem("art1");
        System.out.println(b.getTotalString());
        b.clearBasket();
        System.out.println(b.getTotalString());
        // check that adding a null String causes no problems
        String pid = null;
        b.addItem(pid);
        System.out.println(b.getTotalString());
    }

    public Basket() {
        db = ShopDB.getSingleton();
        items = new HashMap<String, Integer>();
    }


    public Map<String, Integer> getItems() {
        return items;
    }


    public void clearBasket() {
        items.clear();
    }


    public void updateItem(String pid, int quantity) {
        if ((items.get(pid) != null) && quantity == 0)
            items.remove(pid);
        else
            items.put(pid, quantity);
    }


    public void addItem(String pid) {
        if (pid != null) {
            Object quantity = items.get(pid);

            if (quantity == null) items.put(pid, 1);
            else items.put(pid, (int)quantity + 1);
        }
    }


    public int getTotal() {
        int total = 0;

        for (Map.Entry<String, Integer> e : getItems().entrySet()) {
            Product p = db.getProduct(e.getKey());
            total += p.price * e.getValue();
        }

        return total;
    }


    public String getTotalString() {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.00");
        double total = getTotal() / 100.0;
        sb.append(df.format(total));
        return "£" + sb;
    }
}
