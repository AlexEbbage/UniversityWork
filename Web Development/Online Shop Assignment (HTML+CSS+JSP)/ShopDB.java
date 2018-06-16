
package shop;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ShopDB {

    Connection con;
    static int nOrders = 0;
    static ShopDB singleton;

    public static void main(String[] args) throws Exception  {
        // simple method to test that ShopDB works
        System.out.println("Got this far...");
        ShopDB db = new ShopDB();
        System.out.println("created shop db");
        Basket basket = new Basket();
        System.out.println("created the basket");

        System.out.println("Testing getAllProducts");
        Collection c = db.getAllProducts();
        for (Iterator i = c.iterator(); i.hasNext() ; ) {
            Product p = (Product) i.next();
            System.out.println( p );
        }
        System.out.println("Testing getProduct(pid)");
        Product product = db.getProduct("art1");
        System.out.println( product );

        System.out.println("Testing order: ");
        basket.addItem( "game1" );
        System.out.println("added an item");
        db.order( basket , "Simon" );
        System.out.println("order done");

    }

    public ShopDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("loaded class");
            con = DriverManager.getConnection("jdbc:hsqldb:file:\\tomcat\\webapps\\ass2\\shopdb", "sa", "");
            System.out.println("created con");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static ShopDB getSingleton() {
        if (singleton == null) {
            singleton = new ShopDB();
        }
        return singleton;
    }

    public ResultSet getProducts() {
        try {
            Statement s = con.createStatement();
            System.out.println("Created statement");
            ResultSet rs = s.executeQuery("Select * from Product");
            System.out.println("Returning result set...");
            return rs;
        }
        catch(Exception e) {
            System.out.println( "Exception in getProducts(): " + e );
            return null;
        }
    }

    public Collection<Product> getAllProducts() {
        return getProductCollection("Select * from Product");
    }


    public Collection<Product> getSelectProducts(String query, int type) {
        String fullQuery = "";
        if(type == 0){
            fullQuery = "SELECT * FROM PRODUCT WHERE UPPER(TITLE) LIKE UPPER('%" + query + "%')";
        }
        else{
            fullQuery = "SELECT * FROM PRODUCT WHERE UPPER(ARTIST) LIKE UPPER('%" + query + "%')";
        }
        return getProductCollection(fullQuery);
    }


    public Product getProduct(String pid) {
        try {
            // re-use the getProductCollection method
            // even though we only expect to get a single Product Object
            String query = "Select * from Product where PID = '" + pid + "'";
            Collection<Product> c = getProductCollection( query );
            Iterator<Product> i = c.iterator();
            return i.next();
        }
        catch(Exception e) {
            // unable to find the product matching that pid
            return null;
        }
    }

    public Collection<Product> getProductCollection(String query) {
        LinkedList<Product> list = new LinkedList<Product>();
        try {
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery(query);
            while ( rs.next() ) {
                Product product = new Product(
                        rs.getString("PID"),
                        rs.getString("Artist"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("price"),
                        rs.getString("thumbnail"),
                        rs.getString("fullimage")
                );
                list.add( product );
            }
            return list;
        }
        catch(Exception e) {
            System.out.println( "Exception in getProducts(): " + e );
            return null;
        }
    }

    public void order(Basket basket , String customer) {
        try {
            String orderId = System.currentTimeMillis() + ":" + nOrders++;

            for(String pid : basket.getItems().keySet()){
                Product p = getProduct(pid);
                order(con, p, orderId, customer, basket.getItems().get(pid));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    private void order(Connection con, Product p, String orderId, String customer, int quantity) throws Exception {
        System.out.println("INSERT INTO ORDERS (PID, ORDERID, EMAIL, QUANTITY, PRICE) VALUES ("+p.PID+", "+orderId+", "+customer+", "+quantity+", "+p.price+")");
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORDERS (PID, ORDERID, EMAIL, QUANTITY, PRICE) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, p.PID);
        ps.setString(2, orderId);
        ps.setString(3, customer);
        ps.setInt(4, quantity);
        ps.setInt(5, p.price);
        ps.executeUpdate();

    }


}
