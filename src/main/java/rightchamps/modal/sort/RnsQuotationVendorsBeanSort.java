package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsBean;
import rightchamps.modal.RnsQuotationVendorsFullDetails;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RnsQuotationVendorsBeanSort implements Comparator<RnsQuotationVendorsBean> {
    private List<Comparator<RnsQuotationVendorsBean>> listComparators;

    public RnsQuotationVendorsBeanSort(Comparator<RnsQuotationVendorsBean>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    public int compare(RnsQuotationVendorsBean o1, RnsQuotationVendorsBean o2) {
        for (Comparator<RnsQuotationVendorsBean> comparator : listComparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    public List<Comparator<RnsQuotationVendorsBean>> getListComparators() {
        return listComparators;
    }
    public void setListComparators(List<Comparator<RnsQuotationVendorsBean>> listComparators) {
        this.listComparators = listComparators;
    }
}
