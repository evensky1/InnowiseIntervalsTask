package com.innowise.internship;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Intervals {

    private final List<String> fullNoteList
        = List.of("Cbb", "Cb", "C", "C#", "C##",
        "Dbb", "Db", "D", "D#", "D##",
        "Ebb", "Eb", "E", "E#", "E##",
        "Fbb", "Fb", "F", "F#", "F##",
        "Gbb", "Gb", "G", "G#", "G##",
        "Abb", "Ab", "A", "A#", "A##",
        "Bbb", "Bb", "B", "B#", "B##");

    private final List<String> reducedNoteList
        = List.of("Cb", "C", "C#",
        "Db", "D", "D#",
        "Eb", "E", "E#",
        "Fb", "F", "F#",
        "Gb", "G", "G#",
        "Ab", "A", "A#",
        "Bb", "B", "B#");

    private final List<String> alterationSignList = List.of("bb", "b", "", "#", "##");

    private final List<String> intervalList
        = List.of("m2", "M2", "m3", "M3", "P4", "", "P5", "m6", "M6", "m7", "M7", "P8");

    public String intervalConstruction(String[] args) {
        var interval = args[0];
        var initNote = args[1];
        var orderCoefficient = args.length > 2 && args[2].equals("dsc") ? -1 : 1;

        var resultNote = countResultNote(initNote, interval, orderCoefficient);
        var distance = countDistance(initNote, resultNote, orderCoefficient);

        var resultAlterationSign
            = countResultAlterationSign(initNote, interval, distance, orderCoefficient);

        return resultNote + resultAlterationSign;
    }

    public String intervalIdentification(String[] args) {
        var firstNote = args[0];
        var secondNote = args[1];
        var orderCoefficient = args.length > 2 && args[2].equals("dsc") ? -1 : 1;

        var distance = countDistance(firstNote, secondNote, orderCoefficient);
        var alterationDistance =
            alterationSignList.indexOf(secondNote.substring(1)) -
            alterationSignList.indexOf(firstNote.substring(1));

        return intervalList.get(distance + orderCoefficient * alterationDistance - 1);
    }

    private int countDistance(String firstNote, String secondNote, int orderCoefficient) {
        var realNoteDistance = "C-D-EF-G-A-B";
        var currentPos = realNoteDistance.indexOf(firstNote.charAt(0));

        var distance = 0;
        while (realNoteDistance.charAt(currentPos) != secondNote.charAt(0)) {
            currentPos += orderCoefficient;
            currentPos %= realNoteDistance.length();
            if (currentPos < 0) {
                currentPos = realNoteDistance.length() - 1;
            }

            distance++;
        }
        return distance;
    }

    private String countResultNote(String initNote, String interval, int orderCoefficient) {
        var notes = "CDEFGAB";
        var resultNotePos = notes.indexOf(initNote.charAt(0));
        var shiftDegree = Character.getNumericValue(interval.charAt(1)) - 1;

        resultNotePos += shiftDegree * orderCoefficient;
        resultNotePos %= notes.length();
        if (resultNotePos < 0) {
            resultNotePos += notes.length();
        }

        return notes.substring(resultNotePos, resultNotePos + 1);
    }

    private String countResultAlterationSign(
        String initNote,
        String interval,
        int distance,
        int orderCoefficient
    ) {
        var semitones = intervalList.indexOf(interval) + 1;
        var currentAlteration = alterationSignList.indexOf(initNote.substring(1));
        var newAlteration = currentAlteration + orderCoefficient * (semitones - distance);

        return alterationSignList.get(newAlteration);
    }
}