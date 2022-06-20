package com.codechallenge.callapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSACTION_LOCATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "SEQ_TRANSACTION_LOCATION", allocationSize = 1)
    private long Id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "ENABLED")
    private Boolean enabled;

    public TransactionLocation(String name, String number, Integer weight, Boolean enabled) {
        this.name = name;
        this.number = number;
        this.weight = weight;
        this.enabled = enabled;
    }
}
