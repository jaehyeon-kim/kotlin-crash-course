@file:Suppress("ktlint:standard:filename")

package me.jaehyeon.catalog.api.dto

import me.jaehyeon.catalog.model.ProductCategory

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
