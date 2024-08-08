package com.example.grocerystore

import android.net.Uri

data class Product(
    val name: String,
    val price: Double,
    val imageUri: Uri
)
