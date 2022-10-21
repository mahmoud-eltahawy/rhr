package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.MachineProfileRowMapper;
import com.rhr.heat.dao.rowMappers.ProblemProfileRowMapper;
import com.rhr.heat.dao.rowMappers.ProblemRowMapper;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.model.MachineProfile;
import com.rhr.heat.model.ProblemProfile;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemRepo {
	private final JdbcTemplate jdbcTemplate;

	public List<Problem> findAll(){
		String sql = "SELECT * FROM problem";
		return jdbcTemplate.query(sql, new ProblemRowMapper());
	}

	public List<Problem> findProblemDetailProblems(UUID id){
		String sql = "SELECT p.title , p.description FROM problem p "
				+ "join problem_detail_problem pdp on p.title = pdp.problem_title "
				+ "where pdp.problem_detail_id = ?";
		return jdbcTemplate.query(sql, new ProblemRowMapper(),id);
	}
	
	public List<ProblemProfile> findProblemsProfiles(String p,Integer begin,Integer end){
		return jdbcTemplate.query("select si.shift_date ,si.shift_order, "
				+ "pd.machine ,pd.begin_time ,pd.end_time "
				+ "from problem p "
				+ "join problem_detail_problems pdp "
				+ "on p.title = pdp.problem_title "
				+ "ioin problem_detail pd "
				+ "on pd.id = pdp.problem_detail_id "
				+ "join shift_problem sp on sp.problem_id = pd.id "
				+ "join shift_id si on si.id = sp.shift_id "
				+ "where p.id in (select id "
				+ "from problems p where p.title = ?) "
				+ "order by si.shift_date desc offset ? limit ?",
				new ProblemProfileRowMapper(),p,begin,end);
	}
	
	public List<MachineProfile> findMachinesProfiles(String p,Integer begin,Integer end){
		return jdbcTemplate.query("select si.shift_date ,si.shift_order, "
				+ "pd.begin_time ,pd.end_time,pd.id "
				+ "from problem_detail pd "
				+ "join shift_problem sp on sp.problem_id = pd.id "
				+ "join shift_id si on si.id = sp.shift_id "
				+ "where pd.id in (select id "
				+ "from problem_detail pd2 where pd2.machine  = ?) "
				+ "order by si.shift_date desc offset ? limit ?",
				new MachineProfileRowMapper(),p,begin,end).stream().map(m -> {
					m.setProblems(findProblemDetailProblems(m.getId()));
					return m;
				}).collect(Collectors.toList());
	}
	
	public Optional<Problem> findByTitle(String title) {
		String sql = "SELECT * FROM problem WHERE title = ?";
		return jdbcTemplate.query(sql,
				new ProblemRowMapper(),title).stream().findFirst();
	}
	
	public int deleteByTitle(String title) {
		String sql = "DELETE FROM problem WHERE title = ?";
		return jdbcTemplate.update(sql,title);
	}

	public List<String> saveAll(List<Problem> problems) {
		return problems.stream().map(p -> save(p))
				.collect(Collectors.toList());
	}

	public String save(Problem p) {
		jdbcTemplate.update("INSERT INTO problem(title,description) VALUES(?,?)",
							p.getTitle(),
							p.getDescription());
		return p.getTitle();
	}
}