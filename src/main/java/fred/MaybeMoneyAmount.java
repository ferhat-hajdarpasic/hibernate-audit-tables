package fred;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Embeddable
@Audited
// @AuditTable(value = "custom_field_history")
public class MaybeMoneyAmount {
    
    @Column(name = "amount_raw")
    private final String amountRaw;
    @Column(name = "amount_amt")
    private BigDecimal amountAmt;
    
    public MaybeMoneyAmount(String amountRaw) {
        this.amountRaw = amountRaw;
    }

    public MaybeMoneyAmount(BigDecimal amountAmt) {
        super();
        this.amountAmt = amountAmt; 
        this.amountRaw = amountAmt.toPlainString();
    }

    public String getAmountRaw() {
        return amountRaw;
    }

    public BigDecimal getAmountAmt() {
        return amountAmt;
    }

    public void setAmountAmt(BigDecimal amountAmt) {
        this.amountAmt = amountAmt;
    }
}