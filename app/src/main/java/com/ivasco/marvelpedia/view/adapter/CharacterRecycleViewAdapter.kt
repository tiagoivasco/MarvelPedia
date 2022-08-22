package com.ivasco.marvelpedia.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ivasco.marvelpedia.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*
import com.ivasco.marvelpedia.model.Character
import com.ivasco.marvelpedia.model.Thumbnail

class CharacterRecycleViewAdapter(private val itemClickListener: (Character) -> Unit) :
    PagedListAdapter<Character, CharacterRecycleViewAdapter.CharacterViewHolder>(characterDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterRecycleViewAdapter.CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CharacterRecycleViewAdapter.CharacterViewHolder,
        position: Int
    ) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character, itemClickListener)
        }
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character, clickListener: (Character) -> Unit) {
            itemView.list_item_title.text = character.name
            Picasso.get()
                .load(character.thumbnail.getUrl(Thumbnail.STANDARD_MEDIUM))
                .fit()
                .into(itemView.thumbnail)
            itemView.setOnClickListener { clickListener(character) }
        }

    }
}