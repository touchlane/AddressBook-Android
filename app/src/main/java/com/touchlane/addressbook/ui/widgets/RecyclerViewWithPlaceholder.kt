package com.touchlane.addressbook.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.touchlane.addressbook.R
import com.touchlane.addressbook.databinding.ViewRecyclerWithPlaceholderBinding
import com.touchlane.addressbook.domain.model.DomainContact

class RecyclerViewWithPlaceholder(context: Context,
                                  attrs: AttributeSet? = null,
                                  defStyle: Int = 0): FrameLayout(context, attrs, defStyle) {
    private val placeholder: View?
    private val dataObserver: RecyclerView.AdapterDataObserver
    val recyclerView: RecyclerView
    var onItemSelectedListener: SelectItemListener = object : SelectItemListener {
        override fun onClick(contact: DomainContact) {}
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {

        val layoutInflater = LayoutInflater.from(context)
        val binding = ViewRecyclerWithPlaceholderBinding.inflate(layoutInflater, this, true)
        recyclerView = binding.recyclerList

        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewWithPlaceholder)
        val placeholderId =
            attrsArray.getResourceId(R.styleable.RecyclerViewWithPlaceholder_placeholderId, NO_PLACEHOLDER)
        attrsArray.recycle()

        if (placeholderId != NO_PLACEHOLDER) {
            placeholder = layoutInflater.inflate(placeholderId, this, false)
            addView(placeholder)
        } else {
            placeholder = null
        }

        dataObserver = object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                setupList()
            }
        }
    }

    private fun setupList() {
        val showPlaceHolder = recyclerView.adapter?.itemCount == 0
        placeholder?.visibility = if (showPlaceHolder) View.VISIBLE else View.INVISIBLE
        recyclerView.visibility = if (showPlaceHolder) View.INVISIBLE else View.VISIBLE
    }

    fun setLayoutManager(manager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = manager
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        adapter?.registerAdapterDataObserver(dataObserver)
        recyclerView.adapter?.unregisterAdapterDataObserver(dataObserver)
        recyclerView.adapter = adapter
        setupList()
    }

    @FunctionalInterface
    interface SelectItemListener {
        fun onClick(contact: DomainContact)
    }

    companion object {
        private const val NO_PLACEHOLDER = -1
    }
}