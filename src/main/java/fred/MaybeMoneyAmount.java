package fred;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MaybeMoneyAmount {
    
    private String amountRaw;
    private BigDecimal amountAmt;
    
    public MaybeMoneyAmount(String amountRaw) {
        this.amountRaw = amountRaw;
    }

    public MaybeMoneyAmount(BigDecimal amountAmt) {
        super();
        this.amountAmt = amountAmt; 
        this.amountRaw = amountAmt.toPlainString();
    }

    @Column(name = "amount_raw", nullable = false)
    public String getAmountRaw() {
        return amountRaw;
    }

    public void setAmountRaw(String amountRaw) {
        this.amountRaw = amountRaw;
    }

    @Column(name = "amount_amt")
    public BigDecimal getAmountAmt() {
        return amountAmt;
    }

    public void setAmountAmt(BigDecimal amountAmt) {
        this.amountAmt = amountAmt;
    }
}