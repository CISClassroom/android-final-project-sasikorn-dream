package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var mAuth : FirebaseAuth? = null
    private val TAG : String = "Login Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null){
            startActivity(Intent(this@LoginActivity,ResultActivity::class.java))
            finish()
        }
        RegisterBtn.setOnClickListener {
            val email = emailRegister.text.toString().trim{it <= ' '}
            val password = passwordRegister.text.toString().trim{it <= ' '}

            if (email.isEmpty())
            {
                Toast.makeText(this,"Please enter your Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this,"Please enter your password ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if (password.length < 6){
                        Toast.makeText(this,"รหัสผ่านต้องไม่น้อยกว่า 6 ตัวอักษร",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Authentication Failed "+ task.exception,Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Sign in Successfull",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity,ResultActivity::class.java))
                    finish()
                }

            }
        }
        signin.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
    }
}
