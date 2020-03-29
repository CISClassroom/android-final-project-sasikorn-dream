package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_test.*

class testActivity : AppCompatActivity() {

    var testlist: MutableList<model>? = null
    lateinit var adapter: adapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        listViewItems = findViewById<View>(R.id.listview) as ListView

        testlist = mutableListOf<model>() //เป็นการประกาศarray
        adapter = adapter(this, testlist!!) //เป็นการเอาค่าtestlist มาใส่ในadapter
        listViewItems!!.setAdapter(adapter)

        var i = 0
        while (i != 5){

//            testlist!!.add(model("สวัสดี"+ i.toString()))
            i += 1
        }

        listview.setOnItemClickListener{parent, view, position, id ->
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}
