package rightchamps.modal;

import rightchamps.domain.Message;
import rightchamps.domain.RnsQuotationVariant;

import java.io.Serializable;
import java.util.List;

public class DashboardBean implements Serializable {
    private Long id;
    private String monthYear;
    private Long catgCode;
    private Long totalProject;
    private Long totalRfq;
    private Long totalRfb;
    private Long openRfq;
    private Long closedRfq;
    private Long openRfb;
    private Long closedRfb;
    private List<Long> biddingList;
    private List<LotBean> lotList;
    private Long selectedBid;
    private Long selectedLot;
    private RnsQuotationVariant rnsQuotationVariant;
    private List<Message> messageBeansTo;
    private List<Message> messageBeans;
    private List<VndrPriceCustom> pricesList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public Long getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(Long catgCode) {
        this.catgCode = catgCode;
    }

    public Long getTotalProject() {
        return totalProject;
    }

    public void setTotalProject(Long totalProject) {
        this.totalProject = totalProject;
    }

    public Long getTotalRfq() {
        return totalRfq;
    }

    public void setTotalRfq(Long totalRfq) {
        this.totalRfq = totalRfq;
    }

    public Long getTotalRfb() {
        return totalRfb;
    }

    public void setTotalRfb(Long totalRfb) {
        this.totalRfb = totalRfb;
    }

    public Long getOpenRfq() {
        return openRfq;
    }

    public void setOpenRfq(Long openRfq) {
        this.openRfq = openRfq;
    }

    public Long getClosedRfq() {
        return closedRfq;
    }

    public void setClosedRfq(Long closedRfq) {
        this.closedRfq = closedRfq;
    }

    public Long getOpenRfb() {
        return openRfb;
    }

    public void setOpenRfb(Long openRfb) {
        this.openRfb = openRfb;
    }

    public Long getClosedRfb() {
        return closedRfb;
    }

    public void setClosedRfb(Long closedRfb) {
        this.closedRfb = closedRfb;
    }

    public List<Long> getBiddingList() {
        return biddingList;
    }

    public void setBiddingList(List<Long> biddingList) {
        this.biddingList = biddingList;
    }

    public Long getSelectedBid() {
        return selectedBid;
    }

    public void setSelectedBid(Long selectedBid) {
        this.selectedBid = selectedBid;
    }

    public List<Message> getMessageBeans() {
        return messageBeans;
    }

    public void setMessageBeans(List<Message> messageBeans) {
        this.messageBeans = messageBeans;
    }

    public List<LotBean> getLotList() {
        return lotList;
    }

    public void setLotList(List<LotBean> lotList) {
        this.lotList = lotList;
    }

    public Long getSelectedLot() {
        return selectedLot;
    }

    public void setSelectedLot(Long selectedLot) {
        this.selectedLot = selectedLot;
    }

    public List<VndrPriceCustom> getPricesList() {
        return pricesList;
    }

    public void setPricesList(List<VndrPriceCustom> pricesList) {
        this.pricesList = pricesList;
    }

    public RnsQuotationVariant getRnsQuotationVariant() {
        return rnsQuotationVariant;
    }

    public void setRnsQuotationVariant(RnsQuotationVariant rnsQuotationVariant) {
        this.rnsQuotationVariant = rnsQuotationVariant;
    }

    public List<Message> getMessageBeansTo() { return messageBeansTo; }

    public void setMessageBeansTo(List<Message> messageBeansTo) { this.messageBeansTo = messageBeansTo; }
}
