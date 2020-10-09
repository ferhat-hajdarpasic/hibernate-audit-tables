package fred;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name="pvs_fred")
@AuditTable("pvs_fred_history")
@Audited
public class Author {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;


    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="pvs_fred_custom_fields")
    // // @MapKeyColumn(name = "ID")
    // // @Column(name = "fred_fields")
    private Map<String, MaybeMoneyAmount> customFields = new HashMap();
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Map<String, MaybeMoneyAmount> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, MaybeMoneyAmount> customFields) {
        this.customFields = customFields;
    }
}
