package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import rightchamps.domain.enumeration.ShowAsTemplate;


/**
 * A Template.
 */
@Entity
@Table(name = "template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="template_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "specification_one")
    private String specificationOne;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_one_show_as")
    private ShowAsTemplate specificationOneShowAs;

    @Column(name = "specification_one_value")
    private String specificationOneValue;

    @Column(name = "specification_one_required")
    private Boolean specificationOneRequired;

    @Column(name = "show_in_rfq_one_required")
    private Boolean showInRfqOneRequired;

    @Column(name = "show_in_auction_one_required")
    private Boolean showInAuctionOneRequired;

    @Column(name = "specification_two")
    private String specificationTwo;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_two_show_as")
    private ShowAsTemplate specificationTwoShowAs;

    @Column(name = "specification_two_value")
    private String specificationTwoValue;

    @Column(name = "specification_two_required")
    private Boolean specificationTwoRequired;

    @Column(name = "show_in_rfq_two_required")
    private Boolean showInRfqTwoRequired;

    @Column(name = "show_in_auction_two_required")
    private Boolean showInAuctionTwoRequired;

    @Column(name = "specification_three")
    private String specificationThree;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_three_show_as")
    private ShowAsTemplate specificationThreeShowAs;

    @Column(name = "specification_three_value")
    private String specificationThreeValue;

    @Column(name = "specification_three_required")
    private Boolean specificationThreeRequired;

    @Column(name = "show_in_rfq_three_required")
    private Boolean showInRfqThreeRequired;

    @Column(name = "show_in_auction_three_required")
    private Boolean showInAuctionThreeRequired;

    @Column(name = "specification_four")
    private String specificationFour;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_four_show_as")
    private ShowAsTemplate specificationFourShowAs;

    @Column(name = "specification_four_value")
    private String specificationFourValue;

    @Column(name = "specification_four_required")
    private Boolean specificationFourRequired;

    @Column(name = "show_in_rfq_four_required")
    private Boolean showInRfqFourRequired;

    @Column(name = "show_in_auction_four_required")
    private Boolean showInAuctionFourRequired;

    @Column(name = "specification_five")
    private String specificationFive;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_five_show_as")
    private ShowAsTemplate specificationFiveShowAs;

    @Column(name = "specification_five_value")
    private String specificationFiveValue;

    @Column(name = "specification_five_required")
    private Boolean specificationFiveRequired;

    @Column(name = "show_in_rfq_five_required")
    private Boolean showInRfqFiveRequired;

    @Column(name = "show_in_auction_five_required")
    private Boolean showInAuctionFiveRequired;

    @Column(name = "specification_six")
    private String specificationSix;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_six_show_as")
    private ShowAsTemplate specificationSixShowAs;

    @Column(name = "specification_six_value")
    private String specificationSixValue;

    @Column(name = "specification_six_required")
    private Boolean specificationSixRequired;

    @Column(name = "show_in_rfq_six_required")
    private Boolean showInRfqSixRequired;

    @Column(name = "show_in_auction_six_required")
    private Boolean showInAuctionSixRequired;

    @Column(name = "specification_seven")
    private String specificationSeven;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_seven_show_as")
    private ShowAsTemplate specificationSevenShowAs;

    @Column(name = "specification_seven_value")
    private String specificationSevenValue;

    @Column(name = "specification_seven_required")
    private Boolean specificationSevenRequired;

    @Column(name = "show_in_rfq_seven_required")
    private Boolean showInRfqSevenRequired;

    @Column(name = "show_in_auction_seven_required")
    private Boolean showInAuctionSevenRequired;

    @Column(name = "specification_eight")
    private String specificationEight;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_eight_show_as")
    private ShowAsTemplate specificationEightShowAs;

    @Column(name = "specification_eight_value")
    private String specificationEightValue;

    @Column(name = "specification_eight_required")
    private Boolean specificationEightRequired;

    @Column(name = "show_in_rfq_eight_required")
    private Boolean showInRfqEightRequired;

    @Column(name = "show_in_auction_eight_required")
    private Boolean showInAuctionEightRequired;

    @Column(name = "specification_nine")
    private String specificationNine;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_nine_show_as")
    private ShowAsTemplate specificationNineShowAs;

    @Column(name = "specification_nine_value")
    private String specificationNineValue;

    @Column(name = "specification_nine_required")
    private Boolean specificationNineRequired;

    @Column(name = "show_in_rfq_nine_required")
    private Boolean showInRfqNineRequired;

    @Column(name = "show_in_auction_nine_required")
    private Boolean showInAuctionNineRequired;

    @Column(name = "specification_ten")
    private String specificationTen;

    @Enumerated(EnumType.STRING)
    @Column(name = "specification_ten_show_as")
    private ShowAsTemplate specificationTenShowAs;

    @Column(name = "specification_ten_value")
    private String specificationTenValue;

    @Column(name = "specification_ten_required")
    private Boolean specificationTenRequired;

    @Column(name = "show_in_rfq_ten_required")
    private Boolean showInRfqTenRequired;

    @Column(name = "show_in_auction_ten_required")
    private Boolean showInAuctionTenRequired;

    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

    @OneToOne
    @JoinColumn(name="created_by", referencedColumnName="login")
    private User user;

     @Column(name = "created_date")
     private Instant createdDate;

    @OneToOne
    @JoinColumn(name="updated_by", referencedColumnName="login")
    private User updatedUser;

    @Column(name = "last_updated_date")
    private Instant lastUpdatedDate;

    @Column(name = "flag")
    private boolean flag = false;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Template templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSpecificationOne() {
        return specificationOne;
    }

    public Template specificationOne(String specificationOne) {
        this.specificationOne = specificationOne;
        return this;
    }

    public void setSpecificationOne(String specificationOne) {
        this.specificationOne = specificationOne;
    }

    public ShowAsTemplate getSpecificationOneShowAs() {
        return specificationOneShowAs;
    }

    public Template specificationOneShowAs(ShowAsTemplate specificationOneShowAs) {
        this.specificationOneShowAs = specificationOneShowAs;
        return this;
    }

    public void setSpecificationOneShowAs(ShowAsTemplate specificationOneShowAs) {
        this.specificationOneShowAs = specificationOneShowAs;
    }

    public String getSpecificationOneValue() {
        return specificationOneValue;
    }

    public Template specificationOneValue(String specificationOneValue) {
        this.specificationOneValue = specificationOneValue;
        return this;
    }

    public void setSpecificationOneValue(String specificationOneValue) {
        this.specificationOneValue = specificationOneValue;
    }

    public Boolean isSpecificationOneRequired() {
        return specificationOneRequired;
    }

    public Template specificationOneRequired(Boolean specificationOneRequired) {
        this.specificationOneRequired = specificationOneRequired;
        return this;
    }

    public void setSpecificationOneRequired(Boolean specificationOneRequired) {
        this.specificationOneRequired = specificationOneRequired;
    }

    public Boolean isShowInRfqOneRequired() {
		return showInRfqOneRequired;
	}

    public Template showInRfqOneRequired(Boolean showInRfqOneRequired) {
    	this.showInRfqOneRequired = showInRfqOneRequired;
		return this;

    }

    public void setShowInRfqOneRequired(Boolean showInRfqOneRequired) {
		this.showInRfqOneRequired = showInRfqOneRequired;
	}

    public Boolean isShowInAuctionOneRequired() {
		return showInAuctionOneRequired;
	}

    public Template showInAuctionOneRequired(Boolean showInAuctionOneRequired) {
    	this.showInAuctionOneRequired = showInAuctionOneRequired;
		return this;

    }
	public void setShowInAuctionOneRequired(Boolean showInAuctionOneRequired) {
		this.showInAuctionOneRequired = showInAuctionOneRequired;
	}

	public String getSpecificationTwo() {
        return specificationTwo;
    }

    public Template specificationTwo(String specificationTwo) {
        this.specificationTwo = specificationTwo;
        return this;
    }

    public void setSpecificationTwo(String specificationTwo) {
        this.specificationTwo = specificationTwo;
    }

    public ShowAsTemplate getSpecificationTwoShowAs() {
        return specificationTwoShowAs;
    }

    public Template specificationTwoShowAs(ShowAsTemplate specificationTwoShowAs) {
        this.specificationTwoShowAs = specificationTwoShowAs;
        return this;
    }

    public void setSpecificationTwoShowAs(ShowAsTemplate specificationTwoShowAs) {
        this.specificationTwoShowAs = specificationTwoShowAs;
    }

    public String getSpecificationTwoValue() {
        return specificationTwoValue;
    }

    public Template specificationTwoValue(String specificationTwoValue) {
        this.specificationTwoValue = specificationTwoValue;
        return this;
    }

    public void setSpecificationTwoValue(String specificationTwoValue) {
        this.specificationTwoValue = specificationTwoValue;
    }

    public Boolean isSpecificationTwoRequired() {
        return specificationTwoRequired;
    }

    public Template specificationTwoRequired(Boolean specificationTwoRequired) {
        this.specificationTwoRequired = specificationTwoRequired;
        return this;
    }

    public void setSpecificationTwoRequired(Boolean specificationTwoRequired) {
        this.specificationTwoRequired = specificationTwoRequired;
    }

    public Boolean isShowInRfqTwoRequired() {
		return showInRfqTwoRequired;
	}

    public Template showInRfqTwoRequired(Boolean showInRfqTwoRequired) {
    	this.showInRfqTwoRequired = showInRfqTwoRequired;
		return this;

    }

    public void setShowInRfqTwoRequired(Boolean showInRfqTwoRequired) {
		this.showInRfqTwoRequired = showInRfqTwoRequired;
	}

    public Boolean isShowInAuctionTwoRequired() {
		return showInAuctionTwoRequired;
	}

    public Template showInAuctionTwoRequired(Boolean showInAuctionTwoRequired) {
    	this.showInAuctionTwoRequired = showInAuctionTwoRequired;
		return this;

    }
	public void setShowInAuctionTwoRequired(Boolean showInAuctionTwoRequired) {
		this.showInAuctionTwoRequired = showInAuctionTwoRequired;
	}

    public String getSpecificationThree() {
        return specificationThree;
    }

    public Template specificationThree(String specificationThree) {
        this.specificationThree = specificationThree;
        return this;
    }

    public void setSpecificationThree(String specificationThree) {
        this.specificationThree = specificationThree;
    }

    public ShowAsTemplate getSpecificationThreeShowAs() {
        return specificationThreeShowAs;
    }

    public Template specificationThreeShowAs(ShowAsTemplate specificationThreeShowAs) {
        this.specificationThreeShowAs = specificationThreeShowAs;
        return this;
    }

    public void setSpecificationThreeShowAs(ShowAsTemplate specificationThreeShowAs) {
        this.specificationThreeShowAs = specificationThreeShowAs;
    }

    public String getSpecificationThreeValue() {
        return specificationThreeValue;
    }

    public Template specificationThreeValue(String specificationThreeValue) {
        this.specificationThreeValue = specificationThreeValue;
        return this;
    }

    public void setSpecificationThreeValue(String specificationThreeValue) {
        this.specificationThreeValue = specificationThreeValue;
    }

    public Boolean isSpecificationThreeRequired() {
        return specificationThreeRequired;
    }

    public Template specificationThreeRequired(Boolean specificationThreeRequired) {
        this.specificationThreeRequired = specificationThreeRequired;
        return this;
    }

    public void setSpecificationThreeRequired(Boolean specificationThreeRequired) {
        this.specificationThreeRequired = specificationThreeRequired;
    }

    public Boolean isShowInRfqThreeRequired() {
		return showInRfqThreeRequired;
	}

    public Template showInRfqThreeRequired(Boolean showInRfqThreeRequired) {
    	this.showInRfqThreeRequired = showInRfqThreeRequired;
		return this;

    }

    public void setShowInRfqThreeRequired(Boolean showInRfqThreeRequired) {
		this.showInRfqThreeRequired = showInRfqThreeRequired;
	}

    public Boolean isShowInAuctionThreeRequired() {
		return showInAuctionThreeRequired;
	}

    public Template showInAuctionThreeRequired(Boolean showInAuctionThreeRequired) {
    	this.showInAuctionThreeRequired = showInAuctionThreeRequired;
		return this;

    }
	public void setShowInAuctionThreeRequired(Boolean showInAuctionThreeRequired) {
		this.showInAuctionThreeRequired = showInAuctionThreeRequired;
	}

    public String getSpecificationFour() {
        return specificationFour;
    }

    public Template specificationFour(String specificationFour) {
        this.specificationFour = specificationFour;
        return this;
    }

    public void setSpecificationFour(String specificationFour) {
        this.specificationFour = specificationFour;
    }

    public ShowAsTemplate getSpecificationFourShowAs() {
        return specificationFourShowAs;
    }

    public Template specificationFourShowAs(ShowAsTemplate specificationFourShowAs) {
        this.specificationFourShowAs = specificationFourShowAs;
        return this;
    }

    public void setSpecificationFourShowAs(ShowAsTemplate specificationFourShowAs) {
        this.specificationFourShowAs = specificationFourShowAs;
    }

    public String getSpecificationFourValue() {
        return specificationFourValue;
    }

    public Template specificationFourValue(String specificationFourValue) {
        this.specificationFourValue = specificationFourValue;
        return this;
    }

    public void setSpecificationFourValue(String specificationFourValue) {
        this.specificationFourValue = specificationFourValue;
    }

    public Boolean isSpecificationFourRequired() {
        return specificationFourRequired;
    }

    public Template specificationFourRequired(Boolean specificationFourRequired) {
        this.specificationFourRequired = specificationFourRequired;
        return this;
    }

    public void setSpecificationFourRequired(Boolean specificationFourRequired) {
        this.specificationFourRequired = specificationFourRequired;
    }

    public Boolean isShowInRfqFourRequired() {
		return showInRfqFourRequired;
	}

    public Template showInRfqFourRequired(Boolean showInRfqFourRequired) {
    	this.showInRfqFourRequired = showInRfqFourRequired;
		return this;

    }

    public void setShowInRfqFourRequired(Boolean showInRfqFourRequired) {
		this.showInRfqFourRequired = showInRfqFourRequired;
	}

    public Boolean isShowInAuctionFourRequired() {
		return showInAuctionFourRequired;
	}

    public Template showInAuctionFourRequired(Boolean showInAuctionFourRequired) {
    	this.showInAuctionFourRequired = showInAuctionFourRequired;
		return this;

    }
	public void setShowInAuctionFourRequired(Boolean showInAuctionFourRequired) {
		this.showInAuctionFourRequired = showInAuctionFourRequired;
	}

    public String getSpecificationFive() {
        return specificationFive;
    }

    public Template specificationFive(String specificationFive) {
        this.specificationFive = specificationFive;
        return this;
    }

    public void setSpecificationFive(String specificationFive) {
        this.specificationFive = specificationFive;
    }

    public ShowAsTemplate getSpecificationFiveShowAs() {
        return specificationFiveShowAs;
    }

    public Template specificationFiveShowAs(ShowAsTemplate specificationFiveShowAs) {
        this.specificationFiveShowAs = specificationFiveShowAs;
        return this;
    }

    public void setSpecificationFiveShowAs(ShowAsTemplate specificationFiveShowAs) {
        this.specificationFiveShowAs = specificationFiveShowAs;
    }

    public String getSpecificationFiveValue() {
        return specificationFiveValue;
    }

    public Template specificationFiveValue(String specificationFiveValue) {
        this.specificationFiveValue = specificationFiveValue;
        return this;
    }

    public void setSpecificationFiveValue(String specificationFiveValue) {
        this.specificationFiveValue = specificationFiveValue;
    }

    public Boolean isSpecificationFiveRequired() {
        return specificationFiveRequired;
    }

    public Template specificationFiveRequired(Boolean specificationFiveRequired) {
        this.specificationFiveRequired = specificationFiveRequired;
        return this;
    }

    public void setSpecificationFiveRequired(Boolean specificationFiveRequired) {
        this.specificationFiveRequired = specificationFiveRequired;
    }

    public Boolean isShowInRfqFiveRequired() {
		return showInRfqFiveRequired;
	}

    public Template showInRfqFiveRequired(Boolean showInRfqFiveRequired) {
    	this.showInRfqFiveRequired = showInRfqFiveRequired;
		return this;

    }

    public void setShowInRfqFiveRequired(Boolean showInRfqFiveRequired) {
		this.showInRfqFiveRequired = showInRfqFiveRequired;
	}

    public Boolean isShowInAuctionFiveRequired() {
		return showInAuctionFiveRequired;
	}

    public Template showInAuctionFiveRequired(Boolean showInAuctionFiveRequired) {
    	this.showInAuctionFiveRequired = showInAuctionFiveRequired;
		return this;

    }
	public void setShowInAuctionFiveRequired(Boolean showInAuctionFiveRequired) {
		this.showInAuctionFiveRequired = showInAuctionFiveRequired;
	}


    public String getSpecificationSix() {
        return specificationSix;
    }

    public Template specificationSix(String specificationSix) {
        this.specificationSix = specificationSix;
        return this;
    }

    public void setSpecificationSix(String specificationSix) {
        this.specificationSix = specificationSix;
    }

    public ShowAsTemplate getSpecificationSixShowAs() {
        return specificationSixShowAs;
    }

    public Template specificationSixShowAs(ShowAsTemplate specificationSixShowAs) {
        this.specificationSixShowAs = specificationSixShowAs;
        return this;
    }

    public void setSpecificationSixShowAs(ShowAsTemplate specificationSixShowAs) {
        this.specificationSixShowAs = specificationSixShowAs;
    }

    public String getSpecificationSixValue() {
        return specificationSixValue;
    }

    public Template specificationSixValue(String specificationSixValue) {
        this.specificationSixValue = specificationSixValue;
        return this;
    }

    public void setSpecificationSixValue(String specificationSixValue) {
        this.specificationSixValue = specificationSixValue;
    }

    public Boolean isSpecificationSixRequired() {
        return specificationSixRequired;
    }

    public Template specificationSixRequired(Boolean specificationSixRequired) {
        this.specificationSixRequired = specificationSixRequired;
        return this;
    }

    public void setSpecificationSixRequired(Boolean specificationSixRequired) {
        this.specificationSixRequired = specificationSixRequired;
    }

    public Boolean isShowInRfqSixRequired() {
		return showInRfqSixRequired;
	}

    public Template showInRfqSixRequired(Boolean showInRfqSixRequired) {
    	this.showInRfqSixRequired = showInRfqSixRequired;
		return this;

    }

    public void setShowInRfqSixRequired(Boolean showInRfqSixRequired) {
		this.showInRfqSixRequired = showInRfqSixRequired;
	}

    public Boolean isShowInAuctionSixRequired() {
		return showInAuctionSixRequired;
	}

    public Template showInAuctionSixRequired(Boolean showInAuctionSixRequired) {
    	this.showInAuctionSixRequired = showInAuctionSixRequired;
		return this;

    }
	public void setShowInAuctionSixRequired(Boolean showInAuctionSixRequired) {
		this.showInAuctionSixRequired = showInAuctionSixRequired;
	}

    public String getSpecificationSeven() {
        return specificationSeven;
    }

    public Template specificationSeven(String specificationSeven) {
        this.specificationSeven = specificationSeven;
        return this;
    }

    public void setSpecificationSeven(String specificationSeven) {
        this.specificationSeven = specificationSeven;
    }

    public ShowAsTemplate getSpecificationSevenShowAs() {
        return specificationSevenShowAs;
    }

    public Template specificationSevenShowAs(ShowAsTemplate specificationSevenShowAs) {
        this.specificationSevenShowAs = specificationSevenShowAs;
        return this;
    }

    public void setSpecificationSevenShowAs(ShowAsTemplate specificationSevenShowAs) {
        this.specificationSevenShowAs = specificationSevenShowAs;
    }

    public String getSpecificationSevenValue() {
        return specificationSevenValue;
    }

    public Template specificationSevenValue(String specificationSevenValue) {
        this.specificationSevenValue = specificationSevenValue;
        return this;
    }

    public void setSpecificationSevenValue(String specificationSevenValue) {
        this.specificationSevenValue = specificationSevenValue;
    }

    public Boolean isSpecificationSevenRequired() {
        return specificationSevenRequired;
    }

    public Template specificationSevenRequired(Boolean specificationSevenRequired) {
        this.specificationSevenRequired = specificationSevenRequired;
        return this;
    }

    public void setSpecificationSevenRequired(Boolean specificationSevenRequired) {
        this.specificationSevenRequired = specificationSevenRequired;
    }

    public Boolean isShowInRfqSevenRequired() {
		return showInRfqSevenRequired;
	}

    public Template showInRfqSevenRequired(Boolean showInRfqSevenRequired) {
    	this.showInRfqSevenRequired = showInRfqSevenRequired;
		return this;

    }

    public void setShowInRfqSevenRequired(Boolean showInRfqSevenRequired) {
		this.showInRfqSevenRequired = showInRfqSevenRequired;
	}

    public Boolean isShowInAuctionSevenRequired() {
		return showInAuctionSevenRequired;
	}

    public Template showInAuctionSevenRequired(Boolean showInAuctionSevenRequired) {
    	this.showInAuctionSevenRequired = showInAuctionSevenRequired;
		return this;

    }
	public void setShowInAuctionSevenRequired(Boolean showInAuctionSevenRequired) {
		this.showInAuctionSevenRequired = showInAuctionSevenRequired;
	}

    public String getSpecificationEight() {
        return specificationEight;
    }

    public Template specificationEight(String specificationEight) {
        this.specificationEight = specificationEight;
        return this;
    }

    public void setSpecificationEight(String specificationEight) {
        this.specificationEight = specificationEight;
    }

    public ShowAsTemplate getSpecificationEightShowAs() {
        return specificationEightShowAs;
    }

    public Template specificationEightShowAs(ShowAsTemplate specificationEightShowAs) {
        this.specificationEightShowAs = specificationEightShowAs;
        return this;
    }

    public void setSpecificationEightShowAs(ShowAsTemplate specificationEightShowAs) {
        this.specificationEightShowAs = specificationEightShowAs;
    }

    public String getSpecificationEightValue() {
        return specificationEightValue;
    }

    public Template specificationEightValue(String specificationEightValue) {
        this.specificationEightValue = specificationEightValue;
        return this;
    }

    public void setSpecificationEightValue(String specificationEightValue) {
        this.specificationEightValue = specificationEightValue;
    }

    public Boolean isSpecificationEightRequired() {
        return specificationEightRequired;
    }

    public Template specificationEightRequired(Boolean specificationEightRequired) {
        this.specificationEightRequired = specificationEightRequired;
        return this;
    }

    public void setSpecificationEightRequired(Boolean specificationEightRequired) {
        this.specificationEightRequired = specificationEightRequired;
    }

    public Boolean isShowInRfqEightRequired() {
		return showInRfqEightRequired;
	}

    public Template showInRfqEightRequired(Boolean showInRfqEightRequired) {
    	this.showInRfqEightRequired = showInRfqEightRequired;
		return this;

    }

    public void setShowInRfqEightRequired(Boolean showInRfqEightRequired) {
		this.showInRfqEightRequired = showInRfqEightRequired;
	}

    public Boolean isShowInAuctionEightRequired() {
		return showInAuctionEightRequired;
	}

    public Template showInAuctionEightRequired(Boolean showInAuctionEightRequired) {
    	this.showInAuctionEightRequired = showInAuctionEightRequired;
		return this;

    }
	public void setShowInAuctionEightRequired(Boolean showInAuctionEightRequired) {
		this.showInAuctionEightRequired = showInAuctionEightRequired;
	}

    public String getSpecificationNine() {
        return specificationNine;
    }

    public Template specificationNine(String specificationNine) {
        this.specificationNine = specificationNine;
        return this;
    }

    public void setSpecificationNine(String specificationNine) {
        this.specificationNine = specificationNine;
    }

    public ShowAsTemplate getSpecificationNineShowAs() {
        return specificationNineShowAs;
    }

    public Template specificationNineShowAs(ShowAsTemplate specificationNineShowAs) {
        this.specificationNineShowAs = specificationNineShowAs;
        return this;
    }

    public void setSpecificationNineShowAs(ShowAsTemplate specificationNineShowAs) {
        this.specificationNineShowAs = specificationNineShowAs;
    }

    public String getSpecificationNineValue() {
        return specificationNineValue;
    }

    public Template specificationNineValue(String specificationNineValue) {
        this.specificationNineValue = specificationNineValue;
        return this;
    }

    public void setSpecificationNineValue(String specificationNineValue) {
        this.specificationNineValue = specificationNineValue;
    }

    public Boolean isSpecificationNineRequired() {
        return specificationNineRequired;
    }

    public Template specificationNineRequired(Boolean specificationNineRequired) {
        this.specificationNineRequired = specificationNineRequired;
        return this;
    }

    public void setSpecificationNineRequired(Boolean specificationNineRequired) {
        this.specificationNineRequired = specificationNineRequired;
    }

    public Boolean isShowInRfqNineRequired() {
		return showInRfqNineRequired;
	}

    public Template showInRfqNineRequired(Boolean showInRfqNineRequired) {
    	this.showInRfqNineRequired = showInRfqNineRequired;
		return this;

    }

    public void setShowInRfqNineRequired(Boolean showInRfqNineRequired) {
		this.showInRfqNineRequired = showInRfqNineRequired;
	}

    public Boolean isShowInAuctionNineRequired() {
		return showInAuctionNineRequired;
	}

    public Template showInAuctionNineRequired(Boolean showInAuctionNineRequired) {
    	this.showInAuctionNineRequired = showInAuctionNineRequired;
		return this;

    }
	public void setShowInAuctionNineRequired(Boolean showInAuctionNineRequired) {
		this.showInAuctionNineRequired = showInAuctionNineRequired;
	}

    public String getSpecificationTen() {
        return specificationTen;
    }

    public Template specificationTen(String specificationTen) {
        this.specificationTen = specificationTen;
        return this;
    }

    public void setSpecificationTen(String specificationTen) {
        this.specificationTen = specificationTen;
    }

    public ShowAsTemplate getSpecificationTenShowAs() {
        return specificationTenShowAs;
    }

    public Template specificationTenShowAs(ShowAsTemplate specificationTenShowAs) {
        this.specificationTenShowAs = specificationTenShowAs;
        return this;
    }

    public void setSpecificationTenShowAs(ShowAsTemplate specificationTenShowAs) {
        this.specificationTenShowAs = specificationTenShowAs;
    }

    public String getSpecificationTenValue() {
        return specificationTenValue;
    }

    public Template specificationTenValue(String specificationTenValue) {
        this.specificationTenValue = specificationTenValue;
        return this;
    }

    public void setSpecificationTenValue(String specificationTenValue) {
        this.specificationTenValue = specificationTenValue;
    }

    public Boolean isSpecificationTenRequired() {
        return specificationTenRequired;
    }

    public Template specificationTenRequired(Boolean specificationTenRequired) {
        this.specificationTenRequired = specificationTenRequired;
        return this;
    }

    public void setSpecificationTenRequired(Boolean specificationTenRequired) {
        this.specificationTenRequired = specificationTenRequired;
    }

    public Boolean isShowInRfqTenRequired() {
		return showInRfqTenRequired;
	}

    public Template showInRfqTenRequired(Boolean showInRfqTenRequired) {
    	this.showInRfqTenRequired = showInRfqTenRequired;
		return this;

    }

    public void setShowInRfqTenRequired(Boolean showInRfqTenRequired) {
		this.showInRfqTenRequired = showInRfqTenRequired;
	}

    public Boolean isShowInAuctionTenRequired() {
		return showInAuctionTenRequired;
	}

    public Template showInAuctionTenRequired(Boolean showInAuctionTenRequired) {
    	this.showInAuctionTenRequired = showInAuctionTenRequired;
		return this;

    }
	public void setShowInAuctionTenRequired(Boolean showInAuctionTenRequired) {
		this.showInAuctionTenRequired = showInAuctionTenRequired;
	}


    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public Template rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
        return this;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
    }



    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove



	public Instant getCreatedDate() {
		return createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}



	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Instant getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Instant lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Boolean getSpecificationOneRequired() {
		return specificationOneRequired;
	}

	public Boolean getShowInRfqOneRequired() {
		return showInRfqOneRequired;
	}

	public Boolean getShowInAuctionOneRequired() {
		return showInAuctionOneRequired;
	}

	public Boolean getSpecificationTwoRequired() {
		return specificationTwoRequired;
	}

	public Boolean getShowInRfqTwoRequired() {
		return showInRfqTwoRequired;
	}

	public Boolean getShowInAuctionTwoRequired() {
		return showInAuctionTwoRequired;
	}

	public Boolean getSpecificationThreeRequired() {
		return specificationThreeRequired;
	}

	public Boolean getShowInRfqThreeRequired() {
		return showInRfqThreeRequired;
	}

	public Boolean getShowInAuctionThreeRequired() {
		return showInAuctionThreeRequired;
	}

	public Boolean getSpecificationFourRequired() {
		return specificationFourRequired;
	}

	public Boolean getShowInRfqFourRequired() {
		return showInRfqFourRequired;
	}

	public Boolean getShowInAuctionFourRequired() {
		return showInAuctionFourRequired;
	}

	public Boolean getSpecificationFiveRequired() {
		return specificationFiveRequired;
	}

	public Boolean getShowInRfqFiveRequired() {
		return showInRfqFiveRequired;
	}

	public Boolean getShowInAuctionFiveRequired() {
		return showInAuctionFiveRequired;
	}

	public Boolean getSpecificationSixRequired() {
		return specificationSixRequired;
	}

	public Boolean getShowInRfqSixRequired() {
		return showInRfqSixRequired;
	}

	public Boolean getShowInAuctionSixRequired() {
		return showInAuctionSixRequired;
	}

	public Boolean getSpecificationSevenRequired() {
		return specificationSevenRequired;
	}

	public Boolean getShowInRfqSevenRequired() {
		return showInRfqSevenRequired;
	}

	public Boolean getShowInAuctionSevenRequired() {
		return showInAuctionSevenRequired;
	}

	public Boolean getSpecificationEightRequired() {
		return specificationEightRequired;
	}

	public Boolean getShowInRfqEightRequired() {
		return showInRfqEightRequired;
	}

	public Boolean getShowInAuctionEightRequired() {
		return showInAuctionEightRequired;
	}

	public Boolean getSpecificationNineRequired() {
		return specificationNineRequired;
	}

	public Boolean getShowInRfqNineRequired() {
		return showInRfqNineRequired;
	}

	public Boolean getShowInAuctionNineRequired() {
		return showInAuctionNineRequired;
	}

	public Boolean getSpecificationTenRequired() {
		return specificationTenRequired;
	}

	public Boolean getShowInRfqTenRequired() {
		return showInRfqTenRequired;
	}

	public Boolean getShowInAuctionTenRequired() {
		return showInAuctionTenRequired;
	}

    public boolean isFlag() { return flag; }

    public void setFlag(boolean flag) { this.flag = flag; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "Template [id=" + id +
				", templateName=" + templateName +
				", specificationOne=" + specificationOne +
				", specificationOneShowAs=" + specificationOneShowAs +
				", specificationOneValue="	+ specificationOneValue +
				", specificationOneRequired=" + specificationOneRequired +
				", showInRfqOneRequired=" + showInRfqOneRequired +
				", showInAuctionOneRequired=" + showInAuctionOneRequired +
				", specificationTwo=" + specificationTwo +
				", specificationTwoShowAs="	+ specificationTwoShowAs +
				", specificationTwoValue=" + specificationTwoValue	+
				", specificationTwoRequired=" + specificationTwoRequired +
				", showInRfqTwoRequired=" + showInRfqTwoRequired +
				", showInAuctionTwoRequired=" + showInAuctionTwoRequired +
				", specificationThree=" + specificationThree +
				", specificationThreeShowAs=" + specificationThreeShowAs +
				", specificationThreeValue=" + specificationThreeValue	+
				", specificationThreeRequired=" + specificationThreeRequired +
				", showInRfqThreeRequired="	+ showInRfqThreeRequired +
				", showInAuctionThreeRequired=" + showInAuctionThreeRequired +
				", specificationFour=" + specificationFour +
				", specificationFourShowAs=" + specificationFourShowAs	+
				", specificationFourValue=" + specificationFourValue +
				", specificationFourRequired=" + specificationFourRequired +
				", showInRfqFourRequired=" + showInRfqFourRequired +
				", showInAuctionFourRequired=" + showInAuctionFourRequired +
				", specificationFive=" + specificationFive +
				", specificationFiveShowAs=" + specificationFiveShowAs +
				", specificationFiveValue=" + specificationFiveValue +
				", specificationFiveRequired=" + specificationFiveRequired +
				", showInRfqFiveRequired=" + showInRfqFiveRequired +
				", showInAuctionFiveRequired=" + showInAuctionFiveRequired +
				", specificationSix=" + specificationSix +
				", specificationSixShowAs=" + specificationSixShowAs +
				", specificationSixValue=" + specificationSixValue +
				", specificationSixRequired=" + specificationSixRequired +
				", showInRfqSixRequired=" + showInRfqSixRequired +
				", showInAuctionSixRequired=" + showInAuctionSixRequired +
				", specificationSeven=" + specificationSeven +
				", specificationSevenShowAs=" + specificationSevenShowAs +
				", specificationSevenValue=" + specificationSevenValue +
				", specificationSevenRequired=" + specificationSevenRequired +
				", showInRfqSevenRequired=" + showInRfqSevenRequired +
				", showInAuctionSevenRequired=" + showInAuctionSevenRequired +
				", specificationEight=" + specificationEight +
				", specificationEightShowAs=" + specificationEightShowAs +
				", specificationEightValue=" + specificationEightValue +
				", specificationEightRequired=" + specificationEightRequired +
				", showInRfqEightRequired=" + showInRfqEightRequired +
				", showInAuctionEightRequired="	+ showInAuctionEightRequired +
				", specificationNine=" + specificationNine +
				", specificationNineShowAs=" + specificationNineShowAs +
				", specificationNineValue=" + specificationNineValue +
				", specificationNineRequired=" + specificationNineRequired +
				", showInRfqNineRequired=" + showInRfqNineRequired +
				", showInAuctionNineRequired=" + showInAuctionNineRequired +
				", specificationTen=" + specificationTen +
				", specificationTenShowAs=" + specificationTenShowAs +
				", specificationTenValue=" + specificationTenValue +
				", specificationTenRequired=" + specificationTenRequired +
				", showInRfqTenRequired=" + showInRfqTenRequired +
				", showInAuctionTenRequired=" + showInAuctionTenRequired +
				", rnsCatgCode=" + rnsCatgCode +
				", getId()=" + getId() +
				", getTemplateName()=" + getTemplateName() +
				", getSpecificationOne()=" + getSpecificationOne() +
				", getSpecificationOneShowAs()=" + getSpecificationOneShowAs() +
				", getSpecificationOneValue()=" + getSpecificationOneValue() +
				", isSpecificationOneRequired()=" + isSpecificationOneRequired() +
				", isShowInRfqOneRequired()=" + isShowInRfqOneRequired() +
				", isShowInAuctionOneRequired()=" + isShowInAuctionOneRequired() +
				", getSpecificationTwo()=" + getSpecificationTwo() +
				", getSpecificationTwoShowAs()=" + getSpecificationTwoShowAs() +
				", getSpecificationTwoValue()=" + getSpecificationTwoValue() +
				", isSpecificationTwoRequired()=" + isSpecificationTwoRequired() +
				", isShowInRfqTwoRequired()=" + isShowInRfqTwoRequired() +
				", isShowInAuctionTwoRequired()=" + isShowInAuctionTwoRequired() +
				", getSpecificationThree()=" + getSpecificationThree() +
				", getSpecificationThreeShowAs()=" + getSpecificationThreeShowAs() +
				", getSpecificationThreeValue()=" + getSpecificationThreeValue() +
				", isSpecificationThreeRequired()="	+ isSpecificationThreeRequired() +
				", isShowInRfqThreeRequired()=" + isShowInRfqThreeRequired() +
				", isShowInAuctionThreeRequired()=" + isShowInAuctionThreeRequired() +
				", getSpecificationFour()="	+ getSpecificationFour() +
				", getSpecificationFourShowAs()=" + getSpecificationFourShowAs() +
				", getSpecificationFourValue()=" + getSpecificationFourValue() +
				", isSpecificationFourRequired()=" + isSpecificationFourRequired() +
				", isShowInRfqFourRequired()=" + isShowInRfqFourRequired() +
				", isShowInAuctionFourRequired()=" + isShowInAuctionFourRequired() +
				", getSpecificationFive()="	+ getSpecificationFive() +
				", getSpecificationFiveShowAs()=" + getSpecificationFiveShowAs() +
				", getSpecificationFiveValue()=" + getSpecificationFiveValue() +
				", isSpecificationFiveRequired()=" + isSpecificationFiveRequired() +
				", isShowInRfqFiveRequired()=" + isShowInRfqFiveRequired() +
				", isShowInAuctionFiveRequired()=" + isShowInAuctionFiveRequired() +
				", getSpecificationSix()=" + getSpecificationSix() +
				", getSpecificationSixShowAs()=" + getSpecificationSixShowAs() +
				", getSpecificationSixValue()=" + getSpecificationSixValue() +
				", isSpecificationSixRequired()=" + isSpecificationSixRequired() +
				", isShowInRfqSixRequired()=" + isShowInRfqSixRequired() +
				", isShowInAuctionSixRequired()=" + isShowInAuctionSixRequired() +
				", getSpecificationSeven()=" + getSpecificationSeven() +
				", getSpecificationSevenShowAs()=" + getSpecificationSevenShowAs() +
				", getSpecificationSevenValue()=" + getSpecificationSevenValue() +
				", isSpecificationSevenRequired()="	+ isSpecificationSevenRequired() +
				", isShowInRfqSevenRequired()=" + isShowInRfqSevenRequired() +
				", isShowInAuctionSevenRequired()=" + isShowInAuctionSevenRequired() +
				", getSpecificationEight()=" + getSpecificationEight() +
				", getSpecificationEightShowAs()=" + getSpecificationEightShowAs() +
				", getSpecificationEightValue()=" + getSpecificationEightValue() +
				", isSpecificationEightRequired()="	+ isSpecificationEightRequired() +
				", isShowInRfqEightRequired()=" + isShowInRfqEightRequired() +
				", isShowInAuctionEightRequired()=" + isShowInAuctionEightRequired() +
				", getSpecificationNine()="	+ getSpecificationNine() +
				", getSpecificationNineShowAs()=" + getSpecificationNineShowAs() +
				", getSpecificationNineValue()=" + getSpecificationNineValue() +
				", isSpecificationNineRequired()=" + isSpecificationNineRequired() +
				", isShowInRfqNineRequired()=" + isShowInRfqNineRequired() +
				", isShowInAuctionNineRequired()=" + isShowInAuctionNineRequired() +
				", getSpecificationTen()=" + getSpecificationTen() +
				", getSpecificationTenShowAs()=" + getSpecificationTenShowAs() +
				", getSpecificationTenValue()=" + getSpecificationTenValue() +
				", isSpecificationTenRequired()=" + isSpecificationTenRequired() +
				", isShowInRfqTenRequired()=" + isShowInRfqTenRequired() +
				", isShowInAuctionTenRequired()=" + isShowInAuctionTenRequired() +
				", getRnsCatgCode()=" + getRnsCatgCode() +
				", hashCode()=" + hashCode() +
				", getClass()=" + getClass() +
				", toString()="	+ super.toString() +
				"]";
	}
}
