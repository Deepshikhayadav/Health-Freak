package com.deepshikhayadav.geetacollege.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.databinding.ListitemYogaBinding
import com.deepshikhayadav.geetacollege.databinding.YogaFragmentBinding
import com.deepshikhayadav.geetacollege.local_db.entity.BLog
import com.deepshikhayadav.geetacollege.local_db.entity.Yoga
import kotlinx.android.synthetic.main.layout_blog.view.*
import kotlinx.android.synthetic.main.listitem_yoga.view.*

class YogaAdapter (/*private val yoga: List<Yoga>,*/private val onItemClicked: (Yoga) -> Unit)
  /* : RecyclerView.Adapter<YogaAdapter.YogaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_yoga, parent, false)
        return YogaViewHolder(view)
    }

    override fun getItemCount(): Int = yoga.count()

    override fun onBindViewHolder(holder: YogaViewHolder, position: Int) =
        holder.bind(yoga[position])

    class YogaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            private const val IMAGE_BASE_URL = "https://deepu-api-health-freak.herokuapp.com/uploads/"
        }

        fun bind(yoga: Yoga) {
            Log.i("image", IMAGE_BASE_URL+yoga.image)
            Glide.with(itemView.context).load(IMAGE_BASE_URL + yoga.image)
                .into(itemView.imageYoga)
            itemView.yogaTitle.text = yoga.heading
            itemView.yogaDesc.text = yoga.desc
           // itemView.avgVoting.text = yoga.author

        }
    }
}*/

    : ListAdapter<Yoga, YogaAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListitemYogaBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ListitemYogaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            private const val IMAGE_BASE_URL =
                "https://deepu-api-health-freak.herokuapp.com/uploads/"
        }

        fun bind(yoga: Yoga) {
           // Log.i("image", IMAGE_BASE_URL + yoga.image)
            Glide.with(itemView.context).load(IMAGE_BASE_URL + yoga.image)
                .into(binding.imageYoga)
            binding.yogaTitle.text = yoga.heading
            binding.yogaDesc.text = yoga.desc


        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Yoga>() {


            override fun areContentsTheSame(oldYoga: Yoga, newYoga: Yoga): Boolean {
                return oldYoga.heading == newYoga.heading
            }

            override fun areItemsTheSame(oldItem: Yoga, newItem: Yoga): Boolean {
                return oldItem === newItem
            }
        }
    }
}