package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsBean;

import java.util.Comparator;

public class RnsQuotationVendorsBeanByVendorName implements Comparator<RnsQuotationVendorsBean> {
    public int compare(RnsQuotationVendorsBean o1, RnsQuotationVendorsBean o2) {
        // TODO Auto-generated method stub
        if (o1.getVendor() != null && o2.getVendor() != null && o1.getVendor().getVendorName() != null && o2.getVendor().getVendorName() != null) {
            return o1.getVendor().getVendorName().compareTo(o2.getVendor().getVendorName());
        } else {
            return 0;
        }
    }
}
