package rightchamps.modal;

import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.User;
import rightchamps.domain.enumeration.ShowAsTemplate;

import java.sql.Timestamp;
import java.time.Instant;

public class TemplateBean {
    private Long id;
    private String templateName;
    private String specificationOne;
    private ShowAsTemplate specificationOneShowAs;
    private String specificationOneValue;
    private Boolean specificationOneRequired;
    private Boolean showInRfqOneRequired;
    private Boolean showInAuctionOneRequired;
    private String specificationTwo;
    private ShowAsTemplate specificationTwoShowAs;
    private String specificationTwoValue;
    private Boolean specificationTwoRequired;
    private Boolean showInRfqTwoRequired;
    private Boolean showInAuctionTwoRequired;
    private String specificationThree;
    private ShowAsTemplate specificationThreeShowAs;
    private String specificationThreeValue;
    private Boolean specificationThreeRequired;
    private Boolean showInRfqThreeRequired;
    private Boolean showInAuctionThreeRequired;
    private String specificationFour;
    private ShowAsTemplate specificationFourShowAs;
    private String specificationFourValue;
    private Boolean specificationFourRequired;
    private Boolean showInRfqFourRequired;
    private Boolean showInAuctionFourRequired;
    private String specificationFive;
    private ShowAsTemplate specificationFiveShowAs;
    private String specificationFiveValue;
    private Boolean specificationFiveRequired;
    private Boolean showInRfqFiveRequired;
    private Boolean showInAuctionFiveRequired;
    private String specificationSix;
    private ShowAsTemplate specificationSixShowAs;
    private String specificationSixValue;
    private Boolean specificationSixRequired;
    private Boolean showInRfqSixRequired;
    private Boolean showInAuctionSixRequired;
    private String specificationSeven;
    private ShowAsTemplate specificationSevenShowAs;
    private String specificationSevenValue;
    private Boolean specificationSevenRequired;
    private Boolean showInRfqSevenRequired;
    private Boolean showInAuctionSevenRequired;
    private String specificationEight;
    private ShowAsTemplate specificationEightShowAs;
    private String specificationEightValue;
    private Boolean specificationEightRequired;
    private Boolean showInRfqEightRequired;
    private Boolean showInAuctionEightRequired;
    private String specificationNine;
    private ShowAsTemplate specificationNineShowAs;
    private String specificationNineValue;
    private Boolean specificationNineRequired;
    private Boolean showInRfqNineRequired;
    private Boolean showInAuctionNineRequired;
    private String specificationTen;
    private ShowAsTemplate specificationTenShowAs;
    private String specificationTenValue;
    private Boolean specificationTenRequired;
    private Boolean showInRfqTenRequired;
    private Boolean showInAuctionTenRequired;
    private RnsCatgMaster rnsCatgCode;
    private User user;
    private Timestamp createdDateTimestamp;
    private User updatedUser;
    private Timestamp lastUpdatedDateTimestamp;
    private boolean flag = false;
    private boolean exist;
    private String categoryDesc;
    private String createdBy;
    private String updatedBy;
    private Instant createdDate;
    private Instant lastUpdatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSpecificationOne() {
        return specificationOne;
    }

    public void setSpecificationOne(String specificationOne) {
        this.specificationOne = specificationOne;
    }

    public ShowAsTemplate getSpecificationOneShowAs() {
        return specificationOneShowAs;
    }

    public void setSpecificationOneShowAs(ShowAsTemplate specificationOneShowAs) {
        this.specificationOneShowAs = specificationOneShowAs;
    }

    public String getSpecificationOneValue() {
        return specificationOneValue;
    }

    public void setSpecificationOneValue(String specificationOneValue) {
        this.specificationOneValue = specificationOneValue;
    }

    public Boolean getSpecificationOneRequired() {
        return specificationOneRequired;
    }

    public void setSpecificationOneRequired(Boolean specificationOneRequired) {
        this.specificationOneRequired = specificationOneRequired;
    }

    public Boolean getShowInRfqOneRequired() {
        return showInRfqOneRequired;
    }

    public void setShowInRfqOneRequired(Boolean showInRfqOneRequired) {
        this.showInRfqOneRequired = showInRfqOneRequired;
    }

    public Boolean getShowInAuctionOneRequired() {
        return showInAuctionOneRequired;
    }

    public void setShowInAuctionOneRequired(Boolean showInAuctionOneRequired) {
        this.showInAuctionOneRequired = showInAuctionOneRequired;
    }

