#!/bin/bash

BASE_URL="http://172.17.0.2:8080/jpetstore-catalog/"

function execute() {
	echo "--------------------------------"
	echo "$1"
	echo ""

	curl -v "$BASE_URL/$2"

	echo ""
	echo ""
}


execute "Search products" "search-products?keywords=fish"
execute "List product by category" "products-by-category?categoryId=FISH"
execute "Get item" "item?itemId=EST-1"
execute "List items of product" "items-by-product?productId=FI-SW-01"
execute "Item is in stock" "item-in-stock?itemId=EST-1"
execute "Get category" "category?categoryId=FISH"
execute "List categories" "categories"

# end


