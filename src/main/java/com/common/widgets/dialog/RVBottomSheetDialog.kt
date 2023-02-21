package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.common.widgets.R
import com.common.widgets.databinding.FragmentMenuBottomSheetDialogBinding
import com.common.widgets.decoration.MarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RVBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetDialogBinding
    private var adapter: RecyclerView.Adapter<*>? = null
    private var marginItemDecoration: MarginItemDecoration? = null

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
            marginItemDecoration?.let { addItemDecoration(it) }
            adapter = this@RVBottomSheetDialog.adapter
        }
    }

    companion object {
        /**
         * Generate a new instance of common menu dialog, don't try to new instance by class constructor
         * @param type, it means the type of menu that you can define yourself
         * @param items, menu content
         * */
        @JvmStatic
        fun newInstance(
            customAdapter: RecyclerView.Adapter<*>,
            decoration: MarginItemDecoration? = null
        ) = RVBottomSheetDialog().apply {
            if (decoration != null) {
                marginItemDecoration = decoration
            }
            setStyle(
                STYLE_NORMAL,
                R.style.CustomBottomSheetDialogTheme
            )
            adapter = customAdapter
        }
    }
}