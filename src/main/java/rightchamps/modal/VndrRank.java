package rightchamps.modal;

import java.time.Instant;

public class VndrRank {
    private Long id;
    private String vendorCode;
    private Long rank;
    private Float price;
    private Instant createdOn;
    private Integer revision;
    private Float highestPrice;
    private String message;
    private String cvendorCode;

    public VndrRank(Long rank, Float price, Instant createdOn, Integer revision) {
        this.rank = rank;
        this.price = price;
        this.createdOn = createdOn;
        this.revision = revision;
    }

    public VndrRank(Long id, String vendorCode, Long rank, Float price, Instant createdOn, Integer revision, Float highestPrice, String message, String cvendorCode) {
        this.id = id;
        this.vendorCode = vendorCode;
        this.rank = rank;
        this.price = price;
        this.createdOn = createdOn;
        this.revision = revision;
        this.highestPrice = highestPrice;
        this.message = message;
        this.cvendorCode = cvendorCode;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Float highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getCvendorCode() { return cvendorCode; }

    public void setCvendorCode(String cvendorCode) { this.cvendorCode = cvendorCode; }
}
