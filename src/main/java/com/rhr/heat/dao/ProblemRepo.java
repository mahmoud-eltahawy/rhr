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
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.model.MachineProfile;
import com.rhr.heat.model.ProblemProfile;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemRepo {
	private final JdbcTemplate jdbcTemplate;

	public void saveToProblemDetail(String title,UUID PdId) {
		jdbcTemplate.update("""
					INSERT INTO problem_detail_problem
					(problem_detail_id,problem_title) values(?,?)
				""",PdId,title);
	}

	public List<Problem> findProblemDetailProblems(UUID id){
		return jdbcTemplate.query("""
				SELECT p.* FROM problem p
				join problem_detail_problem pdp on p.title = pdp.problem_title
				where pdp.problem_detail_id = ?
				""", new ProblemRowMapper(),id);
	}

	public List<Problem> findAll(){
		return jdbcTemplate.query(
				"SELECT * FROM problem", new ProblemRowMapper());
	}

	public List<String> findAllTitles(){
		return jdbcTemplate.queryForList(
				"SELECT p.title FROM problem p",String.class);
	}
	
	public List<ProblemProfile> findProblemsProfiles(String pr,Integer begin,Integer end){
		return jdbcTemplate.query("""
				SELECT s.shift_date ,s.shift_order,
				m.category, m.num, pd.begin_time ,pd.end_time
				FROM problem p JOIN problem_detail_problem pdp
				ON p.title = pdp.problem_title
				JOIN problem_detail pd
				ON pd.id = pdp.problem_detail_id
				JOIN shift_problem sp on sp.problem_detail_id = pd.id
				JOIN shift s on s.id = sp.shift_id
				JOIN machine m on m.id = pd.machine_id
				WHERE p.title = ?
				ORDER BY s.shift_date DESC OFFSET ? LIMIT ?
				""",new ProblemProfileRowMapper(),pr,begin,end);
	}
	
	public List<MachineProfile> findMachinesProfiles(UUID id,Integer begin,Integer end){
		return jdbcTemplate.query("""
				SELECT s.shift_date ,s.shift_order,
				pd.begin_time ,pd.end_time,pd.id
				FROM problem_detail pd join shift_problem sp
				ON sp.problem_detail_id = pd.id
				JOIN shift s on s.id = sp.shift_id
				JOIN machine m on m.id = pd.machine_id
				WHERE m.id = ?
				ORDER BY s.shift_date DESC OFFSET ? LIMIT ?
				""",new MachineProfileRowMapper(),id,begin,end)
				.stream().map(c -> { c.setProblems(findProblemDetailProblems(c.getId()));
					return c;
				}).collect(Collectors.toList());
	}
	
	public List<MachineProfile> findMachinesProfiles(Machine m,Integer begin,Integer end){
		return jdbcTemplate.query("""
				SELECT s.shift_date ,s.shift_order,
				pd.begin_time ,pd.end_time,pd.id
				FROM problem_detail pd JOIN shift_problem sp
				ON sp.problem_id = pd.id
				JOIN shift_id s ON s.id = sp.shift_id
				JOIN machine m ON m.id = pd.machine_id
				WHERE m.catagory = ? and m.num = ?
				ORDER BY s.shift_date DESC OFFSET ? LIMIT ?
				""",new MachineProfileRowMapper(),
				m.getCategory(),m.getNumber(),begin,end)
				.stream().map(c -> { c.setProblems(findProblemDetailProblems(c.getId()));
					return c;
				}).collect(Collectors.toList());
	}
	
	public Optional<Problem> findByTitle(String title) {
		return jdbcTemplate.query("SELECT * FROM problem WHERE title = ?",
				new ProblemRowMapper(),title).stream().findFirst();
	}
	
	public int deleteByTitle(String title) {
		return jdbcTemplate.update("DELETE FROM problem WHERE title = ?",title);
	}

	public List<String> saveAll(List<Problem> problems) {
		return problems.stream().map(p -> save(p))
				.collect(Collectors.toList());
	}

	public String save(Problem p) {
		if(p.isPushable().isEmpty()) {
		jdbcTemplate.update("INSERT INTO problem(title,description) VALUES(?,?)",
							p.getTitle(),
							p.getDescription());
		return p.getTitle();
		} else {
			return null;
		}
	}
}