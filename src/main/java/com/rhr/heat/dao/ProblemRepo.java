package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ProblemRowMapper;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemRepo {
	private final JdbcTemplate jdbcTemplate;

	public int saveToProblemDetail(String title,UUID PdId) {
		return jdbcTemplate.update("""
					INSERT INTO problem_detail_problem
					(problem_detail_id,problem_title) values(?,?)
					ON CONFLICT(problem_detail_id,problem_title) DO NOTHING
				""",PdId,title);
	}

	public void deleteFromProblemDetail(String title,UUID PdId) {
		jdbcTemplate.update("""
					DELETE FROM problem_detail_problem
					WHERE problem_detail_id =? AND problem_title =?
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
	
	public Optional<Problem> findByTitle(String title) {
		return jdbcTemplate.query("SELECT * FROM problem WHERE title = ?",
				new ProblemRowMapper(),title).stream().findFirst();
	}
	
	public int deleteByTitle(String title) {
		return jdbcTemplate.update("DELETE FROM problem WHERE title = ?",title);
	}

	public List<Pushable> saveAll(List<Problem> problems) {
		List<Pushable> result = new ArrayList<>();
		for (Problem problem : problems) {
			result.addAll(save(problem));
		}
		return result;
	}

	public List<Pushable> save(Problem p) {
		List<Pushable> result = p.isPushable();
		if(result.isEmpty()) {
			int n = jdbcTemplate.update(
					"INSERT INTO problem(title,description) VALUES(?,?)",
					p.getTitle(),
					p.getDescription());
			if(n == 0 ){
				result.add(Pushable.Problem_ALREADY_SAVED);
			}
		}
		return result;
	}
}