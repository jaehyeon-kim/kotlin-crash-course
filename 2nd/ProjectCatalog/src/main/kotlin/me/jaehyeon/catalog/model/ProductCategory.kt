package me.jaehyeon.catalog.model

enum class ProductCategory(
    val displaySequence: Int,
) {
    ELECTRONICS(2),
    FURNITURE(4),
    APPLIANCES(3),
    CLOTHING(1),
    FOOD(6),
    BEDDING(5),
    KITCHENWARE(7),
    TOYS(8),
    ;

    companion object {
        fun getCategoryBySequence(sequenceNumber: Int): ProductCategory? {
            for (category in entries) {
                if (category.displaySequence == sequenceNumber) {
                    return category
                }
            }
            return null
        }
    }
}
