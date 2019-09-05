package com.java.simplejdbcrestapi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.java.simplejdbcrestapi.bean.Team;
import com.java.simplejdbcrestapi.bean.TeamFormat;

@Repository
public class TeamRepository implements TeamDao{

	private static DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	static {
		System.out.println(dataSource);
	}
	
	public List<TeamFormat> getJSONTeamList(){
		String sql  = "select * from team order by teams";
		  List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		  return teamFormat;
	}
	
	public List<TeamFormat> getXMLTeamList(){
		String sql  = "select * from team order by teams";
		  List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		  return teamFormat;
	}
	
	
	public List<TeamFormat> getTeamRange(int fromDoco, int toDoco){
		String sql  = "select * from team where doco between  ? and ? order by doco, lnid";
		  List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new Object[] {fromDoco,toDoco}, new TeamMapper());
		  return teamFormat;
	}
	
	public List<TeamFormat> getWildcardTeam(String team) {
		String uppercasename = team.toUpperCase();
		String sql = "select * from team where Teams LIKE '%" + uppercasename +"%' order by teams";
		List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		return teamFormat;
	}
	
	public String deleteTeam(int doco, int lnid) {
		String sql = "DELETE FROM Team WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] {doco, lnid});
		if(update == 1){
			return "Team is Deleted......";
		}else {
			return "No team exist";
		}
	}
	public String updateTeam(String teams, int doco, int lnid) {
		String sql = "UPDATE team set teams=? WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] {teams, doco, lnid});
		if(update == 1){
			return "Team is Updated....";
		}else {
			return "No team exist";
		}
	}
	
	public String updateTeam(String teams, String city, int doco, int lnid) {
		String sql = "UPDATE team set teams=?, city=? WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] {teams, city, doco, lnid});
		if(update == 1){
			return "Team is Updated....";
		}else {
			return "No team exist";
		}
	}
	
	public String createTeam(Team team) {
		String sql = "INSERT INTO team(doco,lnid,teams,city) VALUES(?,?,?,?)";
		int update = jdbcTemplate.update(sql, new Object[] {team.getDoco(), team.getLnid(), team.getTeams(), team.getCity()});
		
		if(update == 1){
			return "Team is created..";
		}else {
			return "No team created";
		}
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private static final class TeamMapper implements RowMapper<TeamFormat>{

		public TeamFormat mapRow(ResultSet rs, int rowNum) throws SQLException {
			TeamFormat team = new TeamFormat(rs.getInt("DOCO"),rs.getInt("LNID"),rs.getString("TEAMS"),rs.getString("CITY"));
//			 team.setDoco(rs.getInt("DOCO"));
//			 team.setLnid(rs.getInt("LNID"));
//			 team.setTeams(rs.getString("TEAMS"));
//			 team.setCity(rs.getString("CITY"));

		        return team;
		}	
	}
	
}
