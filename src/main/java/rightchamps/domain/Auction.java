package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import rightchamps.domain.enumeration.AuctionEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A Auction.
 */
@Entity
@Table(name = "auction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "auction_title")
    private String auctionTitle;

    @Column(name = "auction_description")
    private String auctionDescription;

    @Column(name = "publish_time")
    private Instant publishTime;

    @Column(name = "bidding_start_time")
    private Instant biddingStartTime;

    @Column(name = "lot_running_time")
    private Integer lotRunningTime;

    @Column(name = "bid_rank_over_time")
    private Integer bidRankOverTime;

    @Column(name = "bid_time_for_overtime_start")
    private Integer bidTimeForOvertimeStart;

    @Column(name = "overtime_period")
    private Integer overtimePeriod;

    @Column(name = "show_lead_bid_to_all")
    private Boolean showLeadBidToAll;

    @Enumerated(EnumType.STRING)
    @Column(name = "event")
    private AuctionEvent event;

    @Column(name = "time_between_lots")
    private Integer timeBetweenLots;

    @Column(name = "min_price_changes")
    private Float minPriceChanges;

    @Column(name = "currency")
    private String currency;

    @Column(name = "show_ranks")
    private Boolean showRanks;
    
    @Column(name = "allow_tie_bids")
    private Boolean allowTieBids;

     @Column(name = "quotation_id")
    private Long quotationId;


    @OneToOne
    @JoinColumn(unique = true,insertable=false, updatable=false)
    private RnsQuotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public Auction auctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
        return this;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public String getAuctionDescription() {
        return auctionDescription;
    }

    public Auction auctionDescription(String auctionDescription) {
        this.auctionDescription = auctionDescription;
        return this;
    }

    public void setAuctionDescription(String auctionDescription) {
        this.auctionDescription = auctionDescription;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public Auction publishTime(Instant publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
    }

    public Instant getBiddingStartTime() {
        return biddingStartTime;
    }

    public Auction biddingStartTime(Instant biddingStartTime) {
        this.biddingStartTime = biddingStartTime;
        return this;
    }

    public void setBiddingStartTime(Instant biddingStartTime) {
        this.biddingStartTime = biddingStartTime;
    }

    public Integer getLotRunningTime() {
        return lotRunningTime;
    }

    public Auction lotRunningTime(Integer lotRunningTime) {
        this.lotRunningTime = lotRunningTime;
        return this;
    }

    public void setLotRunningTime(Integer lotRunningTime) {
        this.lotRunningTime = lotRunningTime;
    }

    public Integer getBidRankOverTime() {
        return bidRankOverTime;
    }

    public Auction bidRankOverTime(Integer bidRankOverTime) {
        this.bidRankOverTime = bidRankOverTime;
        return this;
    }

    public void setBidRankOverTime(Integer bidRankOverTime) {
        this.bidRankOverTime = bidRankOverTime;
    }

    public Integer getBidTimeForOvertimeStart() {
        return bidTimeForOvertimeStart;
    }

    public Auction bidTimeForOvertimeStart(Integer bidTimeForOvertimeStart) {
        this.bidTimeForOvertimeStart = bidTimeForOvertimeStart;
        return this;
    }

    public void setBidTimeForOvertimeStart(Integer bidTimeForOvertimeStart) {
        this.bidTimeForOvertimeStart = bidTimeForOvertimeStart;
    }

    public Integer getOvertimePeriod() {
        return overtimePeriod;
    }

    public Auction overtimePeriod(Integer overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
        return this;
    }

    public void setOvertimePeriod(Integer overtimePeriod) {
        this.overtimePeriod = overtimePeriod;
    }

    public Boolean isShowLeadBidToAll() {
        return showLeadBidToAll;
    }

    public Auction showLeadBidToAll(Boolean showLeadBidToAll) {
        this.showLeadBidToAll = showLeadBidToAll;
        return this;
    }

    public void setShowLeadBidToAll(Boolean showLeadBidToAll) {
        this.showLeadBidToAll = showLeadBidToAll;
    }

    public AuctionEvent getEvent() {
        return event;
    }

    public Auction event(AuctionEvent event) {
        this.event = event;
        return this;
    }

    public void setEvent(AuctionEvent event) {
        this.event = event;
    }
    
    

    public Boolean getAllowTieBids() {
		return allowTieBids;
	}

	public void setAllowTieBids(Boolean allowTieBids) {
		this.allowTieBids = allowTieBids;
	}

	public Integer getTimeBetweenLots() {
        return timeBetweenLots;
    }

    public Auction timeBetweenLots(Integer timeBetweenLots) {
        this.timeBetweenLots = timeBetweenLots;
        return this;
    }

    public void setTimeBetweenLots(Integer timeBetweenLots) {
        this.timeBetweenLots = timeBetweenLots;
    }

    public Float getMinPriceChanges() {
        return minPriceChanges;
    }

    public Auction minPriceChanges(Float updatedMinPriceChanges) {
        this.minPriceChanges = updatedMinPriceChanges;
        return this;
    }

    public void setMinPriceChanges(Float minPriceChanges) {
        this.minPriceChanges = minPriceChanges;
    }

    public String getCurrency() {
        return currency;
    }

    public Auction currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean isShowRanks() {
        return showRanks;
    }

    public Auction showRanks(Boolean showRanks) {
        this.showRanks = showRanks;
        return this;
    }

    public void setShowRanks(Boolean showRanks) {
        this.showRanks = showRanks;
    }

    public RnsQuotation getQuotation() {
        return quotation;
    }

    public Auction quotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
        return this;
    }

    public void setQuotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
    }

      public Long getQuotationId() {
        return quotationId;
    }

    public Auction quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auction auction = (Auction) o;
        if (auction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auction{" +
            "id=" + getId() +
            ", auctionTitle='" + getAuctionTitle() + "'" +
            ", auctionDescription='" + getAuctionDescription() + "'" +
            ", publishTime='" + getPublishTime() + "'" +
            ", biddingStartTime='" + getBiddingStartTime() + "'" +
            ", lotRunningTime=" + getLotRunningTime() +
            ", bidRankOverTime=" + getBidRankOverTime() +
            ", bidTimeForOvertimeStart=" + getBidTimeForOvertimeStart() +
            ", overtimePeriod=" + getOvertimePeriod() +
            ", showLeadBidToAll='" + isShowLeadBidToAll() + "'" +
            ", event='" + getEvent() + "'" +
            ", timeBetweenLots=" + getTimeBetweenLots() +
            ", minPriceChanges=" + getMinPriceChanges() +
            ", currency='" + getCurrency() + "'" +
            ", showRanks='" + isShowRanks() + "'" +
            "}";
    }
}
