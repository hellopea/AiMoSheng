package com.app.aimosheng.utils;

/**
 * 服务端返回结果
 * @param <T>
 */

public class JsonResult<T> {

    private String msg;
    private int result;
    private int Quantity;
    private int TrueQuantity;
    private int Oprationstate;
    private int ShipCustState;
    private String PartNo;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTrueQuantity() {
        return TrueQuantity;
    }

    public void setTrueQuantity(int trueQuantity) {
        TrueQuantity = trueQuantity;
    }

    public int getOprationstate() {
        return Oprationstate;
    }

    public int getShipCustState() {
        return ShipCustState;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public void setOprationstate(int oprationstate) {
        Oprationstate = oprationstate;
    }

    public void setShipCustState(int shipCustState) {
        ShipCustState = shipCustState;
    }
}

