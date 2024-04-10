import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.ItemEpisodeBinding
import com.chumikov.rickandmorty.domain.Episode
import com.chumikov.rickandmorty.presentation.adapters.EpisodeDiffCallback
import com.chumikov.rickandmorty.presentation.adapters.EpisodeListViewHolder

class EpisodeListAdapter(
    private val context: Context
) : ListAdapter<Episode, EpisodeListViewHolder>(EpisodeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeListViewHolder {
        val binding = ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        )
        return EpisodeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeListViewHolder, position: Int) {
        val episode = getItem(position)
        with(holder.binding) {
            val nameTemplate = context.resources.getString(R.string.name_template)
            val airDateTemplate = context.resources.getString(R.string.air_date_template)
            episodeName.text = String.format(nameTemplate, episode.name)
            episodeAirDate.text = String.format(airDateTemplate, episode.airDate)
        }
    }
}