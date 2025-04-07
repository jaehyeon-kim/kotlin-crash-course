@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.catalog.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductCategoryTest {
    @Test
    fun `test get category by display sequence`() {
        assertEquals(
            ProductCategory.ELECTRONICS,
            ProductCategory.getCategoryBySequence(2),
        )
        assertEquals(
            ProductCategory.FURNITURE,
            ProductCategory.getCategoryBySequence(4),
        )
        assertEquals(
            ProductCategory.CLOTHING,
            ProductCategory.getCategoryBySequence(1),
        )
        assertNull(ProductCategory.getCategoryBySequence(10))
    }
}
