package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    var mAuth : FirebaseAuth? = null
    var mAuthListener : FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser

        showemail.text = user!!.email

        mAuthListener = FirebaseAuth.AuthStateListener {  firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null){
                startActivity(Intent(this@ResultActivity,LoginActivity::class.java))
                finish()
            }
        }
        Logoutbutton.setOnClickListener {
            mAuth!!.signOut()
            Toast.makeText(this,"Signed Out",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@ResultActivity,MainActivity::class.java))
            finish()
        }

        button2.setOnClickListener {
            startActivity(Intent(this,testActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null){
            mAuth!!.removeAuthStateListener { mAuthListener }

        }
    }

}
