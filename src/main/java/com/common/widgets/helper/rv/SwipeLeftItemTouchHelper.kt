package com.common.widgets.helper.rv

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class SwipeLeftItemTouchHelper(
    context: Context?,
    private val buttonBindingCallback: UnderlayButtonBindingCallback
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private var recyclerView: RecyclerView? = null
    private var buttons: MutableList<UnderlayButton> = mutableListOf()
    private var swipedPos = -1
    private var excludePosition = arrayListOf<Int>()
    private var swipeThreshold = 0.3f
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>>

    private val recoverQueue: Queue<Int> =
        object : LinkedList<Int>() {
            override fun add(element: Int): Boolean {
                return if (contains(element)) false else super.add(element)
            }
        }
    private val gestureListener: GestureDetector.SimpleOnGestureListener =
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                for (button in buttons) {
                    if (button.onClick(e.x, e.y)) break
                }
                return true
            }
        }
    private val gestureDetector: GestureDetector = GestureDetector(context, gestureListener)

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, e ->
        if (swipedPos < 0) return@OnTouchListener false
        val point = Point(e.rawX.toInt(), e.rawY.toInt())
        val swipedViewHolder = recyclerView?.findViewHolderForAdapterPosition(swipedPos)
        val swipedItem = swipedViewHolder!!.itemView
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)
        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
            if (rect.top < point.y && rect.bottom > point.y) gestureDetector.onTouchEvent(e) else {
                recoverQueue.add(swipedPos)
                swipedPos = -1
                recoverSwipedItem()
            }
        }
        false
    }

    fun attach(rv: RecyclerView) {
        recyclerView = rv
        ItemTouchHelper(this).attachToRecyclerView(rv)
        rv.setOnTouchListener(onTouchListener)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipedPos != pos) recoverQueue.add(swipedPos)
        swipedPos = pos
        if (buttonsBuffer.containsKey(swipedPos)) buttons =
            buttonsBuffer[swipedPos]!! else buttons.clear()
        buttonsBuffer.clear()
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.01f
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0 || pos in excludePosition) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<UnderlayButton> = ArrayList()
                if (!buttonsBuffer.containsKey(pos)) {
                    buttonBindingCallback.instantiate(viewHolder, buffer)
                    buttonsBuffer[pos] = buffer
                } else {
                    buffer = buttonsBuffer[pos]!!
                }
                translationX = dX * buffer.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val pos = recoverQueue.poll() ?: -1
            if (pos > -1) {
                recyclerView?.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>,
        pos: Int,
        dX: Float
    ) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1 * dX / buffer.size
        for (button in buffer) {
            val left = right - dButtonWidth
            button.onDraw(
                c,
                RectF(
                    left,
                    itemView.top.toFloat(),
                    right,
                    itemView.bottom.toFloat()
                ),
                pos
            )
            right = left
        }
    }

    fun excludePosition(position: Int) = excludePosition.add(position)

    companion object {
        const val BUTTON_WIDTH = 200
    }

    init {
        buttons = ArrayList()
        buttonsBuffer = HashMap()
    }
}