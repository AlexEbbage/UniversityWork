<html>
<head>
    <link rel="stylesheet" type="text/css" href="ass2css.css"/>
    <title>Alex's Art Supplies</title>
</head>

<body>
<div class="Header">
    <div class="Home">
        <a href="index.jsp"><img src="images/HomeIcon.png" alt="Home"></a>
    </div>

    <div class="Products">
        <a href="products.jsp">Products</a>
    </div>

    <form class="Search" name="productSearch" action="products.jsp" method="POST">
        <input class="SearchBar" id="Product" type="text" name="Product" placeholder="Search for product...">
        <input class="Confirm" type="submit" value="Search">
    </form>

    <form class="Search" name="artistSearch" action="products.jsp" method="POST">
        <input class="SearchBar" name="Artist" id="Artist" type="text" placeholder="Search by artist...">
        <input class="Confirm" type="submit" value="Search">
    </form>

    <div class="Basket">
        <a href="basket.jsp"><img src="images/BasketIcon.png" alt="Basket"></a>
    </div>
</div>

</body>

</html>
