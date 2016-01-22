package com.agilejerry.springmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USER")
public class UserBean implements java.io.Serializable {
	private static final long serialVersionUID = -7742079576757877039L;
	
	private Integer userNo;
	private String userName;
    @Id
    @GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="USER_NO", unique=true, nullable=false)
	public Integer getUserNo() {
		return userNo;
	}



	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}


	@Column(name="USER_NAME", nullable=false, length=16)
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString(){
		return "User[NO=" + this.userNo +", NAME=" + this.userName;
	}
}
