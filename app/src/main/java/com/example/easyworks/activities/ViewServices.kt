package com.example.easyworks.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.adapters.ServiceAdapter
import com.example.easyworks.models.ServiceModel
import com.example.easyworkscrud.R
import com.google.firebase.database.*

class ViewServices: AppCompatActivity() {

    private lateinit var serviceRecyclerView: RecyclerView
    private lateinit var textviewloadingData: TextView
    private lateinit var servicelist : ArrayList<ServiceModel>
    private lateinit var dbref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching2)

        var servicebtn = findViewById<ImageButton>(R.id.servicenotselbtn)
        servicebtn.setOnClickListener({
            val intent = Intent(this, addService::class.java)
            startActivity(intent)
        })


        serviceRecyclerView = findViewById(R.id.recyclerView)
        serviceRecyclerView.layoutManager = LinearLayoutManager(this)
        serviceRecyclerView.setHasFixedSize(true)
        textviewloadingData = findViewById(R.id.loadingdata)

        servicelist = arrayListOf<ServiceModel>()

        getServiceData()

    }

    private fun getServiceData(){
        serviceRecyclerView.visibility = View.GONE
        textviewloadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Services")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                servicelist.clear()
                if(snapshot.exists()){
                    for(serviceSnap in snapshot.children){
                        val servicedata = serviceSnap.getValue(ServiceModel::class.java)
                        servicelist.add(servicedata!!)
                    }
                    val sadapter = ServiceAdapter(servicelist)
                    serviceRecyclerView.adapter = sadapter

                    sadapter.setOnItemClickListener(object : ServiceAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ViewServices,ServiceDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("serviceid",servicelist[position].serviceid)
                            intent.putExtra("title",servicelist[position].title)
                            // intent.putExtra("category",servicelist[position].category)
                            intent.putExtra("price",servicelist[position].price)
                            intent.putExtra("name",servicelist[position].name)
                            intent.putExtra("phone",servicelist[position].phone)
                            intent.putExtra("email",servicelist[position].email)
                            startActivity(intent)
                        }

                    })

                    serviceRecyclerView.visibility = View.VISIBLE
                    textviewloadingData.visibility = View.GONE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }



}




