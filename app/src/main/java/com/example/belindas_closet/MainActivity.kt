package com.example.belindas_closet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.belindas_closet.screen.ScreenMain
import com.example.belindas_closet.ui.theme.Belindas_closetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = getSharedPreferences("belindasHidden", Context.MODE_PRIVATE)
        productType = String()
        super.onCreate(savedInstanceState)
        setContent {
            Belindas_closetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
    }

    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var productType: String
        fun getPref(): SharedPreferences{
            return pref
        }

        fun getProductType(): String{
            return productType
        }

        fun setProductType(newProductType: String): Unit{
            productType = newProductType
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Belindas_closetTheme {
        ScreenMain()
    }
}