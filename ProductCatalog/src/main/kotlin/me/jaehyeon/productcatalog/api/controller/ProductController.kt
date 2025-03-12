package me.jaehyeon.productcatalog.api.controller

import me.jaehyeon.productcatalog.api.dto.ProductCreateDTO
import me.jaehyeon.productcatalog.api.dto.ProductUpdateDTO
import me.jaehyeon.productcatalog.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping("/{productId}")
    fun getProductDetailsById(
        @PathVariable productId: Int,
    ): ResponseEntity<Any> {
        val productDetailsDTO = productService.getProductDetailsById((productId))
        return if (productDetailsDTO != null) {
            ResponseEntity.ok(productDetailsDTO)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/")
    fun updateProductDetails(
        @RequestBody productUpdateDTO: ProductUpdateDTO,
    ): ResponseEntity<String> =
        try {
            productService.updateProductDetails(productUpdateDTO)
            ResponseEntity.ok("Product details updated successfully.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error updating product details: ${e.message}")
        }

    @PostMapping("/")
    fun createProduct(
        @RequestBody productCreateDTO: ProductCreateDTO,
    ): ResponseEntity<Any> {
        productService.getProductDetailsById(productCreateDTO.id)?.let {
            // If it exists, return a conflict status.
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
        // If not, create the product and return a created status.
        productService.createProduct(productCreateDTO).let {
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
    }
}
