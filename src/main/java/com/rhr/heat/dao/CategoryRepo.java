package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.CategoryRowMapper;
import com.rhr.heat.entity.Category;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepo {
    private final JdbcTemplate jdbcTemplate;
    
	public List<Category> findAll() {
		return jdbcTemplate.query(
				"SELECT c.* FROM category c",
				new CategoryRowMapper());
	}
    
	public List<String> findAllNames() {
		return jdbcTemplate.queryForList(
				"SELECT c.cat_name FROM category c",String.class);
	}
	
	public Optional<Category> findByName(String name) {
		return jdbcTemplate.query("""
				SELECT c.* FROM category c
				WHERE c.cat_name = ?
				""",
				new CategoryRowMapper(), name).stream().findFirst();
	}
	
	public List<String> findHasMachinesNames() {
		return jdbcTemplate.queryForList("""
					SELECT cat_name FROM category
					WHERE has_machines = true
				""",String.class);
	}
	
	public int deleteName(String name) {
		return jdbcTemplate.update(
        "DELETE FROM category c WHERE c.cat_name = ?", name);
	}

	public List<Pushable> saveAll(List<Category> categories) {
		List<Pushable> result = new ArrayList<>();
		for (Category category: categories) {
			result.addAll(save(category));
		}
		return result;
	}

	public List<Pushable> save(Category category) {
		List<Pushable> result = category.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO category
					(cat_name,has_machines,has_temperature) VALUES(?,?,?)
					ON CONFLICT(cat_name) DO NOTHING
					""",
					category.getName(),
                    category.getHasMachines(),
                    category.getHasTemperature());
		}
		return result;
	}
}