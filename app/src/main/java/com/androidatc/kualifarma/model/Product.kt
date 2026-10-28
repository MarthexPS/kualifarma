package com.androidatc.kualifarma.model

data class Product(
    val sku: String,
    val name: String,
    val description: String,
    val category: String,
    val publicPrice: Double,
    val costPrice: Double,
    val stockQuantity: Int,
    val status: String
)