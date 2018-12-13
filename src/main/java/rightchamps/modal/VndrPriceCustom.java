package rightchamps.modal;

import java.time.Instant;

public class VndrPriceCustom {
    private Long id;
    private String title;
    private String vendorCode;
    private Instant date;
    private  Float value;
    private boolean surrogate;
    private String firstName;
    private String lastName;
    private String vendorName;

    public VndrPriceCustom(Long id,String title, String vendorCode, Instant date, Float value, boolean surrogate) {
        this.id = id;
        this.title = title;
        this.vendorCode = vendorCode;
        this.date = date;
        this.value = value;
        this.surrogate = surrogate;
    }

    public VndrPriceCustom(Long id,String title, String vendorCode, Instant date, Float value, boolean surrogate, String firstName, String LastName, String vendorName) {
        this.id = id;
        this.title = title;
        this.vendorCode = vendorCode;
        this.date = date;
        this.value = value;
        this.surrogate = surrogate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vendorName = vendorName;
    }

    public VndrPriceCustom() {

    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getVendorCode() { return vendorCode; }

    public void setVendorCode(String vendorCode) { this.vendorCode = vendorCode; }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public boolean isSurrogate() {
        return surrogate;
    }

    public void setSurrogate(boolean surrogate) {
        this.surrogate = surrogate;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getVendorName() { return vendorName; }

    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
}
