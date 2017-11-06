package party.threebody.skean.jdbc.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Car {

    private String normProp;
    private String noGetterProp;
    private String noSetterProp;
    private String noGetSetProp;
    private int[] intArr;

    private BigDecimal intx;
    private LocalDateTime ldt;

    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Car(String normProp, String noGetterProp, String noSetterProp, String noGetSetProp) {
        this.normProp = normProp;
        this.noGetterProp = noGetterProp;
        this.noSetterProp = noSetterProp;
        this.noGetSetProp = noGetSetProp;
    }

    public int[] getIntArr() {
        return intArr;
    }

    public void setIntArr(int[] intArr) {
        this.intArr = intArr;
    }

    public String getNormProp() {
        return normProp;
    }

    public void setNormProp(String normProp) {
        this.normProp = normProp;
    }


    public void setNoGetterProp(String noGetterProp) {
        this.noGetterProp = noGetterProp;
    }

    public String getNoSetterProp() {
        return noSetterProp;
    }

    public String getCalcedProp(){
        return "calced";
    }

    public void setNoExistProp(int x){

    }

    public BigDecimal getIntx() {
        return intx;
    }

    public void setIntx(BigDecimal intx) {
        this.intx = intx;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }
}

