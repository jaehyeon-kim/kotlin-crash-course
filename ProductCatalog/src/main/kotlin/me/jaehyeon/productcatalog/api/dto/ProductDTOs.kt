package me.jaehyeon.productcatalog.api.dto

import me.jaehyeon.productcatalog.model.ProductCategory
import java.time.LocalDate

data class ProductDetailsDTO(
    val id: Int,
    val name: String,
    val price: Double,
    val category: ProductCategory,
    val stockCount: Int,
    val lastRestocked: LocalDate?,
)

data class ProductUpdateDTO(
    val productId: Int,
    val newPrice: Double?,
    val stockAdjustment: Int?,
)

data class ProductCreateDTO(
    val id: Int,
    val name: String,
    val initialPrice: Double,
    val category: ProductCategory,
    val initialStock: Int,
)
