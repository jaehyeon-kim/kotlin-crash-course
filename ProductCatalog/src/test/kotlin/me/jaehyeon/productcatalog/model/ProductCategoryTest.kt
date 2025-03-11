package me.jaehyeon.productcatalog.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ProductCategoryTest {
    @Test
    fun `test get category by displace sequence`() {
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
