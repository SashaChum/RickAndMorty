package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.ui_theme.MyBlue
import com.chumikov.rickandmorty.ui_theme.MyDarkBlue
import com.chumikov.rickandmorty.ui_theme.MyLightBlue
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


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
                        is EpisodesLoadingState.Success -> {

                            NavigationBar("List Of The Episodes") {
                                findNavController().popBackStack()
                            }

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 8.dp)
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
                                            ),
                                            paddingBottom = 4
                                        )
                                    }
                                }
                            }
                        }

                        is EpisodesLoadingState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(68.dp),
                                color = MyLightBlue,
                                strokeWidth = 7.dp
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


@Composable
private fun TextTemplate(
    text: String,
    paddingLeft: Int = 8,
    paddingRight: Int = 8,
    paddingTop: Int = 2,
    paddingBottom: Int = 2,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = paddingLeft.dp,
                end = paddingRight.dp,
                top = paddingTop.dp,
                bottom = paddingBottom.dp

            ),
        text = text,
        fontFamily = FontFamily.Serif,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}
