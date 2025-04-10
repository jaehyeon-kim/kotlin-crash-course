package me.jaehyeon.catalog.api.dto

import me.jaehyeon.catalog.model.ProductCategory
import java.time.LocalDate

data class ProductDetailsDTO(
    val id: Int,
    val name: String,
    val price: Double,
    val category: ProductCategory,
    val stockCount: Int,
    val lastRestocked: LocalDate?,
)
