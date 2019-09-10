package com.java.simplejdbcrestapi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.java.simplejdbcrestapi.bean.SalesOrderHeaderDetail;
import com.java.simplejdbcrestapi.bean.Team;
import com.java.simplejdbcrestapi.bean.TeamFormat;

import oracle.jdbc.OracleTypes;

@Repository
public class TeamRepository implements TeamDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;

	public List<SalesOrderHeaderDetail> getSalesOrderList(int fDoco, int tDoco) {

		String sql = "select sddoco, sdlnid, sdkcoo, sddcto, sdlitm, sdshpn from JDEDATA920.f4201 a inner join JDEDATA920.f4211 b on a.shdoco = b.sddoco where a.shdoco between ? and ? order by sddoco, sdlnid";
		List<SalesOrderHeaderDetail> salesOrderHeaderDetail = jdbcTemplate.query(sql, new Object[] { fDoco, tDoco },
				new SalesOrderMapper());
		List<SalesOrderHeaderDetail> updateSalesOrderHeaderDetailList = updateSOList(salesOrderHeaderDetail);
		return updateSalesOrderHeaderDetailList;

	}

	private List<SalesOrderHeaderDetail> updateSOList(List<SalesOrderHeaderDetail> salesOrderHeaderDetail) {
		String sql = "select xhssts from JDEDATA920.f4215 where xhshpn = ?";
		for (SalesOrderHeaderDetail so : salesOrderHeaderDetail) {

			String ssts = jdbcTemplate.queryForObject(sql, new Object[] { so.getShpn() }, String.class);
			so.setSsts(ssts);
			// salesOrderHeaderDetail.set(6, so);

		}

		return salesOrderHeaderDetail;
	}

	public List<TeamFormat> getJSONTeamList() {
		String sql = "select * from team order by teams";
		List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		return teamFormat;
	}

	public List<TeamFormat> getXMLTeamList() {
		String sql = "select * from team order by teams";
		List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		return teamFormat;
	}

	public List<TeamFormat> getTeamRange(int fromDoco, int toDoco) {
		String sql = "select * from team where doco between  ? and ? order by doco, lnid";
		List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new Object[] { fromDoco, toDoco }, new TeamMapper());
		return teamFormat;
	}

	public List<TeamFormat> getWildcardTeam(String team) {
		String uppercasename = team.toUpperCase();
		String sql = "select * from team where Teams LIKE '%" + uppercasename + "%' order by teams";
		List<TeamFormat> teamFormat = jdbcTemplate.query(sql, new TeamMapper());
		return teamFormat;
	}

	public String deleteTeam(int doco, int lnid) {
		String sql = "DELETE FROM Team WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] { doco, lnid });
		if (update == 1) {
			return "Team is Deleted......";
		} else {
			return "No team exist";
		}
	}

	public String updateTeam(String teams, int doco, int lnid) {
		String sql = "UPDATE team set teams=? WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] { teams, doco, lnid });
		if (update == 1) {
			return "Team is Updated....";
		} else {
			return "No team exist";
		}
	}

	public String updateTeam(String teams, String city, int doco, int lnid) {
		String sql = "UPDATE team set teams=?, city=? WHERE doco=? and lnid=?";
		int update = jdbcTemplate.update(sql, new Object[] { teams, city, doco, lnid });
		if (update == 1) {
			return "Team is Updated....";
		} else {
			return "No team exist";
		}
	}

	public String createTeam(Team team) {
		String sql = "INSERT INTO team(doco,lnid,teams,city) VALUES(?,?,?,?)";
		int update = jdbcTemplate.update(sql,
				new Object[] { team.getDoco(), team.getLnid(), team.getTeams(), team.getCity() });

		if (update == 1) {
			return "Team is created..";
		} else {
			return "No team created";
		}
	}

	public List<SalesOrderHeaderDetail> getSalesOrderDetailProc(int fDoco, int tDoco) {
		//SimpleJdbcCall simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withCatalogName("JDEDATA920")
			//	.withProcedureName("JDEPROC2");
		//SqlParameterSource parameters = new MapSqlParameterSource().addValue("FDOCO", 2544).addValue("TDOCO", 2545);
		//Map out = simpleJdbcCallRefCursor.execute(parameters);
//		Map out = null;
//		try {
//		 out = simpleJdbcCallRefCursor.execute();
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//		 System.out.println(out);
//		if (out == null) {
//			return Collections.emptyList();
//		} else {
//			return (List<SalesOrderHeaderDetail>) out.get("p_cursor");
//		}
		
		Map<String, Object> map =
				simpleJdbcCallRefCursor.withCatalogName("JDEDATA920")
					.withProcedureName("JDEPROC3")
					.withoutProcedureColumnMetaDataAccess()
		            .declareParameters(
		                    new SqlParameter("FDOCO", Types.NUMERIC),
		                    new SqlParameter("TDOCO", Types.NUMERIC),
		                    new SqlOutParameter("p_cursor", OracleTypes.CURSOR, new SalesOrderProcMapper()))
		            .execute(fDoco, tDoco);
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//		    System.out.println("This is KEY: "+entry.getKey() + " This is VALUE " + entry.getValue().toString());
//		}
		//map.forEach((key, value) -> System.out.println("This is KEY: "+key + " This is VALUE " + value));
//		for (String keys : map.keySet())  
//		{
//		   //System.out.println(keys + ":"+ map.get(keys));
//			   System.out.println(keys);
//
//		   for (Object value : map.values()) {
//			    System.out.println("Value = " + value);
//			}
//		   
//		}
		List<SalesOrderHeaderDetail> list = (List<SalesOrderHeaderDetail>) map.get("p_cursor");
		//System.out.println(list);
//		for(SalesOrderHeaderDetail s : list) {
//			System.out.println(s.getDoco()+" "+s.getLnid()+" "+s.getLitm()+" "+s.getSsts());
//		}
		return list;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallRefCursor= new SimpleJdbcCall(jdbcTemplate);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

//	@Autowired
//	public void intit() {
//		// System.out.println("Came to Data simpleJdbcCallRefCursor");
//
//		jdbcTemplate.setResultsMapCaseInsensitive(true);
//		simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withCatalogName("JDEDATA920").withProcedureName("JDEPROC3")
//				.returningResultSet("p_cursor", BeanPropertyRowMapper.newInstance(SalesOrderProcMapper.class));
//
//	}

	private static final class TeamMapper implements RowMapper<TeamFormat> {

		public TeamFormat mapRow(ResultSet rs, int rowNum) throws SQLException {
			TeamFormat team = new TeamFormat(rs.getInt("DOCO"), rs.getInt("LNID"), rs.getString("TEAMS"),
					rs.getString("CITY"));
//			 team.setDoco(rs.getInt("DOCO"));
//			 team.setLnid(rs.getInt("LNID"));
//			 team.setTeams(rs.getString("TEAMS"));
//			 team.setCity(rs.getString("CITY"));

			return team;
		}
	}

	private static final class SalesOrderMapper implements RowMapper<SalesOrderHeaderDetail> {

		public SalesOrderHeaderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			SalesOrderHeaderDetail salesOrderHeaderDetail = new SalesOrderHeaderDetail(rs.getInt("SDDOCO"),
					rs.getInt("SDLNID"), rs.getString("SDKCOO"), rs.getString("SDDCTO"), rs.getString("SDLITM").trim(),
					rs.getInt("SDSHPN"));
			return salesOrderHeaderDetail;
		}
	}

	private static final class SalesOrderProcMapper implements RowMapper<SalesOrderHeaderDetail> {

		public SalesOrderHeaderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			SalesOrderHeaderDetail salesOrderHeaderDetail = new SalesOrderHeaderDetail(rs.getInt("SDDOCO"),
					rs.getInt("SDLNID"), rs.getString("SDKCOO"), rs.getString("SDDCTO"), rs.getString("SDLITM").trim(),
					rs.getInt("SDSHPN"), rs.getString("XHSSTS"));
			return salesOrderHeaderDetail;
		}
	}

}
