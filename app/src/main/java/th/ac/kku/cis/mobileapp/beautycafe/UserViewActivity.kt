package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show_tips__admin_.*
import kotlinx.android.synthetic.main.activity_user_view.*

class UserViewActivity : AppCompatActivity() {
    var Topiclist: MutableList<model>? = null
    lateinit var adapter: adapter
    private var listViewItems: ListView? = null
    lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        listViewItems = findViewById<View>(R.id.listview_user) as ListView
        Topiclist = mutableListOf<model>() //เป็นการประกาศarray
        adapter = adapter(this, Topiclist!!) //เป็นการเอาค่าtestlist มาใส่ในadapter
        listViewItems!!.setAdapter(adapter)

        listview_user.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as model
            val intent = Intent(this@UserViewActivity,DetailForUserActivity::class.java)
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
    }
}
