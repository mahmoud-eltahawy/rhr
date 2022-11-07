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

	public List<Problem> findAll(){
		String sql = "SELECT * FROM problem";
		return jdbcTemplate.query(sql, new ProblemRowMapper());
	}

	public List<String> findAllTitles(){
		String sql = "SELECT p.title FROM problem p";
		return jdbcTemplate.queryForList(sql,String.class);
	}

	public List<Problem> findProblemDetailProblems(UUID id){
		String sql = "SELECT p.title , p.description FROM problem p "
				+ "join problem_detail_problem pdp on p.title = pdp.problem_title "
				+ "where pdp.problem_detail_id = ?";
		return jdbcTemplate.query(sql, new ProblemRowMapper(),id);
	}
	
	public List<ProblemProfile> findProblemsProfiles(String pr,Integer begin,Integer end){
		return jdbcTemplate.query("select s.shift_date ,s.shift_order, "
				+ "m.catagory, m.num, pd.begin_time ,pd.end_time "
				+ "from problem p "
				+ "join problem_detail_problem pdp "
				+ "on p.title = pdp.problem_title "
				+ "join problem_detail pd "
				+ "on pd.id = pdp.problem_detail_id "
				+ "join shift_problem sp on sp.problem_id = pd.id "
				+ "join shift s on s.id = sp.shift_id "
				+ "join machine m on m.id = pd.machine_id "
				+ "where p.title = ? "
				+ "order by s.shift_date desc offset ? limit ?;",
				new ProblemProfileRowMapper(),pr,begin,end);
	}
	
	public List<MachineProfile> findMachinesProfiles(UUID id,Integer begin,Integer end){
		return jdbcTemplate.query("select s.shift_date ,s.shift_order, "
				+ "pd.begin_time ,pd.end_time,pd.id "
				+ "from problem_detail pd "
				+ "join shift_problem sp on sp.problem_id = pd.id "
				+ "join shift s on s.id = sp.shift_id "
				+ "join machine m on m.id = pd.machine_id "
				+ "where m.id = ? "
				+ "order by s.shift_date desc offset ? limit ?",
				new MachineProfileRowMapper(),id,begin,end)
				.stream().map(c -> { c.setProblems(findProblemDetailProblems(c.getId()));
					return c;
				}).collect(Collectors.toList());
	}
	
	public List<MachineProfile> findMachinesProfiles(Machine m,Integer begin,Integer end){
		return jdbcTemplate.query(
				"select s.shift_date ,s.shift_order, "
				+ "pd.begin_time ,pd.end_time,pd.id "
				+ "from problem_detail pd "
				+ "join shift_problem sp on sp.problem_id = pd.id "
				+ "join shift_id s on s.id = sp.shift_id "
				+ "join machine m on m.id = pd.machine_id "
				+ "where m.catagory = ? and m.num = ? "
				+ "order by s.shift_date desc offset ? limit ?",
				new MachineProfileRowMapper(),m.getCatagory(),m.getNumber(),begin,end)
				.stream().map(c -> { c.setProblems(findProblemDetailProblems(c.getId()));
					return c;
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