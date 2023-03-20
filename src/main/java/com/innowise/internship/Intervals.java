package com.innowise.internship;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Intervals {

    private final List<String> fullNoteList     //For validation purposes only
        = List.of("Cbb", "Cb", "C", "C#", "C##",
        "Dbb", "Db", "D", "D#", "D##",
        "Ebb", "Eb", "E", "E#", "E##",
        "Fbb", "Fb", "F", "F#", "F##",
        "Gbb", "Gb", "G", "G#", "G##",
        "Abb", "Ab", "A", "A#", "A##",
        "Bbb", "Bb", "B", "B#", "B##");

    private final List<String> alterationSignList = List.of("bb", "b", "", "#", "##");

    private final List<String> intervalList
        = List.of("m2", "M2", "m3", "M3", "P4", "", "P5", "m6", "M6", "m7", "M7", "P8");
                            //Empty element serves to save the semitone sequence

    public String intervalConstruction(String[] args) {
        intervalConstructionArgsValidation(args);

        var interval = args[0];
        var initNote = args[1];
        var orderCoefficient = args.length > 2 && args[2].equals("dsc") ? -1 : 1; //Coefficient to set calculation direction

        var resultNote = countResultNote(initNote, interval, orderCoefficient);
        var distance = countSemitones(initNote, resultNote, orderCoefficient);

        var resultAlterationSign
            = countResultAlterationSign(initNote, interval, distance, orderCoefficient);

        return resultNote + resultAlterationSign;
    }

    public String intervalIdentification(String[] args) {
        intervalIdentificationArgsValidation(args);

        var firstNote = args[0];
        var secondNote = args[1];
        var orderCoefficient = args.length > 2 && args[2].equals("dsc") ? -1 : 1;

        var distance = countSemitones(firstNote, secondNote, orderCoefficient);

        var firstNoteAltValue = alterationSignList.indexOf(firstNote.substring(1));
        var secondNoteAltValue = alterationSignList.indexOf(secondNote.substring(1));
        var alterationDistance = secondNoteAltValue - firstNoteAltValue;

        return intervalList.get(distance + orderCoefficient * alterationDistance - 1);
    }

    private int countSemitones(String firstNote, String secondNote, int orderCoefficient) {
        var realNoteDistance = "C-D-EF-G-A-B";
        var currentPos = realNoteDistance.indexOf(firstNote.charAt(0));

        var semitoneCount = 0;
        while (realNoteDistance.charAt(currentPos) != secondNote.charAt(0)) {
            currentPos += orderCoefficient;
            currentPos %= realNoteDistance.length();
            if (currentPos < 0) {
                currentPos = realNoteDistance.length() - 1;
            }

            semitoneCount++;
        }
        return semitoneCount;
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

    private void intervalConstructionArgsValidation(String[] args) {
        if (args == null) {
            throw new RuntimeException("Input array is null");
        } else if (args.length < 2 || args.length > 3) {
            throw new RuntimeException("Illegal number of elements in input array");
        } else if (fullNoteList.stream().noneMatch(note -> note.equals(args[1]))) {
            throw new RuntimeException("Illegal initial note in input array");
        } else if (intervalList.stream().noneMatch(interval -> interval.equals(args[0]))) {
            throw new RuntimeException("Illegal interval value in input array");
        }
    }

    private void intervalIdentificationArgsValidation(String[] args) {
        if (args == null) {
            throw new RuntimeException("Input array is null");
        } else if (args.length < 2 || args.length > 3) {
            throw new RuntimeException("Illegal number of elements in input array");
        } else if (fullNoteList.stream().noneMatch(note -> note.equals(args[0]))
            || fullNoteList.stream().noneMatch(note -> note.equals(args[1]))) {
            throw new RuntimeException("Illegal notes in input array");
        }
    }
}