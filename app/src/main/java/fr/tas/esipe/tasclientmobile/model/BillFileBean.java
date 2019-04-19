package fr.tas.esipe.tasclientmobile.model;

public class BillFileBean {

    String billName;

    public BillFileBean(String billName) {
        this.billName = billName;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
}