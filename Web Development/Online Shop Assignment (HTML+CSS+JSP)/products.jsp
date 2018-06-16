<%@ page import="shop.Product" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>

<jsp:useBean id='db' scope='session' class='shop.ShopDB'/>
<jsp:useBean id='basket' scope='session' class='shop.Basket'/>

<html>
<body>
<%@ include file="header.jsp" %>
<div class="content" align="center">
    <%
        String productString = request.getParameter("Product");
        String artistString = request.getParameter("Artist");
        Collection<Product> products = new ArrayList<Product>();
        if ((productString != null) && (artistString == null)) {
            products = db.getSelectProducts(productString, 0);
            if (products.size() == 0) { %>
            <div class="ProductTitle">No products could be found containing that search. View our full range <a href="products.jsp">here</a>.</div>
     <% }
        }
        else if ((productString == null) && (artistString != null)) {
        products = db.getSelectProducts(artistString, 1);
        if (products.size() == 0) { %>
            <div class="ProductTitle">No products could be found made by that artist. View our full range <a href="products.jsp">here</a>.</div>
     <% }
    }
    else {
        products = db.getAllProducts();
    }

    if (products.size() != 0) {%>
    <table>
        <%
            Map<String, Integer> basketContents = basket.getItems();
            int quantity;

            for (Product product : products) {
                if (basketContents.get(product) != null) {
                    quantity = basketContents.get(product);
                }
                else{
                    quantity = 0;
                }

        %>
        <tr>
            <td class="ProductIcon"><a href='<%="viewProduct.jsp?pid="+product.PID%>'><img
                    src="<%= product.thumbnail %>"/></a></td>
            <td class="ProductLink"><a href='<%="viewProduct.jsp?pid="+product.PID%>'><%= product.title %>
            </a></td>
            <td class="ProductArtist"><%= product.artist %>
            </td>
            <td class="ProductPrice"><%= product.getPrice(product.price) %>
            </td>
            <td class="BuyIcon"><a href='<%="basket.jsp?PID="+product.PID+"&quantity="+(quantity+1)%>'><img
                    src="images/BasketIcon.png" alt="Buy"></a></td>
        </tr>
        <% } %>
    </table>
    <% } %>
</div>
</body>
</html>
