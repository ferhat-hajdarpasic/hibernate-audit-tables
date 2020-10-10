package fred;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;

@Entity
@Audited
@Table(name="PVS_CONTRIBUTION")
@AuditTable(value = "PVS_CONTRIBUTION_HISTORY")
public class PvsContribution {
    private Long pvsContributionId;
    private Date whenCreated;

    private Map<String, MaybeAmount> customFields = new HashMap();

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="PVS_CONTRIBUTION_CUSTOM_FIELDS", joinColumns = {@JoinColumn(name = "entity_ref_id", referencedColumnName = "PVS_CONTRIBUTION_ID")})
    @AuditJoinTable(name="PVS_CONTRIBUTION_CUSTOM_FIELDS_HISTORY")
    @MapKeyColumn(name = "ID")
    public Map<String, MaybeAmount> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, MaybeAmount> customFields) {
        this.customFields = customFields;
    }

    @Id
    @GeneratedValue(generator = "PVS_CONTR_ID_SEQ")
    @Column(name="PVS_CONTRIBUTION_ID", nullable=false, precision=22, scale=0)
    public Long getPvsContributionId() {
        return pvsContributionId;
    }

    public void setPvsContributionId(Long pvsContributionId) {
        this.pvsContributionId = pvsContributionId;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }
}
