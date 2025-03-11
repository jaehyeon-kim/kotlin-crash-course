package me.jaehyeon.productcatalog.service

import me.jaehyeon.productcatalog.model.Product
import me.jaehyeon.productcatalog.model.ProductCategory
import me.jaehyeon.productcatalog.repository.ProductCatalogRepository

class ProductCatalogService {
    private fun getAllProductsAsSequence(): Sequence<Product> =
        ProductCatalogRepository
            .getAllProducts()
            .asSequence()

    fun getTopThreeExpensiveProducts(): List<Product> =
        getAllProductsAsSequence()
            .sortedByDescending { it.price }
            .take(3)
            .toList()

    fun getNumberOfItemsInCategory(category: ProductCategory) =
        getAllProductsAsSequence().count {
            it.productCategory == category
        }

    fun getAveragePriceInCategory(category: ProductCategory) =
        getAllProductsAsSequence()
            .filter { it.productCategory == category }
            .map { it.price }
            .average()

    fun getAllProductNames(): Set<String> =
        getAllProductsAsSequence()
            .map { it.name }
            .sorted()
            .toSet()

    fun searchProductsByName(keyword: String): List<Product> =
        getAllProductsAsSequence()
            .filter { it.name.contains(keyword, ignoreCase = true) }
            .toList()

    fun getProductCountByCategory(): Map<ProductCategory, Int> =
        getAllProductsAsSequence()
            .groupingBy { it.productCategory }
            .eachCount()
}
