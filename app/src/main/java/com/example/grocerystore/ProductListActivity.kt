package com.example.grocerystore

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListActivity : AppCompatActivity() {

    private lateinit var productImageView: ImageView
    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var addProductButton: Button
    private lateinit var productListRecyclerView: RecyclerView
    private val productList = mutableListOf<Product>()
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        productImageView = findViewById(R.id.productImageView)
        productNameEditText = findViewById(R.id.productNameEditText)
        productPriceEditText = findViewById(R.id.productPriceEditText)
        addProductButton = findViewById(R.id.addProductButton)
        productListRecyclerView = findViewById(R.id.productListRecyclerView)

        productImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        addProductButton.setOnClickListener {
            val productName = productNameEditText.text.toString()
            val productPrice = productPriceEditText.text.toString().toDoubleOrNull()
            if (productName.isNotBlank() && productPrice != null && selectedImageUri != null) {
                val product = Product(productName, productPrice, selectedImageUri!!)
                productList.add(product)
                productListRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        productListRecyclerView.layoutManager = LinearLayoutManager(this)
        productListRecyclerView.adapter = ProductAdapter(productList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            productImageView.setImageURI(selectedImageUri)
        }
    }
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_exit -> {
                    finish() // Завершение активности
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }



        companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
}
