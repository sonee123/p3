package rightchamps.modal;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {
    Map<String, Float> base;
    boolean sortType;

    public ValueComparator(Map<String, Float> base, boolean sortType) {
        this.base = base;
        this.sortType = sortType;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if(sortType) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        } else{
            if (base.get(a) <= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
