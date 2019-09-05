package com.java.simplejdbcrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.simplejdbcrestapi.bean.Team;
import com.java.simplejdbcrestapi.bean.TeamFormat;
import com.java.simplejdbcrestapi.repository.TeamDao;

@RestController
@RequestMapping("team")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TeamController {

	@Autowired
	TeamDao teamDao;

	//@GetMapping("/all")
	@RequestMapping(value="/all", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public List<TeamFormat> getJSONTeamList() {
		return teamDao.getJSONTeamList();
	}

	@RequestMapping(value="/allxml", method = RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
	//@ResponseBody
	public List<TeamFormat> getXMLTeamList() {
		return teamDao.getXMLTeamList();
	}

	@PostMapping("/range")
	public List<TeamFormat> getTeamRange(@RequestBody Team team) {
		return teamDao.getTeamRange(team.getFromDoco(), team.getToDoco());
	}

	@GetMapping("/wc")
	public List<TeamFormat> getWildcardTeam(@RequestParam(name = "name") String name) {
		String uppercasename = name.toUpperCase();
		return teamDao.getWildcardTeam(uppercasename);
	}

	@PostMapping("/save")
	public String save(@RequestBody Team team) {
		return teamDao.createTeam(team);
	}

	@PutMapping("/update")
	public String update(@RequestBody Team team) {
		return teamDao.updateTeam(team.getTeams().toUpperCase(), team.getCity().toUpperCase(), team.getDoco(),
				team.getLnid());
	}

	@DeleteMapping("/delete")
	public String deleteTeam(@RequestParam("doco") int doco, @RequestParam("lnid") int lnid) {
		return teamDao.deleteTeam(doco, lnid);
	}

}
