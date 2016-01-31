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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="GROUP_MEMBER")
public class GroupMemberBean implements Serializable {

    private static final long serialVersionUID = 8286824404364885632L;

	private UserBean member;
	private GroupBean group;
	private Integer id;
	

    @Id
    @GeneratedValue
    @Column(name="ID", unique=false, nullable=false)
    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }

	

	@Override
	public String toString(){
		return "[GROUPMEMBER]GROUP:" + this.group.getName() +", MEMBER:" + this.member.getUserName();
	}




    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "GROUP_ID", nullable = true)
    public GroupBean getGroup() {
        return group;
    }



    public void setGroup(GroupBean group) {
        this.group = group;
    }


    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "MEMBER_ID", nullable = true)
    public UserBean getMember() {
        return member;
    }



    public void setMember(UserBean member) {
        this.member = member;
    }
	
	
}
