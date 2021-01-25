package id.collect.desk.paymentservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "billing_trx")
public class BillingTrx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_billing")
    @ManyToOne
    private Billing billing;

    @JoinColumn(name = "id_collector")
    @ManyToOne
    private Collector collector;

    @Column(name = "payment_amount")
    private float paymentAmount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_receipt")
    private String paymentReceipt;

    @Column(name = "reason")
    private String reason;
}
