package com.agilejerry.springmaster.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER")
public class UserBean implements Serializable {

    private static final long serialVersionUID = -5752734270052507303L;
    private Integer userNo;
    private String userName;
    private OrgBean org;
    private Set<GroupBean> groups = new HashSet<GroupBean>(0);

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "USER_NO", unique = true, nullable = false)
    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    @Column(name = "USER_NAME", nullable = false, length = 16)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        String ret = "[User]NO:" + this.userNo + ", NAME=" + this.userName;
        if (org != null)
            ret += ", ORG=" + this.org.getName();
        return ret;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "ORG_ID", nullable = true)
    public OrgBean getOrg() {
        return org;
    }

    public void setOrg(OrgBean org) {
        this.org = org;
    }

    // @ManyToMany(fetch =
    // FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "GROUP_MEMBER", joinColumns = {
            @JoinColumn(name = "MEMBER_ID", nullable = true, updatable = true) }, inverseJoinColumns = {
                    @JoinColumn(name = "GROUP_ID", nullable = true, updatable = true) })
    public Set<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupBean> groups) {
        this.groups = groups;
    }

}
