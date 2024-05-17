package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

                Log.d("my compose", "внутри setContent")

                val state = viewModel.status.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MyBlue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("my compose", "внутри основного контейнера")

                    when (val curState = state.value) {
                        is CharacterDetailsLoadingState.Success -> {
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
                                    text = "Character's Detailed Info",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
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
                                val placeholderPainter =
                                    painterResource(R.drawable.placeholder_image)
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
                                    )
                                }
                            }
                        }

                        is CharacterDetailsLoadingState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(72.dp),
                                color = MyLightBlue,
                                strokeWidth = 6.dp
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

@Composable
fun Container(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}


@Preview
@Composable
fun JustForPreview() {
    Container {
        CircularProgressIndicator(
            modifier = Modifier.size(72.dp),
            color = MyLightBlue,
            strokeWidth = 6.dp
        )
    }
}