package com.example.kyaracter.android.ui.editor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.playing.SavePlayingDataUseCase
import com.example.kyaracter.android.R
import com.example.kyaracter.android.databinding.PickingSoundFileFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class PickingSoundFileFragment : Fragment(), DIAware {
    override val di: DI by di()
    private val _savePlayingDataUseCase: SavePlayingDataUseCase by instance()

    private var _binding: PickingSoundFileFragmentBinding? = null
    private val binding get() = _binding!!

    // todo DIする
    private lateinit var viewModel: PickingSoundFileViewModel
    private val args: PickingSoundFileFragmentArgs by navArgs()

    private val soundPickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it == null || it == Uri.EMPTY) {
                viewModel.onFailedPicking()
                return@registerForActivityResult
            }
            requireContext().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onSelectedSoundFile(args.character, it.toString())
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PickingSoundFileFragmentBinding.inflate(inflater)

        viewModel = PickingSoundFileViewModel(_savePlayingDataUseCase)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputCharacterDisabled.setText(args.character)
        binding.selectFile.setOnClickListener {
            soundPickerLauncher.launch(arrayOf("audio/*"))
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { uiState ->
                if (!uiState.errorMessage.isNullOrEmpty()) {
                    Snackbar
                        .make(binding.root, uiState.errorMessage!!, Snackbar.LENGTH_SHORT)
                        .show()
                }

                if (uiState.goToPlaying) {
                    findNavController().navigate(R.id.action_pickingSoundFileFragment_to_playingFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}