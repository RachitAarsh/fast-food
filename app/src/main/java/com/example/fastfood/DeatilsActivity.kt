package com.example.fastfood

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.fastfood.databinding.ActivityDeatilsBinding

class DeatilsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeatilsBinding
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodDiscount: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodCategory: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeatilsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodDiscount = intent.getStringExtra("MenuItemDiscount")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodCategory = intent.getStringExtra("MenuItemCategory")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding) {
            detailFoodName.text = foodName
            priceDetails.text = foodPrice
            discountDetails.text = foodDiscount
            descriptionTextView.text = foodDescription
            categoryDetails.text = foodCategory
            Glide.with(this@DeatilsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
    }
}