package me.jaehyeon.productcatalog.repository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InventoryRepositoryTest {
    @BeforeEach
    fun setUp() {
        //
    }

    @Test
    fun `test add and get initial stock of product`() {
        InventoryRepository.addInitialStock(1, 100)
        val inventory = InventoryRepository.getStockForProduct(1)
        assertNotNull(inventory)
        assertEquals(100, inventory?.stockCount)
        assertEquals(LocalDate.now(), inventory?.lastRestocked)
    }

    @Test
    fun `test updateStock increases stock`() {
        InventoryRepository.addInitialStock(1, 100)
        InventoryRepository.updateStock(1, 50)
        val inventory = InventoryRepository.getStockForProduct(1)
        assertEquals(150, inventory?.stockCount)
        assertEquals(0, inventory?.soldCount)
        assertEquals(LocalDate.now(), inventory?.lastRestocked)
    }

    @Test
    fun `test updateStock reduces stock and increases soldCount`() {
        InventoryRepository.addInitialStock(1, 100)
        InventoryRepository.updateStock(1, -20)
        val inventory = InventoryRepository.getStockForProduct(1)
        assertEquals(80, inventory?.stockCount)
        assertEquals(20, inventory?.soldCount)
    }

    @Test
    fun `test isInStock`() {
        InventoryRepository.addInitialStock(1, 100)
        assertTrue(InventoryRepository.isInStock(1))
        InventoryRepository.updateStock(1, -100)
        assertFalse(InventoryRepository.isInStock(1))
    }

    @Test
    fun `test productNeedingRestocking`() {
        InventoryRepository.addInitialStock(1, 100)
        InventoryRepository.addInitialStock(2, 4)
        InventoryRepository.addInitialStock(3, 0)
        val insufficientProductIds = InventoryRepository.productsNeedingRestocking()
        assertTrue(insufficientProductIds.contains(2))
        assertTrue(insufficientProductIds.contains(3))
        assertFalse(insufficientProductIds.contains(1))
    }
}
