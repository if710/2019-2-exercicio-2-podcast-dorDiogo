package br.ufpe.cin.android.podcast

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*

class xmlDataViewAdapter(private val itemFeeds: List<ItemFeed>) :
    RecyclerView.Adapter<xmlDataViewAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val item_title = item.item_title
        val item_date = item.item_date
        val item_action = item.item_action
        val item_context = item.context
        var item_guid: String = ""

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context, EpisodeDetailActivity::class.java)
            intent.putExtra("guid", item_guid)
            startActivity(v.context, intent, null)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlista, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemFeed = itemFeeds[position]
        holder.item_title.text = itemFeed.title
        holder.item_date.text = itemFeed.pubDate
        holder.item_guid = itemFeed.downloadLink
        holder.item_action.setOnClickListener {
            Toast.makeText(
                holder.item_context,
                "This button is useless but should download ${itemFeed.link}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount() = itemFeeds.size
}