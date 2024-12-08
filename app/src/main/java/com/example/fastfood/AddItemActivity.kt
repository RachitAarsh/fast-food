package com.example.fastfood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fastfood.databinding.ActivityAddItemBinding
import com.example.fastfood.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDiscount: String
    private var foodImageUri: Uri? = null
    private lateinit var foodDescription: String
    private lateinit var foodCategory: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.updateButton.setOnClickListener {
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDiscount = binding.foodDiscount.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
//            foodName = binding.foodName.text.toString().trim()
            foodCategory = binding.category.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDiscount.isBlank() || foodDescription.isBlank() || foodCategory.isBlank())) {
                uploadData()
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }


        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {
        val menuRef = database.getReference("menu")
        val newItemKey = menuRef.push().key
        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDiscount = foodDiscount,
                        foodImage = downloadUrl.toString(),
                        foodDescription = foodDescription,
                        foodCategory = foodCategory
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data upload failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }

                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}