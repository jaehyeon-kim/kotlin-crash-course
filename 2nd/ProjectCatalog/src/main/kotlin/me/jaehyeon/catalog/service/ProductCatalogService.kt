package me.jaehyeon.catalog.service

import me.jaehyeon.catalog.model.Product
import me.jaehyeon.catalog.repository.ProductCatalogRepository

class ProductCatalogService {
    private fun getAllProductsAsSequence(): Sequence<Product> = ProductCatalogRepository.getAllProducts().asSequence()

    fun getTopThreeExpensiveProducts(): List<Product> =
        getAllProductsAsSequence()
            .sortedByDescending { it.price }
            .take(3)
            .toList()
}
