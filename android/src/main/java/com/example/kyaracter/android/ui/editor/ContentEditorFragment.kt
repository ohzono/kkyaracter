package com.example.kyaracter.android.ui.editor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kyaracter.android.R
import com.example.kyaracter.android.databinding.ContentEditorFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class ContentEditorFragment : Fragment() {

    private var _binding: ContentEditorFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContentEditorFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ContentEditorViewModel()

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { uiState ->
                if (!uiState.errorMessage.isNullOrEmpty()) {
                    Snackbar
                        .make(binding.root, uiState.errorMessage!!, Snackbar.LENGTH_SHORT)
                        .show()
                }

                if (uiState.goToPickingSoundFile) {
                    val action =
                        ContentEditorFragmentDirections.actionContentEditorFragmentToPickingSoundFileFragment(
                            uiState.inputCharacter!!
                        )
                    findNavController().navigate(action)
                }

            }
        }
        binding.inputCharacter.setOnEditorActionListener { textView, actionId, _ -> 
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onEnterCharacter(textView.text?.toString()?:"")
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}