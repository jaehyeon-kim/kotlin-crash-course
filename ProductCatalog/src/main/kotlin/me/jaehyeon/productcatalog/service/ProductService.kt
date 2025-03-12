package me.jaehyeon.productcatalog.service

import me.jaehyeon.productcatalog.api.dto.ProductCreateDTO
import me.jaehyeon.productcatalog.api.dto.ProductDetailsDTO
import me.jaehyeon.productcatalog.api.dto.ProductUpdateDTO
import me.jaehyeon.productcatalog.model.Product
import me.jaehyeon.productcatalog.repository.InventoryRepository
import me.jaehyeon.productcatalog.repository.ProductCatalogRepository
import org.springframework.stereotype.Service

@Service
class ProductService {
    fun getProductDetailsById(productId: Int) =
        ProductCatalogRepository
            .getProductById(productId)
            ?.toDto()

    fun updateProductDetails(productUpdateDTO: ProductUpdateDTO) {
        // within 'with' block, access productUpdateDTO's properties directly
        with(productUpdateDTO) {
            newPrice?.let {
                ProductCatalogRepository.updateProductPriceById(productId, it)
            }
            stockAdjustment?.let {
                InventoryRepository.updateStock(productId, it)
            }
        }
    }

    fun createProduct(productCreateDTO: ProductCreateDTO): Product =
        with(productCreateDTO) {
            Product(
                id = id,
                name = name,
                price = initialPrice,
                productCategory = category,
            ).also {
                ProductCatalogRepository.addProduct(it)
                InventoryRepository.addInitialStock(it.id, initialStock)
            }
        }

    private fun Product.toDto(): ProductDetailsDTO? {
        // fetch the inventory details for the given product
        val nullableInventory = InventoryRepository.getStockForProduct(id)
        // 'let' is used to perform an action if inventory is not null
        return nullableInventory?.let { inventory ->
            ProductDetailsDTO(
                id = id,
                name = name,
                price = price,
                category = productCategory,
                stockCount = inventory.stockCount,
                lastRestocked = inventory.lastRestocked,
            )
        }
    }
}
