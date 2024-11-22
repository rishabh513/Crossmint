package com.rishab.model;

public class Cometh extends CelestialOobject {
    private String direction;

    public Cometh(int row, int column, String direction, String candidateId) {
        super(row, column, candidateId);
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String writeAsJson() {
        return "{ \"row\": " + getRow() + ", \"column\": " + getColumn() + ", \"candidateId\": \"" + getCandidateId() + "\", \"direction\": \"" + getDirection() + "\" }";
    }
}