package com.java.simplejdbcrestapi.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SalesOrderHeaderDetail implements Serializable {

	private int doco;
	private int lnid;
	//@JsonIgnore
	private String kcoo;
	//@JsonIgnore
	private String dcto;
	//@JsonIgnore
	private String litm;
	// private String trdj;
	//@JsonIgnore
	private int shpn;
	private String ssts;

	public SalesOrderHeaderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public SalesOrderHeaderDetail(int doco, int lnid, String kcoo, String dcto, String litm, int shpn) {
		super();
		this.doco = doco;
		this.lnid = lnid;
		this.kcoo = kcoo;
		this.dcto = dcto;
		this.litm = litm;
		this.shpn = shpn;
	}



	public SalesOrderHeaderDetail(int doco, int lnid, String kcoo, String dcto, String litm, String ssts) {
		super();
		this.doco = doco;
		this.lnid = lnid;
		this.kcoo = kcoo;
		this.dcto = dcto;
		this.litm = litm;
		this.ssts = ssts;
	}

	public SalesOrderHeaderDetail(int doco, int lnid, String kcoo, String dcto, String litm, int shpn, String ssts) {
		super();
		this.doco = doco;
		this.lnid = lnid;
		this.kcoo = kcoo;
		this.dcto = dcto;
		this.litm = litm;
		this.shpn = shpn;
		this.ssts = ssts;
	}

	public int getDoco() {
		return doco;
	}

	public void setDoco(int doco) {
		this.doco = doco;
	}

	public int getLnid() {
		return lnid;
	}

	public void setLnid(int lnid) {
		this.lnid = lnid;
	}

	public String getKcoo() {
		return kcoo;
	}

	public void setKcoo(String kcoo) {
		this.kcoo = kcoo;
	}

	public String getDcto() {
		return dcto;
	}

	public void setDcto(String dcto) {
		this.dcto = dcto;
	}

	public String getLitm() {
		return litm;
	}

	public void setLitm(String litm) {
		this.litm = litm;
	}

	public String getSsts() {
		return ssts;
	}

	public void setSsts(String ssts) {
		this.ssts = ssts;
	}

	public int getShpn() {
		return shpn;
	}

	public void setShpn(int shpn) {
		this.shpn = shpn;
	}

	@Override
	public String toString() {
		return "SalesOrderHeaderDetail [doco=" + doco + ", lnid=" + lnid + ", kcoo=" + kcoo + ", dcto=" + dcto
				+ ", litm=" + litm + ", shpn=" + shpn + ", ssts=" + ssts + "]";
	}

	

}
