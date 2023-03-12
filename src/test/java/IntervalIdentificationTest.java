import com.innowise.internship.Intervals;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IntervalIdentificationTest {

    static Stream<Arguments> intervalIdentificationCorrectDataStream() {
        return Stream.of(
            Arguments.of("C", "D", "asc", "M2"),
            Arguments.of("B", "F#", "asc", "P5"),
            Arguments.of("Fb", "Gbb", "asc", "m2"),
            Arguments.of("G", "F#", "asc", "M7"),
            Arguments.of("Bb", "A", "dsc", "m2"),
            Arguments.of("Cb", "Abb", "dsc", "M3"),
            Arguments.of("G#", "D#", "dsc", "P4"),
            Arguments.of("E", "B", "dsc", "P4"),
            Arguments.of("E#", "D#", "dsc", "M2"),
            Arguments.of("B", "G#", "dsc", "m3")
        );
    }

    @ParameterizedTest
    @MethodSource("intervalIdentificationCorrectDataStream")
    void intervalIdentificationCorrectDataTest(
        String firstNote,
        String secondNote,
        String order,
        String expected
    ) {
        var args = new String[]{firstNote, secondNote, order};
        var actual = Intervals.intervalIdentification(args);

        Assertions.assertEquals(expected, actual);
    }
}
