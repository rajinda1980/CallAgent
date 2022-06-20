package com.codechallenge.callapp.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "transferLocations")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferLocations {

    @XmlElement(name = "transferType")
    private List<TransferType> transferType;

    public List<TransferType> getTransferType() {
        return transferType;
    }

    public void setTransferType(List<TransferType> transferType) {
        this.transferType = transferType;
    }
}
