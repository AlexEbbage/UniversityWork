<%@ page import="shop.ShopDB" %>

<jsp:useBean id='basket' scope='session' class='shop.Basket' />
<jsp:useBean id = 'db' scope='page' class='shop.ShopDB' />

<html>
<body>
<%@ include file="header.jsp" %>

<div class="content">
<% String custName = request.getParameter("name");

    if (!custName.equals("") && custName != null) {
        // order the basket of items!
        db.order(basket, custName);
        // then empty the basket
        basket.clearBasket();
    %>
    <h2 class="ProductTitle"> Dear <%= custName %>! Thank you for your order.</h2>
    <h2 class="ProductTitle">Go back to our products page by clicking <a href="products.jsp">here</a>.</h2>
    <%
    }
        else {
        %>
        <h2 class="ProductTitle"> Please go back and supply a name.</h2>
        <h2 class="ProductTitle">Return by clicking <a href="basket.jsp">here</a>.</h2>
        <%
    }

%>
</div>

</html>
