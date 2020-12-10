package ru.appvelox.chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Vibrator
import android.view.MotionEvent
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.viewholder.MessageViewHolder
import kotlin.math.abs

/**
 * Class for changing position of message after swipe
 */
class SwipeToReplyCallback : ItemTouchHelper.Callback() {

    var triggerOffset = DEFAULT_TRIGGER_OFFSET
        set(value) {
            field = when {
                value > MAX_TRIGGER_OFFSET -> MAX_TRIGGER_OFFSET
                value < MIN_TRIGGER_OFFSET -> MIN_TRIGGER_OFFSET
                else -> value
            }
        }

    var actionIconStartAppearingOffset: Float = DEFAULT_ACTION_ICON_START_APPEARING_OFFSET
        set(value) {
            field = when {
                value > triggerOffset -> {
                    triggerOffset
                }
                value <= MIN_ACTION_ICON_START_APPEARING_OFFSET -> {
                    MIN_ACTION_ICON_START_APPEARING_OFFSET
                }
                else -> {
                    value
                }
            }
        }

    var vibrationDuration = DEFAULT_VIBRATION_DURATION
        set(value) {
            field = when {
                value > MAX_VIBRATION_DURATION -> {
                    MAX_VIBRATION_DURATION
                }
                value < MIN_VIBRATION_DURATION -> {
                    MIN_VIBRATION_DURATION
                }
                else -> {
                    value
                }
            }
        }

    var listener: ChatView.OnSwipeActionListener? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
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

        val leftSwipeActionIcon =
            viewHolder.itemView.findViewById<ImageView>(R.id.imageViewLeftSwipeActionIcon)

        if (dX < -triggerOffset) {
            if (!isVibrationCompleted) {
                @Suppress("DEPRECATION")
                (recyclerView.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(
                    vibrationDuration
                )
                isVibrationCompleted = true
            }
            makeActionIconOpaque(leftSwipeActionIcon)

            return
        }

        if (dX == 0f) {
            makeActionIconTransparent(leftSwipeActionIcon)
            isVibrationCompleted = false
        }
        if (dX < -actionIconStartAppearingOffset) {
            setActionIconAlpha(dX, leftSwipeActionIcon)
        }

        if (dX > -triggerOffset)
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                viewHolder.itemView.findViewById(R.id.contentContainer),
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(recyclerView: RecyclerView) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && lastDXPosition <= -triggerOffset)
                currentSwipingTextMessage?.let {
                    listener?.onAction(it)
                }
            false
        }
    }

    private fun setActionIconAlpha(dX: Float, icon: ImageView) {
        val progress =
            (dX + actionIconStartAppearingOffset) / (triggerOffset - actionIconStartAppearingOffset)
        val alpha = abs((255 * progress).toInt())
        icon.imageAlpha = alpha
    }

    private fun makeActionIconOpaque(icon: ImageView) {
        icon.imageAlpha = 255
    }

    private fun makeActionIconTransparent(icon: ImageView) {
        icon.imageAlpha = 0
    }

    companion object {
        private const val MAX_TRIGGER_OFFSET = 500f
        private const val MIN_TRIGGER_OFFSET = 100f
        private const val DEFAULT_TRIGGER_OFFSET = 200f

        private const val MIN_ACTION_ICON_START_APPEARING_OFFSET = 0f
        private const val DEFAULT_ACTION_ICON_START_APPEARING_OFFSET = 100f

        private const val MIN_VIBRATION_DURATION = 0L
        private const val MAX_VIBRATION_DURATION = 500L
        private const val DEFAULT_VIBRATION_DURATION = 10L
    }
}