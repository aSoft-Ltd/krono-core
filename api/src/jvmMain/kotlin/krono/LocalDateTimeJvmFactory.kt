@file:Suppress("NOTHING_TO_INLINE")

package krono

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun Clock.currentJavaLocalDateTime() = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentMillisAsLong()), ZoneId.of("UTC"))