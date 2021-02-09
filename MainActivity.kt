package com.example.databaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.core.text.set
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val email = editText.text.toString().toLowerCase()
            val num = editText2.text.toString()
            var temp:String = ""
            var i :Int = 0;
            progressBar.visibility = View.VISIBLE
            editText.visibility = GONE
            editText2.visibility = GONE
            editText3.visibility = GONE
            button.visibility = GONE
            button1.visibility = GONE
            while(email[i] != '@')
            {
                temp+=email[i]
                i+=1
            }
            FirebaseDatabase.getInstance().reference.child("DataBase").child(temp).removeValue()

            FirebaseDatabase.getInstance().reference.child("DataBase").child(temp)
                    .push()
                    .setValue(
                        SendData(email, num)
                    )
            progressBar.visibility = GONE
            editText.visibility = View.VISIBLE
            editText2.visibility = View.VISIBLE
            editText3.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
            button1.visibility = View.VISIBLE
                Toast.makeText(this, "Value Pushed", Toast.LENGTH_SHORT).show()


        }

        button1.setOnClickListener {
            val email = editText3.text.toString().toLowerCase()
            var temp1:String = ""
            var i :Int = 0;
            progressBar.visibility = View.VISIBLE
            editText.visibility = GONE
            editText2.visibility = GONE
            editText3.visibility = GONE
            button.visibility = GONE
            button1.visibility = GONE
            while(email[i] != '@')
            {
                temp1+=email[i]
                i+=1
            }
            FirebaseDatabase.getInstance().reference.child("DataBase").child(temp1).addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    progressBar.visibility = GONE
                    editText.visibility = View.VISIBLE
                    editText2.visibility = View.VISIBLE
                    editText3.visibility = View.VISIBLE
                    button.visibility = View.VISIBLE
                    button1.visibility = View.VISIBLE

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var data = p0.children
                    println()
                    data.forEach {
                        val res = it.getValue(SendData::class.java)!!.num.toString()
                        val res1  = it.getValue(SendData::class.java)!!.email.toString()
                        editText.setText(res1)
                        editText2.setText(res)
                        progressBar.visibility = GONE
                        editText.visibility = View.VISIBLE
                        editText2.visibility = View.VISIBLE
                        editText3.visibility = View.VISIBLE
                        button.visibility = View.VISIBLE
                        button1.visibility = View.VISIBLE
                    }
                }

            })
        }

    }
}

class SendData(var email:String , var num : String)
{
    constructor():this("" , "")

}