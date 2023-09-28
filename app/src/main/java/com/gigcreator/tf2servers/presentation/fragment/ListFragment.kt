package com.gigcreator.tf2servers.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigcreator.data.models.ServerJson
import com.gigcreator.data.repository.JsonRepositoryImpl
import com.gigcreator.data.repository.ServerRepositoryImpl
import com.gigcreator.domain.usecase.JsonUseCase
import com.gigcreator.domain.usecase.ServerUseCase
import com.gigcreator.tf2servers.databinding.FragmentListBinding
import com.gigcreator.tf2servers.presentation.rcview.adapter.ListAdapter
import java.util.concurrent.Executors

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ListAdapter

    private val executor = Executors.newSingleThreadExecutor()
    private val mainExecutor = Handler(Looper.getMainLooper())

    private val serverUseCase = ServerUseCase(repository = ServerRepositoryImpl())
    private val jsonUseCase = JsonUseCase(repository = JsonRepositoryImpl())

    private fun init() {
        adapter = ListAdapter(this@ListFragment)
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter

        try {
            jsonUseCase.read(requireContext()).forEach {
                addView(it.address, it.port)
            }
        }catch (e: Throwable){
            Toast.makeText(requireContext(), "servers not found", Toast.LENGTH_LONG).show()
        }
    }
    private fun addView(address: String, port: Int){
        executor.execute {
            val server = serverUseCase.execute(address, port)
            mainExecutor.post { adapter.add(server) }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.buttonAdd.setOnClickListener {
            jsonUseCase.add(ServerJson("95.156.230.56", 27055), requireContext())
        }
    }
}