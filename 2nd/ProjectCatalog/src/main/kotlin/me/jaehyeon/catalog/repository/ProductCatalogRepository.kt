package me.jaehyeon.catalog.repository

import me.jaehyeon.catalog.model.Product
import me.jaehyeon.catalog.model.ProductCategory

object ProductCatalogRepository {
    private val products: MutableList<Product> = mutableListOf()
    private val householdCategories: Set<ProductCategory> =
        setOf(
            ProductCategory.FURNITURE,
            ProductCategory.BEDDING,
            ProductCategory.KITCHENWARE,
        )
    private val personalCategories: Set<ProductCategory> =
        setOf(
            ProductCategory.CLOTHING,
            ProductCategory.TOYS,
            ProductCategory.BEDDING,
        )

    init {
        reset()
    }

    internal fun reset() {
        // clear all products
        products.clear()
        // initialize the repository with some sample products
        products.add(Product(1, "Laptop", 1000.0))
        products.add(Product(2, "Phone", 500.0))
        products.add(Product(3, "Headphones", 100.0))
    }

    fun getAllProducts(): List<Product> = products.toList()

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun updateProductPriceById(
        id: Int,
        newPrice: Double,
    ) {
        val index = products.indexOfFirst { it.id == id }
        if (index != -1) {
            val updatedProduct = products[index].copy(price = newPrice)
            products[index] = updatedProduct
        }
    }

    fun remoteProductById(id: Int) {
        products.removeAll { it.id == id }
    }

    fun allCategories(): Set<ProductCategory> = householdCategories union personalCategories

    fun getProductsInRange(
        minPrice: Double,
        maxPrice: Double,
    ): List<Product> = products.filter { it.price in minPrice..maxPrice }

    // with comparable, products.sorted() should work?
    fun getProductSortedByPrice(): List<Product> = products.sortedBy { it.price }

    fun getProductSortedByPriceDescending(): List<Product> = products.sortedByDescending { it.price }
}
