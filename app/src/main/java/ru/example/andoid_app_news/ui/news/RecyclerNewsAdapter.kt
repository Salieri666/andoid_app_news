package ru.example.andoid_app_news.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.model.NewsModel

class RecyclerNewsAdapter : RecyclerView.Adapter<RecyclerNewsAdapter.NewsHolder>()  {

    private var news: List<NewsModel> = arrayListOf(
        NewsModel("","","Title1", "Date1","","Source1"),
        NewsModel("","","Title2", "Date2","","Source2"),
        NewsModel("","","Title3", "Date3","","Source3"),
        NewsModel("","","Title1", "Date1","","Source1"),
        NewsModel("","","Title2", "Date2","","Source2"),
        NewsModel("","","Title3", "Date3","","Source3"),
        NewsModel("","","Title1", "Date1","","Source1"),
        NewsModel("","","Title2", "Date2","","Source2"),
        NewsModel("","","Title3", "Date3","","Source3"),
        NewsModel("","","Title1", "Date1","","Source1"),
        NewsModel("","","Title2", "Date2","","Source2"),
        NewsModel("","","Title3", "Date3","","Source3"),
        NewsModel("","","Title1", "Date1","","Source1"),
        NewsModel("","","Title2", "Date2","","Source2"),
        NewsModel("","","Title3", "Date3","","Source3")
    )

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.news_title)
        val source: TextView = itemView.findViewById(R.id.news_source)
        val date: TextView = itemView.findViewById(R.id.news_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_element, parent, false)
        return NewsHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item: NewsModel = news[position]

        holder.title.text = item.title
        holder.source.text = item.source
        holder.date.text = item.date
    }

    override fun getItemCount(): Int {
        return news.size
    }
}