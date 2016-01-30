package com.agilejerry.springmaster.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TGROUP")
public class GroupBean implements java.io.Serializable {

	private static final long serialVersionUID = 6794699691234549462L;
	private Integer id;
	private String name;
	private String type;
	
	
	
	public GroupBean(String name) {
		this.name = name;
	}

	public GroupBean() {
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
	@Column(name="GROUP_NAME")
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return "[GROUP]ID:" + this.id +", NAME:" + this.name + ", TYPE:" + this.type;
	}

	private Set<UserBean> users = new HashSet<UserBean>(0);
	
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups",cascade=(CascadeType.ALL))
	@ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "GROUP_MEMBER", joinColumns = { 
			@JoinColumn(name = "GROUP_ID", nullable = true, updatable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "MEMBER_ID", 
					nullable = true, updatable = true) })
	public Set<UserBean> getUsers() {
		return users;
	}
	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}

	@Column(name="GROUP_TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
