@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.catalog.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductCatalogServiceTest {
    private lateinit var productCatalogService: ProductCatalogService

    @BeforeEach
    fun setUp() {
        productCatalogService = ProductCatalogService()
    }

    @Test
    fun `get top three expensive products`() {
        val topProducts = productCatalogService.getTopThreeExpensiveProducts()
        assertEquals(3, topProducts.size)
        assertEquals("Laptop", topProducts[0].name)
        assertEquals(1000.0, topProducts[0].price)
        assertEquals("Phone", topProducts[1].name)
        assertEquals(500.0, topProducts[1].price)
        assertEquals("Headphones", topProducts[2].name)
        assertEquals(100.0, topProducts[2].price)
    }
}
