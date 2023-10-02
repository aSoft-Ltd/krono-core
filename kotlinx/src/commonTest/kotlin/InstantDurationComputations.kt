import kommander.expect
import krono.Instant
import krono.LocalDate
import krono.TimeZones
import krono.days
import krono.months
import kotlin.test.Test

class InstantDurationComputations {
    @Test
    fun should_add_a_duration() {
        val i1 = Instant(0)
        expect(i1.atZone(TimeZones.UTC).date).toBe(LocalDate(1970,1,1).getOrThrow())
        val i2 = i1 + 1.days
        expect(i2.atZone(TimeZones.UTC).date).toBe(LocalDate(1970,1,2).getOrThrow())
    }

    @Test
    fun should_add_thirty_days_to_a_duration() {
        val i1 = Instant(0)
        expect(i1.atZone(TimeZones.UTC).date).toBe(LocalDate(1970,1,1).getOrThrow())
        val i2 = i1 + 30.days
        expect(i2.atZone(TimeZones.UTC).date).toBe(LocalDate(1970,1,31).getOrThrow())

        val i3 = i1 + 1.months
        expect(i3.atZone(TimeZones.UTC).date).toBe(LocalDate(1970,1,31).getOrThrow())
    }
}