package rightchamps.modal.sort;

import rightchamps.modal.RnsQuotationVendorsFullDetails;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RnsQuotationVendorsSort implements Comparator<RnsQuotationVendorsFullDetails> {
    private List<Comparator<RnsQuotationVendorsFullDetails>> listComparators;

    public RnsQuotationVendorsSort(Comparator<RnsQuotationVendorsFullDetails>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    public int compare(RnsQuotationVendorsFullDetails o1, RnsQuotationVendorsFullDetails o2) {
        for (Comparator<RnsQuotationVendorsFullDetails> comparator : listComparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    public List<Comparator<RnsQuotationVendorsFullDetails>> getListComparators() {
        return listComparators;
    }
    public void setListComparators(List<Comparator<RnsQuotationVendorsFullDetails>> listComparators) {
        this.listComparators = listComparators;
    }
}
