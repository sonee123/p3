package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsFullDetails;

import java.util.Comparator;

public class RnsQuotationVendorsByVendorName implements Comparator<RnsQuotationVendorsFullDetails> {
    public int compare(RnsQuotationVendorsFullDetails o1, RnsQuotationVendorsFullDetails o2) {
        // TODO Auto-generated method stub
        if (o1.getVendor() != null && o2.getVendor() != null && o1.getVendor().getVendorName() != null && o2.getVendor().getVendorName() != null) {
            return o1.getVendor().getVendorName().compareTo(o2.getVendor().getVendorName());
        } else {
            return 0;
        }
    }
}
