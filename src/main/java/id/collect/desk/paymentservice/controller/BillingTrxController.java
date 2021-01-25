package id.collect.desk.paymentservice.controller;

import id.collect.desk.paymentservice.entity.Billing;
import id.collect.desk.paymentservice.entity.BillingTrx;
import id.collect.desk.paymentservice.entity.dto.BillingTrxDto;
import id.collect.desk.paymentservice.repo.BillingRepository;
import id.collect.desk.paymentservice.repo.BillingTrxRepository;
import id.collect.desk.paymentservice.repo.CollectorRepository;
import id.collect.desk.paymentservice.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/billing-trx")
public class BillingTrxController {

    @Autowired
    private BillingTrxRepository billingTrxRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        Map<String, Object> objectMap = new HashMap<>();
        List<BillingTrx> billingTrxList = (List<BillingTrx>) billingTrxRepository.findAll();
        objectMap.put("data", billingTrxList);

        Response response = new Response();
        response.setMessage("Load List Billing Transaction Successfully");
        response.setService(nameofCurrMethod);
        response.setData(objectMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/payment/{idBilling}")
    public ResponseEntity<?> payment(@RequestBody BillingTrxDto billingTrxDto, @PathVariable Long idBilling) {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        Response response = new Response();

        try {
            Optional<Billing> billing = billingRepository.findById(idBilling);
            if (billing.isPresent()) {
                BillingTrx billingTrx = new BillingTrx();
                billingTrx.setBilling(billing.get());
                billingTrx.setCollector(collectorRepository.findById(billingTrxDto.getIdCollector()).get());
                billingTrx.setPaymentDate(billingTrxDto.getPaymentDate());
                billingTrx.setPaymentAmount(billingTrxDto.getPaymentAmount());
                billingTrx.setPaymentReceipt(billingTrxDto.getPaymentReceipt());
                billingTrx.setReason(billingTrxDto.getReason());

                billingTrxRepository.save(billingTrx);

                //Update Status Billing and Payment Amount
                billing.get().setBillingAmount(billingTrxDto.getPaymentAmount() - billing.get().getBillingAmount());
                billing.get().setIsClose(billingTrxDto.getIsClose());
                billingRepository.save(billing.get());

                response.setMessage("Billing Transaction Successfully");
                response.setData(billingTrx);
            } else {
                response.setMessage("Billing Not Found");
            }
            response.setService(nameofCurrMethod);

        } catch (Exception e) {
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setService(nameofCurrMethod);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
