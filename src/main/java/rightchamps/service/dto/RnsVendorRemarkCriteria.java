package rightchamps.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the RnsVendorRemark entity. This class is used in RnsVendorRemarkResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /rns-vendor-remarks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RnsVendorRemarkCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter remarkText;

    private StringFilter vendorEmail;

    private StringFilter staffEmail;

    private StringFilter fromEmail;

    private StringFilter toEmail;

    private BooleanFilter read;

    private LongFilter quotationId;

    public RnsVendorRemarkCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRemarkText() {
        return remarkText;
    }

    public void setRemarkText(StringFilter remarkText) {
        this.remarkText = remarkText;
    }

    public StringFilter getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(StringFilter vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public StringFilter getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(StringFilter staffEmail) {
        this.staffEmail = staffEmail;
    }

    public StringFilter getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(StringFilter fromEmail) {
        this.fromEmail = fromEmail;
    }

    public StringFilter getToEmail() {
        return toEmail;
    }

    public void setToEmail(StringFilter toEmail) {
        this.toEmail = toEmail;
    }

    public BooleanFilter getRead() {
        return read;
    }

    public void setRead(BooleanFilter read) {
        this.read = read;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }

    @Override
    public String toString() {
        return "RnsVendorRemarkCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (remarkText != null ? "remarkText=" + remarkText + ", " : "") +
                (vendorEmail != null ? "vendorEmail=" + vendorEmail + ", " : "") +
                (staffEmail != null ? "staffEmail=" + staffEmail + ", " : "") +
                (fromEmail != null ? "fromEmail=" + fromEmail + ", " : "") +
                (toEmail != null ? "toEmail=" + toEmail + ", " : "") +
                (read != null ? "read=" + read + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
            "}";
    }

}
