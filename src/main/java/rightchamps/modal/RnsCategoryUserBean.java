package rightchamps.modal;

import java.util.List;

public class RnsCategoryUserBean {
    private String login;
    private Long userId;
    private List<RnsCatgMasterBean> rnsCatgMasterList;
    private List<RnsCatgMasterBean> rnsCatgMasterSelectedList;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<RnsCatgMasterBean> getRnsCatgMasterList() {
        return rnsCatgMasterList;
    }

    public void setRnsCatgMasterList(List<RnsCatgMasterBean> rnsCatgMasterList) {
        this.rnsCatgMasterList = rnsCatgMasterList;
    }

    public List<RnsCatgMasterBean> getRnsCatgMasterSelectedList() {
        return rnsCatgMasterSelectedList;
    }

    public void setRnsCatgMasterSelectedList(List<RnsCatgMasterBean> rnsCatgMasterSelectedList) {
        this.rnsCatgMasterSelectedList = rnsCatgMasterSelectedList;
    }
}
