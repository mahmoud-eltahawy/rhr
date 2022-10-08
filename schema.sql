CREATE TABLE employee (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	first_name VARCHAR(40) NOT NULL,
	second_name VARCHAR(40) NOT NULL,
	third_name VARCHAR(40) NOT NULL,
    emp_position INTEGER NOT NULL
)

CREATE TABLE total_flow (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    atms_case INTEGER NOT NULL,
    begin_time TIME NOT NULL,
    end_time TIME NOT NULL,
    max_flow INTEGER NOT NULL
)

CREATE TABLE problem_detail (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    problem INTEGER NOT NULL,
    machine INTEGER NOT NULL,
    begin_time TIME NOT NULL,
    end_time TIME NOT NULL
)

CREATE TABLE shift (
    shift_date DATE NOT NULL,
    shift_order INTEGER NOT NULL,
    max_temp INTEGER NOT NULL,
    min_temp INTEGER NOT NULL,
    notes VARCHAR,
    PRIMARY KEY(shift_date,shift_order)
)

CREATE TABLE shift_problem (
    shift_date DATE NOT NULL,
    shift_order INTEGER NOT NULL,
    problem_id BIGINT NOT NULL,
    PRIMARY KEY(shift_date,shift_order,problem_id),
    FOREIGN KEY(shift_date) REFERENCES shift(shift_date) ON DELETE CASCADE;
    FOREIGN KEY(shift_order) REFERENCES shift(shift_order) ON DELETE CASCADE;
    FOREIGN KEY(problem_id) REFERENCES problem_detail(id) ON DELETE CASCADE;
)

CREATE TABLE shift_total_flow (
    shift_date DATE NOT NULL,
    shift_order INTEGER NOT NULL,
    flow_id BIGINT NOT NULL,
    PRIMARY KEY(shift_date,shift_order,flow_id),
    FOREIGN KEY(shift_date) REFERENCES shift(shift_date) ON DELETE CASCADE;
    FOREIGN KEY(shift_order) REFERENCES shift(shift_order) ON DELETE CASCADE;
    FOREIGN KEY(flow_id) REFERENCES total_flow(id) ON DELETE CASCADE;
)

CREATE TABLE shift_employee (
    shift_date DATE NOT NULL,
    shift_order INTEGER NOT NULL,
    emp_id BIGINT NOT NULL,
    PRIMARY KEY(shift_date,shift_order,emp_id),
    FOREIGN KEY(shift_date) REFERENCES shift(shift_date) ON DELETE CASCADE;
    FOREIGN KEY(shift_order) REFERENCES shift(shift_order) ON DELETE CASCADE;
    FOREIGN KEY(flow_id) REFERENCES employee(id) ON DELETE CASCADE;
)
