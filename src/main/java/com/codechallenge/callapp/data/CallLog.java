package com.codechallenge.callapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "CALL_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "SEQ_CALL_LOG", allocationSize = 1)
    private long id;

    @Column(name = "TRANSFER_TYPE_NAME")
    private String transferTypeName;

    @Column(name = "CREATED_DATE_TIME")
    private LocalDateTime createdDateTime;

    @ManyToOne
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "LOCATION_ID")
    private TransactionLocation transactionLocation;

    public CallLog(String transferTypeName, LocalDateTime createdDateTime, TransactionLocation location) {
        this.transferTypeName = transferTypeName;
        this.createdDateTime = createdDateTime;
        this.transactionLocation = location;
    }
}
