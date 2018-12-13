package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsBean;

import java.util.Comparator;

public class RnsQuotationVendorsBeanByFirstName implements Comparator<RnsQuotationVendorsBean> {
    public int compare(RnsQuotationVendorsBean o1, RnsQuotationVendorsBean o2) {
        // TODO Auto-generated method stub
        if (o1.getVendor() != null && o2.getVendor() != null && o1.getVendor().getFirstName() != null && o2.getVendor().getFirstName() != null) {
            return o1.getVendor().getFirstName().compareTo(o2.getVendor().getFirstName());
        } else {
            return 0;
        }
    }
}
