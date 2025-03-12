package me.jaehyeon.productcatalog.repository

import me.jaehyeon.productcatalog.model.Product
import me.jaehyeon.productcatalog.model.ProductCategory

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
        products.clear()

        products.add(Product(1, "Laptop", 1000.0))
        products.add(Product(2, "Phone", 500.0))
        products.add(Product(3, "Headphone", 100.0))
    }

    fun getAllProducts(): List<Product> = products.toList()

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun addProduct(product: Product) = products.add(product)

    fun updateProductPriceByIdRefact(
        id: Int,
        newPrice: Double,
    ) {
        products.firstOrNull { it.id == id }?.run {
            val updatedProduct = copy(price = newPrice)
            val index = products.indexOf(this)
            products[index] = updatedProduct
        }
    }

    fun updateProductPriceById(
        id: Int,
        newPrice: Double,
    ) {
        val index = products.indexOfFirst { it.id == id }
        if (index != -1) {
            products[index] = products[index].copy(price = newPrice)
        }
    }

    fun removeProductById(id: Int) {
        products.removeAll { it.id == id }
    }

    fun allCategories(): Set<ProductCategory> = householdCategories union personalCategories

    fun getProductsInRance(
        minPrice: Double,
        maxPrice: Double,
    ): List<Product> = products.filter { it.price in minPrice..maxPrice }

    fun getProductsSortedByPrice(): List<Product> = products.sortedBy { it.price }
}
