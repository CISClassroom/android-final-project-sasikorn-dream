package th.ac.kku.cis.mobileapp.beautycafe

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_admin.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class AdminActivity : AppCompatActivity() {

    lateinit var nameOfTips : EditText
    lateinit var DetailOfTips : EditText
    lateinit var AddTipsbutton : Button
    lateinit var ChooseImage : Button
    lateinit var imageview : ImageView
    lateinit var imguri: Uri
    private val PERMISSION_CODE = 1000;
//    private var baseR = FirebaseDatabase.getInstance().reference
//    private var dataTipsOfHair = baseR.child("TipsOfHair")

    lateinit var storageRef : StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

            storageRef = FirebaseStorage.getInstance().getReference("Images")

            nameOfTips = findViewById(R.id.nameOfTips_editText) as EditText
            DetailOfTips = findViewById(R.id.DetailOfTips_editText2) as EditText
            AddTipsbutton = findViewById(R.id.AddTipsbutton) as Button
            ChooseImage = findViewById(R.id.ChooseImage_Btn) as Button
            imageview = findViewById(R.id.imageView2) as ImageView

        ChooseImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    FileChoose()
                }
            }
            else{
                //system os is < marshmallow
                FileChoose()
            }

        }

        AddTipsbutton.setOnClickListener {

            ///////////////////////////////////////////////////////////////////////////////////
            val nameOfTips = nameOfTips.text.toString().trim()
            if (nameOfTips.isEmpty()) {
                nameOfTips_editText.error = "กรุณาใส่ชื่อหัวข้อ"
                return@setOnClickListener
            }
            val DetailOfTips = DetailOfTips.text.toString().trim()
            if (DetailOfTips.isEmpty()){
                DetailOfTips_editText2.error = "กรุณากรอกรายละเอียดเคล็ดลับการดูแลผม"
                return@setOnClickListener
            }

            val calendar: Calendar = Calendar.getInstance()
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate = format.format(calendar.time)
            val date_Post = formattedDate.toString()
            if (nameOfTips.isNotEmpty() && DetailOfTips.isNotEmpty()){
                val ref = FirebaseDatabase.getInstance().getReference("TipsOfHair")
                val TipsOfHair_id = ref.push().key
                val Tips = TipsOfHair_id?.let { TipsOfHair(it,nameOfTips, DetailOfTips,date_Post) }
                if (TipsOfHair_id != null){
                    ref.child(TipsOfHair_id).setValue(Tips).addOnCompleteListener {
                        Toast.makeText(this,"Add Data Successfull",Toast.LENGTH_LONG).show()
                    }

                }
                FileUploader()
                val intent = Intent(this@AdminActivity,ShowTips_Admin_Activity::class.java)
                intent.putExtra("nameTips",""+nameOfTips)
                intent.putExtra("DetailTips",""+DetailOfTips)
                intent.putExtra("date_Post",""+date_Post)
                if (nameOfTips.isNotEmpty() && DetailOfTips.isNotEmpty() && date_Post.isNotEmpty()){
                    startActivity(intent)
                    finish()
                }
            }

        }
    }
    private fun getExtension(uri: Uri): String? {
            var cr = contentResolver
            var mimetypemap = MimeTypeMap.getSingleton()
        return mimetypemap.getExtensionFromMimeType(cr.getType(uri))
    }

    private fun FileUploader(){

        var ref = storageRef.child(""+System.currentTimeMillis()+"."+getExtension(imguri))
        ref.putFile(imguri)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot -> // Get a URL to the uploaded content
               // val downloadUrl: Uri = taskSnapshot.uploadSessionUri!!
                Toast.makeText(this,"Image success",Toast.LENGTH_LONG).show()
            })
            .addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                // ...
            })
    }

    private fun FileChoose(){

        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){

                imguri = data.getData()!!
                imageview.setImageURI(imguri)
        }


    }
//    private fun saveTips(){
//
//
//    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    FileChoose()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
