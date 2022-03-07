package com.example.kyaracter.android.ui.playing

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.playing.DeletePlayingDataUseCase
import com.example.kyaracter.android.R
import com.example.kyaracter.android.databinding.PlayingFragmentBinding
import com.example.domain.playing.LoadPlayingDataUseCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class PlayingFragment : Fragment(), DIAware {
    override val di: DI by di()

    private var _binding: PlayingFragmentBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null

    private val _loadPlayingDtaUseCase: LoadPlayingDataUseCase by instance()
    private val _deletePlayingDataUseCase: DeletePlayingDataUseCase by instance()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlayingFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo DI対応後Repository, DB層の依存も消す
        val viewModel =
            PlayingViewModel( _loadPlayingDtaUseCase, _deletePlayingDataUseCase)

        binding.kyaraCard.setOnClickListener {
            viewModel.onTap()
        }
        binding.kyaraCard.setOnLongClickListener {
            viewModel.onLongTap()
            return@setOnLongClickListener true
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { uiState ->
                binding.kyaraCharacter.text = uiState.kyara?.character ?: "test"
                binding.kyaraCardProgressbar.visibility =
                    if (uiState.loading) View.VISIBLE else View.GONE

                if (uiState.onPlaying) {
                    // todo: メモリリークしていないか調査する
                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                    }
                    mediaPlayer?.apply {
                        withContext(Dispatchers.IO) {
                            setDataSource(requireContext(), uiState.kyara!!.soundFilePath.toUri())
                            prepare()
                            setOnCompletionListener {
                                viewModel.onPlayingStop()
                            }
                            start()
                        }
                    }
                }

                if (uiState.goToCreatePage) {
                    findNavController().navigate(R.id.action_playingFragment_to_contentEditorFragment)
                }

                if (uiState.error != null) {
                    Snackbar
                        .make(binding.root, R.string.error_occurred, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.apply {
            reset()
            release()
            mediaPlayer = null
        }
        _binding = null
    }
}