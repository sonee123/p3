package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsFullDetails;

import java.util.Comparator;

public class RnsQuotationVendorsByFirstName implements Comparator<RnsQuotationVendorsFullDetails> {
    public int compare(RnsQuotationVendorsFullDetails o1, RnsQuotationVendorsFullDetails o2) {
        // TODO Auto-generated method stub
        if (o1.getVendor() != null && o2.getVendor() != null && o1.getVendor().getFirstName() != null && o2.getVendor().getFirstName() != null) {
            return o1.getVendor().getFirstName().compareTo(o2.getVendor().getFirstName());
        } else {
            return 0;
        }
    }
}
