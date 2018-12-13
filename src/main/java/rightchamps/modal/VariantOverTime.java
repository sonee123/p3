package rightchamps.modal;

public class VariantOverTime {
    private Long overTime;
    private Long lotNo;

    public VariantOverTime(Long overTime, Long lotNo) {
        this.overTime = overTime;
        this.lotNo = lotNo;
    }

    public VariantOverTime(Long overTime) {
        this.overTime = overTime;
    }

    public Long getOverTime() {
        return overTime;
    }

    public void setOverTime(Long overTime) {
        this.overTime = overTime;
    }

    public Long getLotNo() {
        return lotNo;
    }

    public void setLotNo(Long lotNo) {
        this.lotNo = lotNo;
    }
}
