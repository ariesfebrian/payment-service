package id.collect.desk.paymentservice.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class BillingTrxDto {
    private Long idBilling;
    private Long idCollector;
    private float paymentAmount;
    private Date paymentDate;
    private String paymentReceipt;
    private String reason;
    private int isClose;
}