    public String getSpecificationTwo() {
        return specificationTwo;
    }

    public void setSpecificationTwo(String specificationTwo) {
        this.specificationTwo = specificationTwo;
    }

    public ShowAsTemplate getSpecificationTwoShowAs() {
        return specificationTwoShowAs;
    }

    public void setSpecificationTwoShowAs(ShowAsTemplate specificationTwoShowAs) {
        this.specificationTwoShowAs = specificationTwoShowAs;
    }

    public String getSpecificationTwoValue() {
        return specificationTwoValue;
    }

    public void setSpecificationTwoValue(String specificationTwoValue) {
        this.specificationTwoValue = specificationTwoValue;
    }

    public Boolean getSpecificationTwoRequired() {
        return specificationTwoRequired;
    }

    public void setSpecificationTwoRequired(Boolean specificationTwoRequired) {
        this.specificationTwoRequired = specificationTwoRequired;
    }

    public Boolean getShowInRfqTwoRequired() {
        return showInRfqTwoRequired;
    }

    public void setShowInRfqTwoRequired(Boolean showInRfqTwoRequired) {
        this.showInRfqTwoRequired = showInRfqTwoRequired;
    }

    public Boolean getShowInAuctionTwoRequired() {
        return showInAuctionTwoRequired;
    }

    public void setShowInAuctionTwoRequired(Boolean showInAuctionTwoRequired) {
        this.showInAuctionTwoRequired = showInAuctionTwoRequired;
    }

    public String getSpecificationThree() {
        return specificationThree;
    }

    public void setSpecificationThree(String specificationThree) {
        this.specificationThree = specificationThree;
    }

    public ShowAsTemplate getSpecificationThreeShowAs() {
        return specificationThreeShowAs;
    }

    public void setSpecificationThreeShowAs(ShowAsTemplate specificationThreeShowAs) {
        this.specificationThreeShowAs = specificationThreeShowAs;
    }

    public String getSpecificationThreeValue() {
        return specificationThreeValue;
    }

    public void setSpecificationThreeValue(String specificationThreeValue) {
        this.specificationThreeValue = specificationThreeValue;
    }

    public Boolean getSpecificationThreeRequired() {
        return specificationThreeRequired;
    }

    public void setSpecificationThreeRequired(Boolean specificationThreeRequired) {
        this.specificationThreeRequired = specificationThreeRequired;
    }

    public Boolean getShowInRfqThreeRequired() {
        return showInRfqThreeRequired;
    }

    public void setShowInRfqThreeRequired(Boolean showInRfqThreeRequired) {
        this.showInRfqThreeRequired = showInRfqThreeRequired;
    }

    public Boolean getShowInAuctionThreeRequired() {
        return showInAuctionThreeRequired;
    }

    public void setShowInAuctionThreeRequired(Boolean showInAuctionThreeRequired) {
        this.showInAuctionThreeRequired = showInAuctionThreeRequired;
    }

    public String getSpecificationFour() {
        return specificationFour;
    }

    public void setSpecificationFour(String specificationFour) {
        this.specificationFour = specificationFour;
    }

    public ShowAsTemplate getSpecificationFourShowAs() {
        return specificationFourShowAs;
    }

    public void setSpecificationFourShowAs(ShowAsTemplate specificationFourShowAs) {
        this.specificationFourShowAs = specificationFourShowAs;
    }

    public String getSpecificationFourValue() {
        return specificationFourValue;
    }

    public void setSpecificationFourValue(String specificationFourValue) {
        this.specificationFourValue = specificationFourValue;
    }

    public Boolean getSpecificationFourRequired() {
        return specificationFourRequired;
    }

    public void setSpecificationFourRequired(Boolean specificationFourRequired) {
        this.specificationFourRequired = specificationFourRequired;
    }

    public Boolean getShowInRfqFourRequired() {
        return showInRfqFourRequired;
    }

    public void setShowInRfqFourRequired(Boolean showInRfqFourRequired) {
        this.showInRfqFourRequired = showInRfqFourRequired;
    }

    public Boolean getShowInAuctionFourRequired() {
        return showInAuctionFourRequired;
    }

    public void setShowInAuctionFourRequired(Boolean showInAuctionFourRequired) {
        this.showInAuctionFourRequired = showInAuctionFourRequired;
    }

    public String getSpecificationFive() {
        return specificationFive;
    }

