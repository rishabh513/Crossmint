package com.rishab.model;

public class Soloon extends CelestialOobject {
    private String color;

    public Soloon(int row, int column, String candidateId, String color) {
        super(row, column, candidateId);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String writeAsJson() {
        return "{ \"row\": " + getRow() + ", \"column\": " + getColumn() + ", \"candidateId\": \"" + getCandidateId() + "\", \"color\": \"" + getColor() + "\" }";
    }
}