package com.gigcreator.tf2servers.presentation.rcview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.data.models.Server
import com.gigcreator.tf2servers.R
import com.gigcreator.tf2servers.presentation.fragment.ListFragment
import com.gigcreator.tf2servers.presentation.rcview.holder.ListHolder

class ListAdapter(private val fragment: ListFragment): RecyclerView.Adapter<ListHolder>() {
    
    private val listServer = ArrayList<Server>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.server_item, parent, false)
        return ListHolder(view)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(listServer[position])
    }

    override fun getItemCount(): Int {
        return listServer.size
    }
    fun add(server: Server){
        listServer.add(server)
        notifyDataSetChanged()
    }

    fun clear() {
        listServer.clear()
        notifyDataSetChanged()
    }
}