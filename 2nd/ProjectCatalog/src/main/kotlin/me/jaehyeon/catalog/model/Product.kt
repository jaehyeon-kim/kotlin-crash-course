package me.jaehyeon.catalog.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
) : Comparable<Product> {
    override fun compareTo(other: Product): Int = price.compareTo(other.price)
}

class ProductCatalog(
    val products: List<Product>,
) : Iterable<Product> {
    override fun iterator(): Iterator<Product> =
        products
            .sortedWith(compareBy({ it.name }, { it.id }))
            .iterator()
}

// // Usage example:
// val catalog = ProductCatalog(listOf(/* products */))
// for (product in catalog) {
//    println("Product: ${product.name}, Price: ${product.price}")
// }
