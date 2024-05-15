package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import coil.compose.AsyncImage
import coil.load
import coil.request.ImageRequest
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.chumikov.rickandmorty.ui_theme.MyBlue
import com.chumikov.rickandmorty.ui_theme.MyLightBlue
import com.chumikov.rickandmorty.ui_theme.Purple200
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: CharacterDetailsViewModel.Factory

    private val viewModel by getViewModel<CharacterDetailsViewModel> {
        viewModelFactory.get(args.characterId)
    }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }


    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding: FragmentCharacterDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterDetailsBinding == null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentCharacterDetailsBinding
//            .inflate(inflater, container, false)
//        return binding.root

        return ComposeView(requireContext()).apply {
            setContent {
                when (val stateValue = viewModel.status.collectAsState().value) {
                    is CharacterDetailsLoadingState.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MyBlue)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = {

                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Arrow Back"
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Character's Detailed Info",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.width(48.dp))
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MyBlue)
                                    .padding(
                                        start = 14.dp,
                                        end = 14.dp,
                                        top = 10.dp,
                                        bottom = 60.dp
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MyLightBlue)
                            ) {
                                val imagePlaceholder = R.drawable.placeholder_image
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            BorderStroke(3.dp, MyBlue),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .weight(1f),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(stateValue.data.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(imagePlaceholder),
                                    error = painterResource(imagePlaceholder),
                                    contentDescription = "Character's Photo",
                                    contentScale = ContentScale.Crop,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = String.format(
                                        getString(R.string.name_template),
                                        stateValue.data.name
                                    ),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = String.format(
                                        getString(R.string.location_template),
                                        stateValue.data.location
                                    ),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = String.format(
                                        getString(R.string.species_template),
                                        stateValue.data.species
                                    ),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = String.format(
                                        getString(R.string.status_template),
                                        stateValue.data.status
                                    ),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 26.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        ),
                                    onClick = { },
                                    colors = ButtonDefaults.buttonColors(MyBlue)
                                ) {
                                    Text(
                                        text = "GO TO THE EPISODES INFO",
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }

                            }
                        }
                    }

                    is CharacterDetailsLoadingState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MyBlue),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(80.dp),
                                color = MyLightBlue
                            )
                        }
                    }

                    is CharacterDetailsLoadingState.Error -> {
                        Button(onClick = { }) {
                            Text(text = "Ошибка")
                        }
                    }
                }

            }
        }
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val filler = R.drawable.placeholder_image
//
//        val toolbar = binding.toolbar
//        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
//
//        val retryButton = binding.retryButton
//        retryButton.setOnClickListener { viewModel.retry() }
//
//        lifecycleScope.launch {
//            viewModel.status.collect { state ->
//                binding.mainCardView.isInvisible = state !is CharacterDetailsLoadingState.Success
//                binding.loader.isInvisible = state !is CharacterDetailsLoadingState.Loading
//                toolbar.isInvisible = state !is CharacterDetailsLoadingState.Success
//                retryButton.isInvisible = state !is CharacterDetailsLoadingState.Error
//
//                if (state is CharacterDetailsLoadingState.Success) {
//                    binding.characterPhoto.load(state.data.imageUrl) {
//                        placeholder(filler)
//                        error(filler)
//                        fallback(filler)
//                    }
//                    binding.characterName.text = String.format(
//                        getString(R.string.name_template), state.data.name
//                    )
//                    binding.characterLocation.text = String.format(
//                        getString(R.string.location_template), state.data.location
//                    )
//                    binding.characterSpecies.text = String.format(
//                        getString(R.string.species_template), state.data.species
//                    )
//                    binding.characterStatus.text = String.format(
//                        getString(R.string.status_template), state.data.status
//                    )
//                    toEpisodesScreen(state)
//                }
//            }
//        }
//    }
//
//    private fun toEpisodesScreen(state: CharacterDetailsLoadingState.Success) {
//        binding.episodesButton.setOnClickListener {
//            findNavController().navigate(
//                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeFragment(
//                    state.data.episodes.toIntArray()
//                )
//            )
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun foo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyBlue),
        contentAlignment = Alignment.Center,
    ) {
//        TopAppBar(
//            modifier = Modifier.padding(horizontal = 12.dp),
//            title = {
//                Text(
//                    modifier = Modifier.padding(horizontal = 24.dp),
//                    text = "Character's Detailed Info",
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = FontFamily.Serif,
//                    fontSize = 20.sp,
//                    textAlign = TextAlign.Center
//                )
//            },
//            navigationIcon = {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
//                    contentDescription = "Arrow Back"
//                )
//            },
//            colors = TopAppBarDefaults.topAppBarColors(MyBlue)
//        )

        CircularProgressIndicator(
            color = MyLightBlue
        )
    }
}