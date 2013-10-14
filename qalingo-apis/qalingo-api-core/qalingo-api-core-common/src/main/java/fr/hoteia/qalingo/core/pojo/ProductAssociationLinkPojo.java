package fr.hoteia.qalingo.core.pojo;

import fr.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;
import org.codehaus.jackson.annotate.JsonBackReference;

import java.util.Date;

public class ProductAssociationLinkPojo {

    private Long id;
    private int version;
    private int rankPosition;
    private Date dateStart;
    private Date dateEnd;
    private Date dateCreate;
    private Date dateUpdate;
    private ProductAssociationLinkType type;
    private ProductMarketingPojo productMarketing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(int rankPosition) {
        this.rankPosition = rankPosition;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public ProductAssociationLinkType getType() {
        return type;
    }

    public void setType(ProductAssociationLinkType type) {
        this.type = type;
    }

    @JsonBackReference
    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
    }
}
