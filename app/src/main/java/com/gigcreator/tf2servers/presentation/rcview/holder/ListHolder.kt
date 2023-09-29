package com.gigcreator.tf2servers.presentation.rcview.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gigcreator.data.models.Server
import com.gigcreator.tf2servers.databinding.ServerItemBinding

class ListHolder(item: View): RecyclerView.ViewHolder(item){

    private val binding = ServerItemBinding.bind(item)

    fun bind(new: Server) = with(binding){
        textViewName.text = new.serverName
        textViewAddressPort.text = "address: ${new.address}:${new.port}"
        textViewGamemode.text = "game: ${new.game}"
        textViewMap.text = "map: ${new.mapName}"
        textViewPlayers.text = "players: ${new.playerCount}/${new.maxPlayers}"
    }

    fun getBinding(): ServerItemBinding = binding
}