    public void setSpecificationFive(String specificationFive) {
        this.specificationFive = specificationFive;
    }

    public ShowAsTemplate getSpecificationFiveShowAs() {
        return specificationFiveShowAs;
    }

    public void setSpecificationFiveShowAs(ShowAsTemplate specificationFiveShowAs) {
        this.specificationFiveShowAs = specificationFiveShowAs;
    }

    public String getSpecificationFiveValue() {
        return specificationFiveValue;
    }

    public void setSpecificationFiveValue(String specificationFiveValue) {
        this.specificationFiveValue = specificationFiveValue;
    }

    public Boolean getSpecificationFiveRequired() {
        return specificationFiveRequired;
    }

    public void setSpecificationFiveRequired(Boolean specificationFiveRequired) {
        this.specificationFiveRequired = specificationFiveRequired;
    }

    public Boolean getShowInRfqFiveRequired() {
        return showInRfqFiveRequired;
    }

    public void setShowInRfqFiveRequired(Boolean showInRfqFiveRequired) {
        this.showInRfqFiveRequired = showInRfqFiveRequired;
    }

    public Boolean getShowInAuctionFiveRequired() {
        return showInAuctionFiveRequired;
    }

    public void setShowInAuctionFiveRequired(Boolean showInAuctionFiveRequired) {
        this.showInAuctionFiveRequired = showInAuctionFiveRequired;
    }

    public String getSpecificationSix() {
        return specificationSix;
    }

    public void setSpecificationSix(String specificationSix) {
        this.specificationSix = specificationSix;
    }

    public ShowAsTemplate getSpecificationSixShowAs() {
        return specificationSixShowAs;
    }

    public void setSpecificationSixShowAs(ShowAsTemplate specificationSixShowAs) {
        this.specificationSixShowAs = specificationSixShowAs;
    }

    public String getSpecificationSixValue() {
        return specificationSixValue;
    }

    public void setSpecificationSixValue(String specificationSixValue) {
        this.specificationSixValue = specificationSixValue;
    }

    public Boolean getSpecificationSixRequired() {
        return specificationSixRequired;
    }

    public void setSpecificationSixRequired(Boolean specificationSixRequired) {
        this.specificationSixRequired = specificationSixRequired;
    }

    public Boolean getShowInRfqSixRequired() {
        return showInRfqSixRequired;
    }

    public void setShowInRfqSixRequired(Boolean showInRfqSixRequired) {
        this.showInRfqSixRequired = showInRfqSixRequired;
    }

    public Boolean getShowInAuctionSixRequired() {
        return showInAuctionSixRequired;
    }

    public void setShowInAuctionSixRequired(Boolean showInAuctionSixRequired) {
        this.showInAuctionSixRequired = showInAuctionSixRequired;
    }

    public String getSpecificationSeven() {
        return specificationSeven;
    }

    public void setSpecificationSeven(String specificationSeven) {
        this.specificationSeven = specificationSeven;
    }

    public ShowAsTemplate getSpecificationSevenShowAs() {
        return specificationSevenShowAs;
    }

    public void setSpecificationSevenShowAs(ShowAsTemplate specificationSevenShowAs) {
        this.specificationSevenShowAs = specificationSevenShowAs;
    }

    public String getSpecificationSevenValue() {
        return specificationSevenValue;
    }

    public void setSpecificationSevenValue(String specificationSevenValue) {
        this.specificationSevenValue = specificationSevenValue;
    }

    public Boolean getSpecificationSevenRequired() {
        return specificationSevenRequired;
    }

    public void setSpecificationSevenRequired(Boolean specificationSevenRequired) {
        this.specificationSevenRequired = specificationSevenRequired;
    }

    public Boolean getShowInRfqSevenRequired() {
        return showInRfqSevenRequired;
    }

    public void setShowInRfqSevenRequired(Boolean showInRfqSevenRequired) {
        this.showInRfqSevenRequired = showInRfqSevenRequired;
    }

    public Boolean getShowInAuctionSevenRequired() {
        return showInAuctionSevenRequired;
    }

    public void setShowInAuctionSevenRequired(Boolean showInAuctionSevenRequired) {
        this.showInAuctionSevenRequired = showInAuctionSevenRequired;
    }

    public String getSpecificationEight() {
        return specificationEight;
    }

    public void setSpecificationEight(String specificationEight) {
        this.specificationEight = specificationEight;
    }

    public ShowAsTemplate getSpecificationEightShowAs() {
        return specificationEightShowAs;
    }

