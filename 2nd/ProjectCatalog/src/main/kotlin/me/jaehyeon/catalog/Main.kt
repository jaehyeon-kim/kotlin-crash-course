package me.jaehyeon.catalog

import me.jaehyeon.catalog.api.dto.ProductCreateDTO
import me.jaehyeon.catalog.api.dto.ProductUpdateDTO
import me.jaehyeon.catalog.model.ProductCategory
import me.jaehyeon.catalog.service.ProductService

fun main() {
    val productService = ProductService()
    println(productService.getProductDetailsById(1))
    println(productService.getProductDetailsById(99))

    val productUpdateDTO =
        ProductUpdateDTO(
            productId = 1,
            newPrice = 1200.0,
            stockAdjustment = 5,
        )
    productService.updateProductDetails(productUpdateDTO)
    println(productService.getProductDetailsById(1))

    val dto =
        ProductCreateDTO(
            id = 4,
            name = "TestProduct",
            initialPrice = 100.0,
            ProductCategory.ELECTRONICS,
            10,
        )
    val newProduct = productService.createProduct(dto)
    println(newProduct)
}
