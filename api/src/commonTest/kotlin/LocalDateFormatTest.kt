import kommander.expect
import krono.LocalDate
import krono.LocalDateTime
import krono.LocalTime
import krono.Patterns
import kotlin.test.Test

class LocalDateFormatTest {
    @Test
    fun should_parse_string_to_local_date() {
        val date = LocalDate("2022-04-01").getOrThrow()
        expect(date.format("{DD}/{MM}/{YYYY}")).toBe("01/04/2022")
    }

    @Test
    fun should_parse_full_iso_string_to_local_date() {
        val date = LocalDate("2022-04-01T14:00").getOrThrow()
        expect(date.format("{DD}/{MM}/{YYYY}")).toBe("01/04/2022")
    }

    @Test
    fun should_format_the_time_part_of_the_local_date_time_properly() {
        val date = LocalDateTime("2022-04-01T14:00").getOrThrow()
        expect(date.format("{hh}:{mm}{ampm}")).toBe("02:00pm")
    }

    @Test
    fun should_format_the_am_pm_time_with_the_correct_casing() {
        val date = LocalDateTime("2022-04-01T14:00").getOrThrow()
        expect(date.format("{hh}:{mm}{ampm}")).toBe("02:00pm")
        expect(date.format("{hh}:{mm}{AMPM}")).toBe("02:00PM")
    }

    @Test
    fun should_format_a_local_date_time() {
        val date = LocalDateTime("2022-04-01T14:00").getOrThrow()
        expect(date.format("{hh}:{mm}{ampm}")).toBe("02:00pm")
        expect(date.format("{hh}:{mm}{AMPM}")).toBe("02:00PM")
        expect(date.format("{D}{th} {MMM} {YYYY}, {hh}:{mm}")).toBe("1st Apr 2022, 02:00")
    }

    @Test
    fun should_format_to_the_iso_time() {
        val time = LocalTime(3, 30).getOrThrow()
        expect(time.format(Patterns.ISO_TIME)).toBe("03:30:00")
    }
}