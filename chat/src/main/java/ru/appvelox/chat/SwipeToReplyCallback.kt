package ru.appvelox.chat

import android.content.Context
import android.graphics.Canvas
import android.os.Vibrator
import android.view.MotionEvent
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import kotlin.math.abs

class SwipeToReplyCallback : ItemTouchHelper.Callback() {

    private val MAX_TRIGGER_OFFSET = 500f
    private val MIN_TRIGGER_OFFSET = 100f
    private val DEFAULT_TRIGGER_OFFSET = 200f

    private val MIN_ACTION_ICON_START_APPEARING_OFFSET = 0f
    private val DEFAULT_ACTION_ICON_START_APPEARING_OFFSET = 100f

    private val MIN_VIBRATION_DURATION = 0L
    private val MAX_VIBRATION_DURATION = 500L
    private val DEFAULT_VIBRATION_DURATION = 10L

    var triggerOffset = DEFAULT_TRIGGER_OFFSET
    set(value) {
        if(value > MAX_TRIGGER_OFFSET)
            field = MAX_TRIGGER_OFFSET
        else if(value < MIN_TRIGGER_OFFSET)
            field = MIN_TRIGGER_OFFSET
        else field = value
    }

    var actionIconStartAppearingOffset: Float = DEFAULT_ACTION_ICON_START_APPEARING_OFFSET
    set (value){
        if(value > triggerOffset){
            field = triggerOffset
        } else if(value <= MIN_ACTION_ICON_START_APPEARING_OFFSET){
            field = MIN_ACTION_ICON_START_APPEARING_OFFSET
        } else {
            field = value
        }
    }

    var vibrationDuration = DEFAULT_VIBRATION_DURATION
    set(value) {
        if(value > MAX_VIBRATION_DURATION){
            field = MAX_VIBRATION_DURATION
        } else if(value < MIN_VIBRATION_DURATION){
            field = MIN_VIBRATION_DURATION
        } else {
            field = value
        }
    }

    var listener: ChatView.OnSwipeActionListener? = null

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 1f
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 0f
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 4000f
    }

    private var isVibrationCompleted = false
    private var lastDXPosition = 0f
    private var currentSwipingTextMessage: Message? = null

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        lastDXPosition = dX
        currentSwipingTextMessage = (viewHolder as MessageViewHolder).message

        setTouchListener(recyclerView)

        if (dX < -triggerOffset) {
            if(!isVibrationCompleted){
                (recyclerView.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(vibrationDuration)
                isVibrationCompleted = true
            }
            makeActionIconOpaque(viewHolder.itemView.imageViewLeftSwipeActionIcon)

            return
        }

        if (dX == 0f) {
            makeActionIconTransparent(viewHolder.itemView.imageViewLeftSwipeActionIcon)
            isVibrationCompleted = false
        }
        if (dX < -actionIconStartAppearingOffset) {
            setActionIconAlpha(dX, viewHolder.itemView.imageViewLeftSwipeActionIcon)
        }

        if (dX > -triggerOffset)
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                viewHolder.itemView.contentContainer,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
    }

    private fun setTouchListener(recyclerView: RecyclerView) {
        recyclerView.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_UP && lastDXPosition <= -triggerOffset )
                currentSwipingTextMessage?.let {
                    listener?.onAction(it)
                }
            false
        }
    }

    private fun setActionIconAlpha(dX: Float, icon: ImageView) {
        val progress = (dX + actionIconStartAppearingOffset) / (triggerOffset - actionIconStartAppearingOffset)
        val alpha = abs((255 * progress).toInt())
        icon.imageAlpha = alpha
    }

    private fun makeActionIconOpaque(icon: ImageView) {
        icon.imageAlpha = 255
    }

    private fun makeActionIconTransparent(icon: ImageView) {
        icon.imageAlpha = 0
    }

}