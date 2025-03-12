@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.productcatalog.service

import me.jaehyeon.productcatalog.api.dto.ProductCreateDTO
import me.jaehyeon.productcatalog.api.dto.ProductUpdateDTO
import me.jaehyeon.productcatalog.model.Product
import me.jaehyeon.productcatalog.model.ProductCategory
import me.jaehyeon.productcatalog.repository.InventoryRepository
import me.jaehyeon.productcatalog.repository.ProductCatalogRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ProductServiceTest {
    private lateinit var productService: ProductService
    private lateinit var initialProduct: Product

    @BeforeEach
    fun setUp() {
        ProductCatalogRepository.reset()
        initialProduct =
            Product(
                4,
                "Smartphone",
                1000.0,
                ProductCategory.ELECTRONICS,
            )
        ProductCatalogRepository.addProduct(initialProduct)
        InventoryRepository.addInitialStock(initialProduct.id, 50)
        productService = ProductService()
    }

    @Test
    fun `test get product details by ID`() {
        val productDetailsDTO = productService.getProductDetailsById(4)

        assertNotNull(productDetailsDTO)
        assertEquals(4, productDetailsDTO!!.id)
        assertEquals("Smartphone", productDetailsDTO.name)
        assertEquals(1000.0, productDetailsDTO.price)
        assertEquals(ProductCategory.ELECTRONICS, productDetailsDTO.category)
        assertEquals(50, productDetailsDTO.stockCount)
        assertEquals(LocalDate.now(), productDetailsDTO.lastRestocked)
    }

    @Test
    fun `test get product details by ID for nonexistent product`() {
        val productDetailsDTO = productService.getProductDetailsById(99)
        assertNull(productDetailsDTO)
    }

    @Test
    fun `test update product details`() {
        val updateDTO =
            ProductUpdateDTO(
                productId = initialProduct.id,
                newPrice = 1100.0,
                stockAdjustment = 20,
            )
        productService.updateProductDetails(updateDTO)
        assertEquals(
            1100.0,
            ProductCatalogRepository.getProductById(initialProduct.id)?.price,
        )
        assertEquals(
            70,
            InventoryRepository.getStockForProduct(initialProduct.id)?.stockCount,
        )
    }

    @Test
    fun `test create product`() {
        val productCreateDTO =
            ProductCreateDTO(
                id = 5,
                name = "TestProduct",
                initialPrice = 100.0,
                category = ProductCategory.ELECTRONICS,
                initialStock = 10,
            )
        // from creation
        val resultProduct = productService.createProduct(productCreateDTO)
        assertEquals(productCreateDTO.id, resultProduct.id)
        assertEquals(productCreateDTO.name, resultProduct.name)
        assertEquals(productCreateDTO.initialPrice, resultProduct.price)
        assertEquals(productCreateDTO.category, resultProduct.productCategory)
        // from catalog
        val productFromCatalog = ProductCatalogRepository.getProductById(productCreateDTO.id)
        assertNotNull(productFromCatalog)
        assertEquals(productCreateDTO.id, productFromCatalog?.id)
        val stockCount = InventoryRepository.getStockForProduct(productCreateDTO.id)?.stockCount
        assertEquals(productCreateDTO.initialStock, stockCount)
    }
}
