package com.example.pakmedicine;

public class FormulaModel {
    int fid;
    String name,overview,effects,risk,warning,contradiction,dosage;

    public FormulaModel(int fid, String name, String overview, String effects, String risk, String warning, String contradiction, String dosage) {
        this.fid = fid;
        this.name = name;
        this.overview = overview;
        this.effects = effects;
        this.risk = risk;
        this.warning = warning;
        this.contradiction = contradiction;
        this.dosage = dosage;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getContradiction() {
        return contradiction;
    }

    public void setContradiction(String contradiction) {
        this.contradiction = contradiction;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
