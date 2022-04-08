package ru.example.andoid_app_news.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.model.ui.News

class RecyclerNewsAdapter : RecyclerView.Adapter<RecyclerNewsAdapter.NewsHolder>()  {

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private var news: List<News> = ArrayList()

    class NewsHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.news_title)
        val source: TextView = itemView.findViewById(R.id.news_source)
        val date: TextView = itemView.findViewById(R.id.news_date)

        init {
            itemView.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_element, parent, false)
        return NewsHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item: News = news[position]

        holder.title.text = item.title
        holder.source.text = item.source
        holder.date.text = item.date
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun refreshNews(news: List<News>) {
        this.news = news
        notifyItemInserted(news.size)
    }
}