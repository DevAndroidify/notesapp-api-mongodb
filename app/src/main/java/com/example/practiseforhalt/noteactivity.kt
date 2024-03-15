package com.example.practiseforhalt

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practiseforhalt.api.objectforapi
import com.example.practiseforhalt.databinding.ActivityNoteactivityBinding
import com.example.practiseforhalt.models.noterequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class noteactivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding:ActivityNoteactivityBinding
    private  var c:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNoteactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val a=intent.getStringExtra("title")
        val b=intent.getStringExtra("description")
        c=intent.getStringExtra("noteid")
    binding.noteactivitytitle.setText(a)
        sharedPreferences =this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.noteactivitydescription.setText(b)
        binding.noteupdate.setOnClickListener {
        val token=sharedPreferences.getString("token",null)
            val x=binding.noteactivitytitle.text.toString()
            val y=binding.noteactivitydescription.text.toString()

            val noterequest=noterequest(y,x);
            createNoteWithAuthorization(token!!,noterequest,c!!)



        }
        binding.deletebutton.setOnClickListener {
            val token=sharedPreferences.getString("token",null)
            val apiservice=objectforapi.apiServices
            GlobalScope.launch {
                val response=apiservice.deleteNote("Brearer $token",c!!)


                withContext(Dispatchers.Main){


                    Toast.makeText(this@noteactivity, "note deleted successfully ", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }

    private fun createNoteWithAuthorization(token: String, noterequest: noterequest,noteid:String) {
        val apiservice=objectforapi.apiServices
        GlobalScope.launch {
            try {
                val response=apiservice.updateNote("Brearer $token",noteid,noterequest)
                withContext(Dispatchers.Main){
                    Toast.makeText(this@noteactivity, "updated successfully", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){

                    Toast.makeText(this@noteactivity, e.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}