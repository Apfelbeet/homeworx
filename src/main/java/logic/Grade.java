package logic;

public class Grade {
    public int value;
    public GradeType gradeType;
}

enum GradeType {
    Schulaufgabe, Ausfrage, Kurztest, Test, Ex, Referat, Praesentation, Projekt, PraktischeNote, Unterrichtsbeitrag
}
