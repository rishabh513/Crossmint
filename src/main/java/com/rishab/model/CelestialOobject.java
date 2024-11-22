package com.rishab.model;

public class CelestialOobject {
    private int row;
    private int column;
    private String candidateId;

    public CelestialOobject(int row, int column, String candidateId) {
        this.row = row;
        this.column = column;
        this.candidateId = candidateId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String writeAsJson() {
        return "{ \"row\": " + getRow() + ", \"column\": " + getColumn() + ", \"candidateId\": \"" + getCandidateId() + "\" }";
    }
}