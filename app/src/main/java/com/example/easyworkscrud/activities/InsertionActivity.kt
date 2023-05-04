package com.example.easyworkscrud.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.easyworkscrud.R
import com.example.easyworkscrud.models.CustomerModel


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    // define properties for views and database reference
private lateinit var etCusName: EditText
private lateinit var etCusEmail: EditText
private lateinit var etCusReview: EditText
private lateinit var btnSaveReview: Button

private lateinit var dbRef: DatabaseReference

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_insertion)

    // initialize views and database reference
    etCusName = findViewById(R.id.etCusName)
    etCusEmail = findViewById(R.id.etCusEmail)
    etCusReview = findViewById(R.id.etCusReview)
    btnSaveReview = findViewById(R.id.btnSave)

    dbRef = FirebaseDatabase.getInstance().getReference("Customers")

    // set click listener for save button
    btnSaveReview.setOnClickListener {
        saveCustomerData()
    }
}

private fun saveCustomerData() {

    //getting values
    val cusName = etCusName.text.toString()
    val cusEmail = etCusEmail.text.toString()
    val cusReview = etCusReview.text.toString()

    if (cusName.isEmpty()) {
        etCusName.error = "Please enter name"
    }
    if (cusEmail.isEmpty()) {
        etCusEmail.error = "Please enter email"
    }

    if (cusReview.isEmpty()) {
        etCusReview.error = "Please enter review"
    }

    val cusId = dbRef.push().key!! //generate customer id

    val customer = CustomerModel(cusId, cusName, cusEmail, cusReview)

    //connect  firebase database
    dbRef.child(cusId).setValue(customer)
        .addOnCompleteListener {
            //display success msg and clear input fields
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

            etCusName.text.clear()
            etCusEmail.text.clear()
            etCusReview.text.clear()


        }.addOnFailureListener { err ->
            //display error message
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }

}


}