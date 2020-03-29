package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference;
import kotlinx.android.synthetic.main.activity_show_tips__admin_.*
import kotlinx.android.synthetic.main.activity_test.*


class ShowTips_Admin_Activity : AppCompatActivity() {
    var Topiclist: MutableList<model>? = null
    lateinit var adapter: adapter
    private var listViewItems: ListView? = null
    lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tips__admin_)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        listViewItems = findViewById<View>(R.id.listview_admin) as ListView

        Topiclist = mutableListOf<model>() //เป็นการประกาศarray
        adapter = adapter(this, Topiclist!!) //เป็นการเอาค่าtestlist มาใส่ในadapter
        listViewItems!!.setAdapter(adapter)

        listview_admin.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as model
            val intent = Intent(this@ShowTips_Admin_Activity,DetailActivity::class.java)
            intent.putExtra("id", selectedItem.TipsOfHair_id)
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
        val items = dataSnapshot.children.iterator()
        while (items.hasNext()) {
            var model = model.create()
            val TopicItems = items.next().getValue() as HashMap<String, Any>
            model.TipsOfHair_id = TopicItems.get("tipsOfHair_id")as String
            model.Topicname = TopicItems.get("nameOfTips")as String
            Topiclist!!.add(model)
            adapter.notifyDataSetChanged()

        }
//        }
    }




}