    public void setSpecificationEightShowAs(ShowAsTemplate specificationEightShowAs) {
        this.specificationEightShowAs = specificationEightShowAs;
    }

    public String getSpecificationEightValue() {
        return specificationEightValue;
    }

    public void setSpecificationEightValue(String specificationEightValue) {
        this.specificationEightValue = specificationEightValue;
    }

    public Boolean getSpecificationEightRequired() {
        return specificationEightRequired;
    }

    public void setSpecificationEightRequired(Boolean specificationEightRequired) {
        this.specificationEightRequired = specificationEightRequired;
    }

    public Boolean getShowInRfqEightRequired() {
        return showInRfqEightRequired;
    }

    public void setShowInRfqEightRequired(Boolean showInRfqEightRequired) {
        this.showInRfqEightRequired = showInRfqEightRequired;
    }

    public Boolean getShowInAuctionEightRequired() {
        return showInAuctionEightRequired;
    }

    public void setShowInAuctionEightRequired(Boolean showInAuctionEightRequired) {
        this.showInAuctionEightRequired = showInAuctionEightRequired;
    }

    public String getSpecificationNine() {
        return specificationNine;
    }

    public void setSpecificationNine(String specificationNine) {
        this.specificationNine = specificationNine;
    }

    public ShowAsTemplate getSpecificationNineShowAs() {
        return specificationNineShowAs;
    }

    public void setSpecificationNineShowAs(ShowAsTemplate specificationNineShowAs) {
        this.specificationNineShowAs = specificationNineShowAs;
    }

    public String getSpecificationNineValue() {
        return specificationNineValue;
    }

    public void setSpecificationNineValue(String specificationNineValue) {
        this.specificationNineValue = specificationNineValue;
    }

    public Boolean getSpecificationNineRequired() {
        return specificationNineRequired;
    }

    public void setSpecificationNineRequired(Boolean specificationNineRequired) {
        this.specificationNineRequired = specificationNineRequired;
    }

    public Boolean getShowInRfqNineRequired() {
        return showInRfqNineRequired;
    }

    public void setShowInRfqNineRequired(Boolean showInRfqNineRequired) {
        this.showInRfqNineRequired = showInRfqNineRequired;
    }

    public Boolean getShowInAuctionNineRequired() {
        return showInAuctionNineRequired;
    }

    public void setShowInAuctionNineRequired(Boolean showInAuctionNineRequired) {
        this.showInAuctionNineRequired = showInAuctionNineRequired;
    }

    public String getSpecificationTen() {
        return specificationTen;
    }

    public void setSpecificationTen(String specificationTen) {
        this.specificationTen = specificationTen;
    }

    public ShowAsTemplate getSpecificationTenShowAs() {
        return specificationTenShowAs;
    }

    public void setSpecificationTenShowAs(ShowAsTemplate specificationTenShowAs) {
        this.specificationTenShowAs = specificationTenShowAs;
    }

    public String getSpecificationTenValue() {
        return specificationTenValue;
    }

    public void setSpecificationTenValue(String specificationTenValue) {
        this.specificationTenValue = specificationTenValue;
    }

    public Boolean getSpecificationTenRequired() {
        return specificationTenRequired;
    }

    public void setSpecificationTenRequired(Boolean specificationTenRequired) {
        this.specificationTenRequired = specificationTenRequired;
    }

    public Boolean getShowInRfqTenRequired() {
        return showInRfqTenRequired;
    }

    public void setShowInRfqTenRequired(Boolean showInRfqTenRequired) {
        this.showInRfqTenRequired = showInRfqTenRequired;
    }

    public Boolean getShowInAuctionTenRequired() {
        return showInAuctionTenRequired;
    }

    public void setShowInAuctionTenRequired(Boolean showInAuctionTenRequired) {
        this.showInAuctionTenRequired = showInAuctionTenRequired;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgCode) {
        this.rnsCatgCode = rnsCatgCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedDateTimestamp() { return createdDateTimestamp; }

    public void setCreatedDateTimestamp(Timestamp createdDateTimestamp) { this.createdDateTimestamp = createdDateTimestamp; }

    public Timestamp getLastUpdatedDateTimestamp() {
        return lastUpdatedDateTimestamp;
    }

    public void setLastUpdatedDateTimestamp(Timestamp lastUpdatedDateTimestamp) {
        this.lastUpdatedDateTimestamp = lastUpdatedDateTimestamp;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
