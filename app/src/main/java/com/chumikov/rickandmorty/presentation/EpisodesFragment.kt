package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.FragmentEpisodeListBinding
import com.chumikov.rickandmorty.presentation.adapters.EpisodeListAdapter
import com.chumikov.rickandmorty.ui_theme.MyBlue
import com.chumikov.rickandmorty.ui_theme.MyDarkBlue
import com.chumikov.rickandmorty.ui_theme.MyLightBlue
import kotlinx.coroutines.launch
import javax.inject.Inject


class EpisodesFragment : Fragment() {

    private val args by navArgs<EpisodesFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: EpisodesViewModel.Factory

    private val viewModel by getViewModel<EpisodesViewModel> {
        viewModelFactory.get(args.episodesId.toList())
    }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

//    private var _binding: FragmentEpisodeListBinding? = null
//    private val binding: FragmentEpisodeListBinding
//        get() = _binding ?: throw RuntimeException("FragmentEpisodeListBinding == null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
//        return binding.root
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.status.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MyBlue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (val curState = state.value) {
                        is EpisodesLoadingState.Success -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = {
                                        findNavController().popBackStack()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Arrow Back"
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "List Of The Episodes",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.width(48.dp))
                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(curState.data) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MyLightBlue)

                                    ) {
                                        TextTemplate(
                                            String.format(
                                                getString(R.string.name_template),
                                                it.name
                                            )
                                        )
                                        TextTemplate(
                                            String.format(
                                                getString(R.string.air_date_template),
                                                it.airDate
                                            )
                                        )
                                    }
                                }
                            }

                        }

                        is EpisodesLoadingState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(72.dp),
                                color = MyLightBlue,
                                strokeWidth = 6.dp
                            )
                        }

                        is EpisodesLoadingState.Error -> {
                            Button(
                                modifier = Modifier.padding(10.dp),
                                onClick = {
                                    viewModel.retry()
                                },
                                colors = ButtonDefaults.buttonColors(MyDarkBlue)
                            ) {
                                Text(
                                    text = getString(R.string.retry_text),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 34.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    val retryButton = binding.retryButton
//    retryButton.setOnClickListener { viewModel.retry() }
//
//    val toolbar = binding.toolbar
//    toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
//
//    val adapter = EpisodeListAdapter(requireContext())
//    binding.rvEpisodes.adapter = adapter
//
//    lifecycleScope.launch {
//        viewModel.status.collect { state ->
//            toolbar.isInvisible = state !is EpisodesLoadingState.Success
//            retryButton.isInvisible = state !is EpisodesLoadingState.Error
//            binding.rvEpisodes.isInvisible = state !is EpisodesLoadingState.Success
//            binding.loader.isInvisible = state !is EpisodesLoadingState.Loading
//
//            if (state is EpisodesLoadingState.Success) adapter.submitList(state.data)
//        }
//    }
//}

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

@Composable
//@Preview
private fun TextTemplate(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        text = text,
        fontFamily = FontFamily.Serif,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}

@Preview
@Composable
private fun Test() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MyLightBlue)

        ) {
            Text(text = "Какой-то теккст текс ттекст")
            Text(text = "Какой-то теккст текс ттекст")
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(MyLightBlue)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(text = "Какой-то теккст текс ттекст")
            Text(text = "Какой-то теккст текс ттекст")
        }

    }

}