package com.rishab.model;

public class DeleteRequest {
    private CelestialOobject celestialOobject;
    private String celestialObjectType;

    public DeleteRequest(CelestialOobject celestialOobject, String celestialObjectType) {
        this.celestialOobject = celestialOobject;
        this.celestialObjectType = celestialObjectType;
    }

    public CelestialOobject getCelestialOobject() {
        return celestialOobject;
    }

    public void setCelestialOobject(CelestialOobject celestialOobject) {
        this.celestialOobject = celestialOobject;
    }

    public String getCelestialObjectType() {
        return celestialObjectType;
    }

    public void setCelestialObjectType(String celestialObjectType) {
        this.celestialObjectType = celestialObjectType;
    }
}