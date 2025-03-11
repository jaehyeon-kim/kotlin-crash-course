package me.jaehyeon.productcatalog.service

import me.jaehyeon.productcatalog.model.Product
import me.jaehyeon.productcatalog.model.ProductCategory
import me.jaehyeon.productcatalog.repository.ProductCatalogRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProductCatalogServiceTest {
    private lateinit var productCatalogService: ProductCatalogService

    @BeforeEach
    fun setUp() {
        ProductCatalogRepository.reset()
        productCatalogService = ProductCatalogService()
    }

    @Test
    fun getTopThreeExpensiveProducts() {
        val topProducts = productCatalogService.getTopThreeExpensiveProducts()
        assertEquals(3, topProducts.size)
        assertEquals("Laptop", topProducts[0].name)
        assertEquals(1000.0, topProducts[0].price)
        assertEquals("Phone", topProducts[1].name)
        assertEquals(500.0, topProducts[1].price)
        assertEquals("Headphone", topProducts[2].name)
        assertEquals(100.0, topProducts[2].price)
    }

    @Test
    fun `test get number of items in electronics category`() {
        val numberOfItems =
            productCatalogService.getNumberOfItemsInCategory(
                ProductCategory.ELECTRONICS,
            )
        assertEquals(3, numberOfItems)
    }

    @Test
    fun `test get number of items in an empty category`() {
        val numberOfItems =
            productCatalogService.getNumberOfItemsInCategory(
                ProductCategory.KITCHENWARE,
            )
        assertEquals(0, numberOfItems)
    }

    @Test
    fun `test get average price in electronics category`() {
        val avgPrice =
            productCatalogService.getAveragePriceInCategory(
                ProductCategory.ELECTRONICS,
            )
        assertEquals(listOf(1000.0, 500.0, 100.0).average(), avgPrice)
    }

    @Test
    fun `test get average price in an empty category`() {
        val avgPrice =
            productCatalogService.getAveragePriceInCategory(
                ProductCategory.KITCHENWARE,
            )
        assertTrue(avgPrice.isNaN())
    }

    @Test
    fun `test get all product names in alphabetical order`() {
        val productNames = productCatalogService.getAllProductNames()
        assertEquals(setOf("Headphone", "Laptop", "Phone"), productNames)
    }

    @Test
    fun `test search products by name`() {
        ProductCatalogRepository.addProduct(
            Product(4, "Smartphone", 1000.0),
        )

        val searchResult = productCatalogService.searchProductsByName("phone")
        assertEquals(3, searchResult.size)
        assertTrue(searchResult.any { it.name == "Phone" })
        assertTrue(searchResult.any { it.name == "Headphone" })
        assertTrue(searchResult.any { it.name == "Smartphone" })
        assertFalse(searchResult.any { it.name == "Laptop" })
    }

    @Test
    fun `test get product count by category`() {
        val counts = productCatalogService.getProductCountByCategory()
        assertEquals(1, counts.size)
        assertTrue(counts.containsKey(ProductCategory.ELECTRONICS))
        assertEquals(3, counts[ProductCategory.ELECTRONICS])
    }
}
