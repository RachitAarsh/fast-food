package com.example.fastfood

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fastfood.databinding.ActivitySignUpBinding
import com.example.fastfood.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var e_mail: String
    private lateinit var passwrd: String
    private lateinit var userName: String
    private lateinit var dataBase: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        dataBase = Firebase.database.reference
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.signUpPageGoogleButton.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        binding.signUpPageButton.setOnClickListener {
            userName = binding.name.text.toString().trim()
            e_mail = binding.email.text.toString().trim()
            passwrd = binding.password.text.toString().trim()
            if (userName.isBlank() || e_mail.isBlank() || passwrd.isBlank()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(e_mail, passwrd)
            }
        }

        binding.ifLogin.setOnClickListener {
            startActivity(Intent(this, LoginPageActivity::class.java))
        }
    }

    private fun createAccount(e_mail: String, passwrd: String) {
        auth.createUserWithEmailAndPassword(e_mail, passwrd).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account wasn't created", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        e_mail = binding.email.text.toString().trim()
        passwrd = binding.password.text.toString().trim()
        val user = UserModel(userName, e_mail, passwrd)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        dataBase.child("user").child(userId).setValue(user)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credentials = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credentials).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
            }
        }
}