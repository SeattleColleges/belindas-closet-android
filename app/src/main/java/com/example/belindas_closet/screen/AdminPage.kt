package com.example.belindas_closet.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add_product.setOnClickListener {
            val productName = et_product_name.text.toString()
            val productPrice = et_product_price.text.toString().toDouble()

            if (productName.isNotEmpty() && productPrice > 0) {
                // Add product code here
                Toast.makeText(this, "Product added: $productName ($productPrice)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a valid product name and price", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

