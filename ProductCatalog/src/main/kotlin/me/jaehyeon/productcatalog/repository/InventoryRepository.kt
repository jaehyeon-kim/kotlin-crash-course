package me.jaehyeon.productcatalog.repository

import me.jaehyeon.productcatalog.model.Inventory
import java.time.LocalDate

object InventoryRepository {
    private val inventoryMap: MutableMap<Int, Inventory> = mutableMapOf()

    init {
        reset()
    }

    internal fun reset() {
        inventoryMap.clear()
        val now = LocalDate.now()
        inventoryMap[1] = Inventory(10, now)
        inventoryMap[2] = Inventory(20, now)
        inventoryMap[3] = Inventory(5, now)
    }

    fun addInitialStock(
        productId: Int,
        stockCount: Int,
    ) {
        inventoryMap[productId] = Inventory(stockCount, LocalDate.now())
    }

    fun getStockForProduct(productId: Int): Inventory? = inventoryMap[productId]

    fun updateStock(
        productId: Int,
        count: Int,
    ) {
        inventoryMap[productId]?.let {
            it.stockCount += count
            if (count > 0) { // added new stock
                it.lastRestocked = LocalDate.now()
            } else { // sold stock
                it.soldCount -= count
            }
        }
    }

    fun isInStock(productId: Int) = (inventoryMap[productId]?.stockCount ?: 0) > 0

    fun productsNeedingRestocking(threshold: Int = 5): Set<Int> =
        inventoryMap
            .filter { (_, inventory) ->
                inventory.stockCount < threshold
            }.keys
}
