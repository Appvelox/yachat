package ru.appvelox.chat

import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class SwipeToReplyCallbackTest {
    lateinit var callback: SwipeToReplyCallback

    @Before
    fun init() {
        callback = spyk()
    }

    @Test
    fun setTriggerOffset_moreThanMax() {
        val bigOffset = 1000f

        callback.triggerOffset = bigOffset

        assertNotEquals(bigOffset, callback.triggerOffset)
        assertEquals(MAX_TRIGGER_OFFSET, callback.triggerOffset)
    }

    @Test
    fun setTriggerOffset_lessThanMin() {
        val smallOffset = 50f

        callback.triggerOffset = smallOffset

        assertNotEquals(smallOffset, callback.triggerOffset)
        assertEquals(MIN_TRIGGER_OFFSET, callback.triggerOffset)
    }

    @Test
    fun setActionIconStartAppearingOffset_moreThanDefault() {
        val bigOffset = 300f

        callback.actionIconStartAppearingOffset = bigOffset

        assertNotEquals(bigOffset, callback.actionIconStartAppearingOffset)
        assertEquals(DEFAULT_TRIGGER_OFFSET, callback.actionIconStartAppearingOffset)
    }

    @Test
    fun setActionIconStartAppearingOffset_lessThanMin() {
        val negativeOffset = -100f

        callback.actionIconStartAppearingOffset = negativeOffset

        assertNotEquals(negativeOffset, callback.actionIconStartAppearingOffset)
        assertEquals(MIN_ACTION_ICON_START_APPEARING_OFFSET, callback.actionIconStartAppearingOffset)
    }

    @Test
    fun setVibrationDuration_moreThanMax() {
        val bigDuration = 1000L

        callback.vibrationDuration = bigDuration

        assertNotEquals(bigDuration, callback.vibrationDuration)
        assertEquals(MAX_VIBRATION_DURATION, callback.vibrationDuration)
    }

    @Test
    fun setVibrationDuration_lessThanMin() {
        val negativeDuration = -100L

        callback.vibrationDuration = negativeDuration

        assertNotEquals(negativeDuration, callback.vibrationDuration)
        assertEquals(MIN_VIBRATION_DURATION, callback.vibrationDuration)
    }

    companion object {
        private const val MAX_TRIGGER_OFFSET = 500f
        private const val MIN_TRIGGER_OFFSET = 100f
        private const val DEFAULT_TRIGGER_OFFSET = 200f

        private const val MIN_ACTION_ICON_START_APPEARING_OFFSET = 0f

        private const val MIN_VIBRATION_DURATION = 0L
        private const val MAX_VIBRATION_DURATION = 500L
    }
}