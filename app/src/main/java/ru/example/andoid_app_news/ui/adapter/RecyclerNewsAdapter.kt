package ru.example.andoid_app_news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.model.data.News

class RecyclerNewsAdapter : ListAdapter<News, RecyclerNewsAdapter.NewsHolder>(AsyncDifferConfig.Builder(
    DiffCallback()
).build()) {

    var itemClickListener: ( (Int) -> Unit )? = null

    private class DiffCallback : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem == newItem

    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val title: TextView = itemView.findViewById(R.id.news_title)
        val source: TextView = itemView.findViewById(R.id.news_source)
        val date: TextView = itemView.findViewById(R.id.news_date)
        val imgView: ImageView = itemView.findViewById(R.id.newsImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_element, parent, false)
        return NewsHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item: News = getItem(position)

        holder.title.text = item.title
        holder.source.text = item.source
        holder.date.text = item.date

        itemClickListener?.let { itemListener ->
            holder.view.setOnClickListener {
                itemListener(position)
            }
        }
        if (item.img == null)
            holder.imgView.visibility = View.GONE
        else {
            Glide.with(holder.imgView.context)
                .asBitmap()
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .load(item.img)
                .into(holder.imgView)
        }
    }

}