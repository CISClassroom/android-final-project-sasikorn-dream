package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var id = getIntent().getExtras()!!.getString("id")
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        delete_Btn.setOnClickListener {
            mDatabase.child("TipsOfHair").child(id!!).removeValue()
            val intent = Intent(this,ShowTips_Admin_Activity::class.java)
            startActivity(intent)
            finish()
        }

    }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            addDataToList(dataSnapshot.child("TipsOfHair"))
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message

        }
    }
    fun addDataToList(dataSnapshot: DataSnapshot) {
        var id = getIntent().getExtras()!!.getString("id")
        val items = dataSnapshot.children.iterator()
        if(items.hasNext()){
            while (items.hasNext()){
                val currentItem = items.next().getValue() as HashMap<String, Any>
                if (currentItem.get("tipsOfHair_id")==id){
                    TopictextView6.text = currentItem.get("nameOfTips") as String
                    detailtextView7.text = currentItem.get("detailOfTips") as String
                    getdate.text = currentItem.get("date_Post") as String
                }
            }
        }

//        }
    }
}
