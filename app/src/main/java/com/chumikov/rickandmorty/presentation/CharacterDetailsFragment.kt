package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.ui_theme.MyBlue
import com.chumikov.rickandmorty.ui_theme.MyDarkBlue
import com.chumikov.rickandmorty.ui_theme.MyLightBlue
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

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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
                        is CharacterDetailsLoadingState.Success -> {

                            NavigationBar("Character's Detailed Info") {
                                findNavController().popBackStack()
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 14.dp,
                                        end = 14.dp,
                                        bottom = 20.dp
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MyLightBlue)
                            ) {
                                val placeholderPainter =
                                    painterResource(R.drawable.placeholder_image)
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            BorderStroke(3.dp, MyBlue),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .weight(1f),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(curState.data.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = placeholderPainter,
                                    error = placeholderPainter,
                                    fallback = placeholderPainter,
                                    contentDescription = "Character's Photo",
                                    contentScale = ContentScale.Crop,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = String.format(
                                        getString(R.string.name_template),
                                        curState.data.name
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
                                        curState.data.location
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
                                        curState.data.species
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
                                        curState.data.status
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
                                    onClick = {
                                        findNavController().navigate(
                                            CharacterDetailsFragmentDirections
                                                .actionCharacterDetailsFragmentToEpisodeFragment(
                                                    curState.data.episodes.toIntArray()
                                                )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(MyBlue)
                                ) {
                                    Text(
                                        text = "GO TO THE EPISODES INFO",
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }

                        is CharacterDetailsLoadingState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(68.dp),
                                color = MyLightBlue,
                                strokeWidth = 7.dp
                            )
                        }

                        is CharacterDetailsLoadingState.Error -> {
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
