package com.common.widgets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.common.widgets.R
import com.common.widgets.databinding.FragmentSearchMenuBottomSheetDialogBinding
import com.common.widgets.decoration.MarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchRVBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchMenuBottomSheetDialogBinding
    private var adapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMenuBottomSheetDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(MarginItemDecoration(top = resources.getDimensionPixelSize(R.dimen.standard_margin_divided_2)))
            adapter = this@SearchRVBottomSheetDialog.adapter
            binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false
                override fun onQueryTextChange(newText: String?): Boolean {
                    (adapter as Filterable).filter.filter(newText)
                    return false
                }
            })
        }
    }

    companion object {
        /**
         * Generate a new instance of common menu dialog, don't try to new instance by class constructor
         * @param type, it means the type of menu that you can define yourself
         * @param items, menu content
         * */
        fun newInstance(
            customAdapter: RecyclerView.Adapter<*>
        ) = SearchRVBottomSheetDialog().apply {
            setStyle(
                STYLE_NORMAL,
                R.style.CustomBottomSheetDialogTheme
            )
            adapter = customAdapter
        }
    }
}