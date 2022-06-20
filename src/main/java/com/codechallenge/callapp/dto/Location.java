package com.codechallenge.callapp.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Location {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "number")
    private String number;
    @XmlElement(name = "weight")
    private int weight;
    @XmlElement(name = "enabled")
    private boolean enabled;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return "Number : " + number + ", Weight : " + weight + ", Enabled : " + enabled;
    }
}
