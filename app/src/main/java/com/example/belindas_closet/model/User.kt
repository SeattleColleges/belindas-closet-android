package com.example.belindas_closet.model

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.belindas_closet.R

// saving template of product variables in case of editing user variables similarly
enum class UserRole {
    ADMIN, STANDARD
}
//
//// Shoes sizes
//enum class UserLastName(val size: Int?) {
//    SELECT_SIZE(null), XS(5), S(6), M(7), L(8), XL(9), XXL(10), XXXL(11), XXXXL(12)
//}
//
//// General product sizes
//enum class UserEmail {
//    SELECT_SIZE, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
//}
//
//// productSizePantsWaist is nullable because not all products have a waist size
//enum class UserRole(val size: Int?) {
//    SELECT_SIZE(null), XS(28), S(30), M(32), L(34), XL(36), XXL(38), XXXL(40), XXXXL(42)
//}


data class User(
    val userFirstName: String,
    val userLastName: String,
    val userEmail: String,
    val userRole: UserRole,
    val userId: String = "0"
)

