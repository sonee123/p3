package rightchamps.modal;

import rightchamps.domain.RnsCatgMaster;

public class RnsQuotationSearch {
    private long id;
    private String title;
    private String projectType;
    private RnsCatgMaster catgCode;
    private String sourceTeam;
    private String dateFrom;
    private String dateTo;
    private String type;
    private String rfqStatus;
    private String rfbStatus;
    private String workflowStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public RnsCatgMaster getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(RnsCatgMaster catgCode) {
        this.catgCode = catgCode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getSourceTeam() {
        return sourceTeam;
    }

    public void setSourceTeam(String sourceTeam) {
        this.sourceTeam = sourceTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRfqStatus() {
        return rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public String getRfbStatus() {
        return rfbStatus;
    }

    public void setRfbStatus(String rfbStatus) {
        this.rfbStatus = rfbStatus;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
}
