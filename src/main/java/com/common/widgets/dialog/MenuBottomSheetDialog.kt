package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.common.widgets.R
import com.common.widgets.databinding.FragmentMenuBottomSheetDialogBinding
import com.common.widgets.databinding.ItemMenuBottomSheetDialogBinding
import com.common.widgets.decoration.MarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun interface OnMenuItemListener {
    fun onMenuClicked(menuType: Int, position: Int)
}

class MenuBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetDialogBinding
    private var menuItemListener: OnMenuItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBottomSheetDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(MarginItemDecoration(top = resources.getDimensionPixelSize(R.dimen.standard_margin_divided_2)))
            adapter = MenuBottomSheetDialogAdapter(
                arguments?.getSerializable(BUNDLE_MENU_ITEMS) as? List<String> ?: emptyList()
            ) {
                dismiss()
                menuItemListener?.onMenuClicked(
                    arguments?.getInt(BUNDLE_MENU_TYPE) ?: UNKNOWN_MENU_TYPE, it
                )
            }
        }
    }

    companion object {
        private const val BUNDLE_MENU_ITEMS = "BUNDLE_MENU_ITEMS"
        private const val BUNDLE_MENU_TYPE = "BUNDLE_MENU_TYPE"
        private const val UNKNOWN_MENU_TYPE = -1

        /**
         * Generate a new instance of common menu dialog, don't try to new instance by class constructor
         * @param type, it means the type of menu that you can define yourself
         * @param items, menu content
         * */
        fun newInstance(
            type: Int = 0,
            items: List<String> = emptyList(),
            menuItemListener: OnMenuItemListener? = null
        ) = MenuBottomSheetDialog().apply {
            setStyle(
                STYLE_NORMAL,
                R.style.CustomBottomSheetDialogTheme
            )
            arguments = bundleOf(BUNDLE_MENU_TYPE to type, BUNDLE_MENU_ITEMS to items)
            this.menuItemListener = menuItemListener
        }
    }
}

class MenuBottomSheetDialogAdapter(
    private val list: List<String>,
    private val itemListener: (position: Int) -> Unit
) : RecyclerView.Adapter<MenuBottomSheetDialogAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemMenuBottomSheetDialogBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemMenuBottomSheetDialogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { holder -> holder.itemView.setOnClickListener { itemListener(holder.adapterPosition) } }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.title.text = list[position]
    }

    override fun getItemCount(): Int = list.size
}