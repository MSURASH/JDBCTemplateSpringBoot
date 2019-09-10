package com.java.simplejdbcrestapi.repository;

import java.util.List;

import com.java.simplejdbcrestapi.bean.SalesOrderHeaderDetail;
import com.java.simplejdbcrestapi.bean.Team;
import com.java.simplejdbcrestapi.bean.TeamFormat;
public interface TeamDao {

	public List<SalesOrderHeaderDetail> getSalesOrderList(int fromDoco, int toDoco);
	public List<TeamFormat> getJSONTeamList();
	public List<TeamFormat> getXMLTeamList();
	public List<TeamFormat> getWildcardTeam(String name);
	public List<TeamFormat> getTeamRange(int fromDoco, int toDoco);	
	public String deleteTeam(int doco, int lnid);
	public String updateTeam(String teams, int doco, int lnid) ;	
	public String updateTeam(String teams, String city, int doco, int lnid);	
	public String createTeam(Team team);
	public List<SalesOrderHeaderDetail> getSalesOrderDetailProc(int fromDoco, int toDoco);	
}
