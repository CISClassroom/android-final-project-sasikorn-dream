package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAuth : FirebaseAuth? = null
    private val TAG : String = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null){
            startActivity(Intent(this@MainActivity,ResultActivity::class.java))
            finish()
        }
        main_email_Btn.setOnClickListener {
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }
    }
}
