package th.ac.kku.cis.mobileapp.beautycafe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adapter(context: Context, toDoItemList: MutableList<model>) : BaseAdapter() {

    val mInflater = LayoutInflater.from(context) //เรียกใช้ context
    var itemList = toDoItemList //

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view
//        val objectId: String = itemList.get(position).objectId as String
        val testname: String = itemList.get(position).text as String
        val view: View
        val vh: ListRowHolder

        // get list view ดึงหน้า item list view มาแสดงในlistview
        if (convertView == null) {
            view = mInflater.inflate(R.layout.items_listview, parent, false) //เอาจาก item list มาใส่ในlistview
            vh = ListRowHolder(view)
            view.tag = vh //ใส่ชื่อให้หน้า items list
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        // add text to view
        vh.label.text = testname

        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.textView5) as TextView
    }
}