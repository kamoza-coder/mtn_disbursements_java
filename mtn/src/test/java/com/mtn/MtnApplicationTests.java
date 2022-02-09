package com.mtn;

import com.mtn.dto.Payer;
import com.mtn.dto.MakeTransfer;
import com.mtn.responses.AccountBalanceResponse;
import com.mtn.responses.AccountHolderResponse;
import com.mtn.responses.AcountHolderInfoResponse;
import com.mtn.responses.GetTransferResponse;
import com.mtn.services.MTNServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MtnApplicationTests {

    private MTNServices mtnServices;

    @Autowired
    public void setMtnServices(MTNServices mtnServices) {
        this.mtnServices = mtnServices;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testGetToken() {

        String response = MTNServices.getToken();
        assertThat(response).isNotNull();
    }

    @Test

    void testMakeTransfer() {
        Payer payee = new Payer("MSISDN", "260359499393");
        MakeTransfer request = new MakeTransfer("30.0", "EUR", "260359499393", payee, "Payment Request", "pay within 72hrs");
        String response = mtnServices.makeTransfer(request,"6f2512f9-79d4-4ba0-a00f-29bb2c669578");
        assertThat(response).isEqualTo("202");
    }

    @Test
    void testTransferStatus() {
        GetTransferResponse response = mtnServices.transferStatus("6f2611f9-49b4-4ba0-a00f-69bb2c6d9572");
        assertThat(response.getStatus()).isEqualTo("SUCCESSFUL");
    }
    @Test
    void testGetAccountBalance() {
        AccountBalanceResponse response = mtnServices.getAccountBalance();
        assertThat(response.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void testGetAccountHolderInfo() {
        AcountHolderInfoResponse response = mtnServices.getAccountHolderInfo("260359499393");
        assertThat(response).isNotNull();
    }

    @Test
    void testGetAccountHolderStatus() {
        AccountHolderResponse response = mtnServices.getAccountHolderStatus("260359499393");
        assertThat(response.isResult()).isEqualTo(true);
    }

}
