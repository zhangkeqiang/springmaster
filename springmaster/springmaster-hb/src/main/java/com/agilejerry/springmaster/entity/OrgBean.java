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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ORG")
public class OrgBean implements Serializable {

	private static final long serialVersionUID = -6098017345019583939L;
	private Integer id;
	private String name;
	
	private Set<UserBean> users = new HashSet<UserBean>(0);
	private Set<GroupBean> groups = new HashSet<GroupBean>(0);
	public OrgBean(String name) {
		this.name = name;
	}

	public OrgBean() {
	}


	@Id
    @GeneratedValue
	@Column(name="ID", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}


	//@Column(name="NAME", nullable=false, length=255)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return "[ORG]ID:" + this.id +", NAME=" + this.name;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "org",cascade=(CascadeType.ALL))
	public Set<UserBean> getUsers() {
		return users;
	}



	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "org",cascade=(CascadeType.ALL))
    public Set<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupBean> groups) {
        this.groups = groups;
    }
	
	
}
