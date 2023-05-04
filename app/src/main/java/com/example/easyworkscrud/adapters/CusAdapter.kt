package com.example.easyworkscrud.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworkscrud.R
import com.example.easyworkscrud.models.CustomerModel


class CusAdapter (private val cusList : ArrayList<CustomerModel>) : RecyclerView.Adapter<CusAdapter.ViewHolder>(){
    @SuppressLint("SuspiciousIndentation")

    private lateinit var  mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener= clickListener
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cus_list_item,parent, false)
        return ViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val curruntCus = cusList[position]
        holder.tvCusName.text = curruntCus.cusName
    }


    override fun getItemCount(): Int {
      return cusList.size
    }

    class ViewHolder(itemView : View, clickListener:onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvCusName : TextView = itemView.findViewById(R.id.tvCusName)

        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }


}