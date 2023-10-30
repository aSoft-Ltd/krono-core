package krono

sealed external interface IntervalTimer

sealed external interface TimeoutTimer

external fun setInterval(handler: () -> Unit, milliseconds: Int): IntervalTimer

external fun clearInterval(timer: IntervalTimer)

external fun setTimeout(handler: () -> Unit, milliseconds: Int): TimeoutTimer

external fun clearTimeout(timer: TimeoutTimer)