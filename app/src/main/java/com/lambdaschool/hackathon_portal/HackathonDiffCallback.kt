package com.lambdaschool.hackathon_portal

import androidx.recyclerview.widget.DiffUtil
import com.lambdaschool.hackathon_portal.model.Hackathon

class HackathonDiffCallback(
    private val oldList: MutableList<Hackathon>,
    private val newList: MutableList<Hackathon>) : DiffUtil.Callback () {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].url == newList[newItemPosition].url &&
                oldList[oldItemPosition].start_date == newList[newItemPosition].start_date &&
                oldList[oldItemPosition].end_date == newList[newItemPosition].end_date &&
                oldList[oldItemPosition].location == newList[newItemPosition].location &&
                oldList[oldItemPosition].is_open == newList[newItemPosition].is_open)
    }

}