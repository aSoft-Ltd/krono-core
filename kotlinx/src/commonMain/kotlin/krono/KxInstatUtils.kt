@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(ExperimentalTime::class)

package krono

import krono.internal.InstantImpl
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant as KxInstant
import kotlin.time.Instant as KtInstant

inline fun KxInstant.toInstant(): Instant = InstantImpl(toEpochMilliseconds() * 1000)
inline fun KtInstant.toInstant(): Instant = InstantImpl(toEpochMilliseconds() * 1000)