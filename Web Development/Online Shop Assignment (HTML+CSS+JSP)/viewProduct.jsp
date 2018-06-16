<%@ page import="shop.Product"%>
<%@ page import="java.util.Map" %>

<jsp:useBean id='db' scope='session' class='shop.ShopDB'/>
<jsp:useBean id='basket' scope='session' class='shop.Basket'/>

<html>
<body>
<%@ include file="header.jsp" %>
<div class="content">
<%
    String pid = request.getParameter("pid");
    Product product = db.getProduct(pid);
    Map<String, Integer> basketContents = basket.getItems();
    int quantity;
    if(basketContents.get(product) != null) quantity = basketContents.get(product);
    else quantity = 0;

    if (product == null) out.println( product );

    else {
        %>
        <div align="center">
            <h2 class="ProductTitle"> <%= product.title %>  by <%= product.artist %> </h2>
            <img class="ProductImage" src="<%= product.fullimage %>" />
            <p class="ProductDescription"> <%= product.description %> </p>
            <p class="ProductTitle"><%= product.getPrice(product.price) %></p>
            <p class="BuyIcon"><a href = '<%="basket.jsp?PID="+product.PID+"&quantity="+(quantity+1)%>'><img src="images/BasketIcon.png" alt="Buy"></a></p>
        </div>
        <%
    }
%>
</div>
</body>
</html>
