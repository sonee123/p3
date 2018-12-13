package rightchamps.modal;

public class LotBean {
    private Long id;
    private String variantName;

    public LotBean() {
    }

    public LotBean(Long id, String variantName) {
        this.id = id;
        this.variantName = variantName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }
}
