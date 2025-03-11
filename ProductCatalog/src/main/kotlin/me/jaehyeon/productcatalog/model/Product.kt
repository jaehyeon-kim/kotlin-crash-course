package me.jaehyeon.productcatalog.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val productCategory: ProductCategory,
) : Comparable<Product> {
    override fun compareTo(other: Product): Int = price.compareTo(other.price)

    constructor(id: Int, name: String, price: Double) : this(id, name, price, ProductCategory.ELECTRONICS)
}

// class ProductCatalog(
//    val products: List<Product>,
// ) : Iterable<Product> {
//    override fun iterator(): Iterator<Product> =
//        products
//            .sortedWith(
//                compareBy(
//                    { it.name },
//                    { it.price },
//                ),
//            ).iterator()
// }
