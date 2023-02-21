package com.common.widgets.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.common.widgets.databinding.ViewTextWithLabelBinding
import com.common.widgets.dialog.data.LabelWithContent

class CommonDetailContentAdapter(private val data: List<LabelWithContent>) :
    RecyclerView.Adapter<CommonDetailContentAdapter.TitleContentHolder>() {
    class TitleContentHolder(val binding: ViewTextWithLabelBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleContentHolder =
        TitleContentHolder(
            ViewTextWithLabelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TitleContentHolder, position: Int) {
        holder.binding.apply {
            label.text = data[position].label
            content.text = data[position].content
        }
    }

    override fun getItemCount(): Int = data.size
}