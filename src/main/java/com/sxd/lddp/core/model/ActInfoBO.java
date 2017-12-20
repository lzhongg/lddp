package com.sxd.lddp.core.model;

import java.util.Date;

public class ActInfoBO {

    private String actCode;
    private String actName;
    private String actOrg;
    private Date actStartDT;
    private Date actEndDT;
    private String actTyp ;
    private String actSuccNorm;
    private Date modifyDT;
    private Date createDT;

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActOrg() {
        return actOrg;
    }

    public void setActOrg(String actOrg) {
        this.actOrg = actOrg;
    }

    public Date getActStartDT() {
        return actStartDT;
    }

    public void setActStartDT(Date actStartDT) {
        this.actStartDT = actStartDT;
    }

    public Date getActEndDT() {
        return actEndDT;
    }

    public void setActEndDT(Date actEndDT) {
        this.actEndDT = actEndDT;
    }

    public String getActTyp() {
        return actTyp;
    }

    public void setActTyp(String actTyp) {
        this.actTyp = actTyp;
    }

    public String getActSuccNorm() {
        return actSuccNorm;
    }

    public void setActSuccNorm(String actSuccNorm) {
        this.actSuccNorm = actSuccNorm;
    }

    public Date getModifyDT() {
        return modifyDT;
    }

    public void setModifyDT(Date modifyDT) {
        this.modifyDT = modifyDT;
    }

    public Date getCreateDT() {
        return createDT;
    }

    public void setCreateDT(Date createDT) {
        this.createDT = createDT;
    }
}
