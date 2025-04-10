@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.catalog.service

import me.jaehyeon.catalog.api.dto.ProductCreateDTO
import me.jaehyeon.catalog.api.dto.ProductUpdateDTO
import me.jaehyeon.catalog.model.Product
import me.jaehyeon.catalog.model.ProductCategory
import me.jaehyeon.catalog.repository.InventoryRepository
import me.jaehyeon.catalog.repository.ProductCatalogRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ProductServiceTest {
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
        // update product details of the initial product
        val updateDTO =
            ProductUpdateDTO(
                productId = initialProduct.id,
                newPrice = 1100.0,
                stockAdjustment = 20,
            )
        productService.updateProductDetails(updateDTO)
        assertEquals(
            1100.0,
            ProductCatalogRepository
                .getProductById(
                    initialProduct.id,
                )?.price,
        )
        assertEquals(
            70, // Initial 50 + 20
            InventoryRepository
                .getStockForProduct(
                    initialProduct.id,
                )?.stockCount,
        )
    }

    @Test
    fun `test create product`() {
        val dto =
            ProductCreateDTO(
                id = 1,
                name = "TestProduct",
                initialPrice = 100.0,
                ProductCategory.ELECTRONICS,
                10,
            )
        val resultProduct = productService.createProduct(dto)

        assertEquals(dto.id, resultProduct.id)
        assertEquals(dto.name, resultProduct.name)
        assertEquals(dto.initialPrice, resultProduct.price)
        assertEquals(dto.category, resultProduct.productCategory)
        // Validate: product has been added to the ProductCatalogRepository
        val productFromCatalog = ProductCatalogRepository.getProductById(dto.id)
        assertNotNull(productFromCatalog)
        assertEquals(dto.id, productFromCatalog?.id)
        // Validate: initial stock has been added to the InventoryRepository
        val stockCount = InventoryRepository.getStockForProduct(dto.id)?.stockCount
        assertEquals(dto.initialStock, stockCount)
    }
}
