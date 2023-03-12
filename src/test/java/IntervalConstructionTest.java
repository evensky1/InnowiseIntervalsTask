import com.innowise.internship.Intervals;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IntervalConstructionTest {

    static Stream<Arguments> intervalConstructorCorrectDataStream() {
        return Stream.of(
            Arguments.of("M2", "C", "asc", "D"),
            Arguments.of("P5", "B", "asc", "F#"),
            Arguments.of("m2", "Bb", "dsc", "A"),
            Arguments.of("M3", "Cb", "dsc", "Abb"),
            Arguments.of("P4", "G#", "dsc", "D#"),
            Arguments.of("m3", "B", "dsc", "G#"),
            Arguments.of("m2", "Fb", "asc", "Gbb"),
            Arguments.of("M2", "E#", "dsc", "D#"),
            Arguments.of("P4", "E", "dsc", "B"),
            Arguments.of("m2", "D#", "asc", "E"),
            Arguments.of("M7", "G", "asc", "F#")
        );
    }

    @ParameterizedTest
    @MethodSource("intervalConstructorCorrectDataStream")
    void intervalConstructorCorrectDataTest(
        String interval,
        String initNote,
        String order,
        String expected
    ) {
        var args = new String[]{interval, initNote, order};
        var actual = Intervals.intervalConstruction(args);

        Assertions.assertEquals(expected, actual);
    }
}
