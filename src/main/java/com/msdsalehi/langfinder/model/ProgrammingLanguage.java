package com.msdsalehi.langfinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author masoud
 */
public class ProgrammingLanguage implements Serializable, Comparable<ProgrammingLanguage>, Cloneable {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Designed by")
    private String designedBy;
    @JsonProperty("relevancyLevel")
    private Integer relevancyLevel = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesignedBy() {
        return designedBy;
    }

    public void setDesignedBy(String designedBy) {
        this.designedBy = designedBy;
    }

    public Integer getRelevancyLevel() {
        return relevancyLevel;
    }

    public void setRelevancyLevel(Integer relevancyLevel) {
        this.relevancyLevel = relevancyLevel;
    }

    @Override
    public int compareTo(ProgrammingLanguage destinationObj) {
        return destinationObj.getRelevancyLevel() - getRelevancyLevel();
    }

    @Override
    public ProgrammingLanguage clone() throws CloneNotSupportedException {
        ProgrammingLanguage clone = (ProgrammingLanguage) super.clone();
        clone.setName(name);
        clone.setType(type);
        clone.setDesignedBy(designedBy);
        clone.setRelevancyLevel(relevancyLevel);
        return clone;
    }

}
