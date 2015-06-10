package com.core.bean;

import javax.persistence.*;

/**
 * TAuthority generated by hbm2java
 */
@Entity
@Table(name = "t_authority", catalog = "mvc")
public class Authority extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String authorityMark;
	private String authorityName;
	private String authorityDesc;
	private String authorityMsg;
	
	@Column(name = "AUTHORITY_MARK", nullable = false, length = 50)
	public String getAuthorityMark() {
		return this.authorityMark;
	}

	public void setAuthorityMark(String authorityMark) {
		this.authorityMark = authorityMark;
	}

	@Column(name = "AUTHORITY_NAME", nullable = false, length = 50)
	public String getAuthorityName() {
		return this.authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	@Column(name = "AUTHORITY_DESC", length = 200)
	public String getAuthorityDesc() {
		return this.authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	@Column(name = "AUTHORITY_MSG", length = 100)
	public String getAuthorityMsg() {
		return this.authorityMsg;
	}

	public void setAuthorityMsg(String authorityMsg) {
		this.authorityMsg = authorityMsg;
	}

}
