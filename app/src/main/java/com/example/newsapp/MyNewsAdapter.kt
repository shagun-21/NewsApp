package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyNewsAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<MyNewsAdapter.NewsViewHolder>() {

    private val items:ArrayList<News> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout,parent,false)
        val viewholder=NewsViewHolder(view)
        view.setOnClickListener {
            listener.OnItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.newsTitle.text=currentItem.title
        holder.authorName.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageurl).into(holder.image)
    }
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle:TextView=itemView.findViewById(R.id.tv_title_of_news)
        val image:ImageView=itemView.findViewById(R.id.news_image)
        val authorName:TextView=itemView.findViewById(R.id.tv_author_name)
    }
}
interface NewsItemClicked{
    fun OnItemClicked(item: News){

    }
}