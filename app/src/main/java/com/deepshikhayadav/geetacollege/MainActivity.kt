package com.deepshikhayadav.geetacollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.deepshikhayadav.geetacollege.databinding.ActivityHomeBinding
import com.deepshikhayadav.geetacollege.databinding.ActivityMainBinding
import com.firebase.ui.auth.util.signincontainer.IdpSignInContainer.signIn
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private val RC_SIGN_IN=123
    private val TAG: String = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("804335635453-8bkv4oapoaed8edqqllqm04mcmrt9jqe.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        binding.googleLogin.setOnClickListener {
            signIn()
        }

        getTokenFcm()
    }


    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                Toast.makeText(this, "firebaseAuthWithGoogle successful  ${account.displayName}", Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

                // ...
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(applicationContext,HomeActivity::class.java))
                    finish()
                   /* documentRef=db.collection("users")
                        .document(user!!.uid)
                    val hashMap = hashMapOf(
                        "Email" to auth.currentUser!!.email,
                    )
                    documentRef.get().addOnSuccessListener {
                        if(it.getString("admin")!=null){
                            Toast.makeText(applicationContext,"You are admin",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext,AdminLogin::class.java))
                            finish()
                        }
                        else{
                            documentRef.set(hashMap)
                            startActivity(Intent(applicationContext,Dashboard::class.java))
                            finish()
                        }
                    }*/


                } else {
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext,"OOPs something went wrong!!",Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(applicationContext,HomeActivity::class.java))
            finish()

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getTokenFcm() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val newToken = task.result

                Log.d("fcmtoken", newToken)
            })
    }
}