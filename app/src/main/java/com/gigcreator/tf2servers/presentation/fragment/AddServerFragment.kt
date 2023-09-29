package com.gigcreator.tf2servers.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gigcreator.data.models.ServerJson
import com.gigcreator.data.repository.JsonRepositoryImpl
import com.gigcreator.data.repository.ServerRepositoryImpl
import com.gigcreator.domain.usecase.JsonUseCase
import com.gigcreator.domain.usecase.ServerUseCase
import com.gigcreator.tf2servers.databinding.FragmentAddServerBinding
import java.util.concurrent.Executors

class AddServerFragment : Fragment() {

    private lateinit var binding: FragmentAddServerBinding

    private val serverUseCase = ServerUseCase(repository = ServerRepositoryImpl())
    private lateinit var jsonUseCase: JsonUseCase

    private val executor = Executors.newSingleThreadExecutor()
    private val mainExecutor = Handler(Looper.getMainLooper())

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddServerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jsonUseCase = JsonUseCase(repository = JsonRepositoryImpl(requireContext()))

        binding.buttonAddServer.setOnClickListener {
            if (binding.editTextPort.text.isNotEmpty() && binding.editTextAddress.text.isNotEmpty()) {
                executor.execute {
                    val server = serverUseCase.execute(
                        binding.editTextAddress.text.toString(),
                        binding.editTextPort.text.toString().toInt()
                    )
                    mainExecutor.post {
                        if (server.address != "0.0.0.0") {
                            binding.textViewConnectionResult.setTextColor(Color.GREEN)
                            binding.textViewConnectionResult.text = "True"
                            binding.textViewProtocolResult.text = server.protocol.toString()
                            binding.textViewFolderResult.text = server.folder
                            Toast.makeText(
                                requireContext(),
                                "connection success!",
                                Toast.LENGTH_LONG
                            ).show()

                            binding.server.textViewName.text = server.serverName
                            binding.server.textViewAddressPort.text =
                                "address: ${server.address}:${server.port}"
                            binding.server.textViewGamemode.text = "game: ${server.game}"
                            binding.server.textViewMap.text = "map: ${server.mapName}"
                            binding.server.textViewPlayers.text =
                                "players: ${server.playerCount}/${server.maxPlayers}"

                            jsonUseCase.add(
                                ServerJson(server.address, server.port)
                            )
                        } else {
                            binding.textViewConnectionResult.setTextColor(Color.RED)
                            binding.textViewConnectionResult.text = "ERROR"
                            binding.textViewProtocolResult.text = "False"
                            binding.textViewFolderResult.text = "False"
                            Toast.makeText(requireContext(), "connection error!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }else{
                Toast.makeText(
                    requireContext(),
                    "fields are empty!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}