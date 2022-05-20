package com.deepshikhayadav.geetacollege.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import kotlinx.android.synthetic.main.layout_blog.view.*

class BlogAdapter (private val blog: List<BLog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int = blog.count()

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) =
        holder.bind(blog[position])

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            private const val IMAGE_BASE_URL = "https://deepu-api-health-freak.herokuapp.com/uploads/"
        }

        fun bind(blog: BLog) {
            Log.i("image", IMAGE_BASE_URL+blog.image)
            Glide.with(itemView.context).load(IMAGE_BASE_URL + blog.image)
                .into(itemView.blogImage)
             itemView.blogTitle.text = blog.heading
             itemView.blogDesc.text = blog.desc
            // itemView.avgVoting.text = blog.author

        }
    }
}
