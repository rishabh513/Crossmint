package com.rishab.model;

public class Polyanet extends CelestialOobject {
    public Polyanet(int row, int column, String candidateId) {
        super(row, column, candidateId);
    }

    @Override
    public String writeAsJson() {
        return "{ \"row\": " + getRow() + ", \"column\": " + getColumn() + ", \"candidateId\": \"" + getCandidateId() + "\" }";
    }
}
