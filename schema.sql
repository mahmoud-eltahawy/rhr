CREATE TABLE employee (
    id           BIGSERIAL   NOT NULL PRIMARY KEY,
	first_name   VARCHAR(40) NOT NULL,
	middle_name  VARCHAR(40) NOT NULL,
	last_name    VARCHAR(40) NOT NULL,
    emp_position INTEGER     NOT NULL
);

CREATE TABLE total_flow (
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    atms_case  INTEGER   NOT NULL,
    begin_time TIME      NOT NULL,
    end_time   TIME      NOT NULL,
    min_flow   INTEGER   NOT NULL,
    max_flow   INTEGER   NOT NULL
);

CREATE TABLE problem_detail (
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    problem    INTEGER   NOT NULL,
    machine    INTEGER   NOT NULL,
    begin_time TIME      NOT NULL,
    end_time   TIME      NOT NULL
);

CREATE TABLE shift_id (
    id          BIGSERIAL PRIMARY KEY,
    shift_date  DATE      NOT NULL,
    shift_order INTEGER   NOT NULL
);

CREATE TABLE shift (
    shift_id BIGINT  PRIMARY KEY,
    max_temp INTEGER NOT NULL,
    min_temp INTEGER NOT NULL,
    notes    VARCHAR(255),
    FOREIGN KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE
);

CREATE TABLE shift_problem (
    shift_id   BIGINT NOT NULL,
    problem_id BIGINT NOT NULL,
    PRIMARY KEY(shift_id,problem_id),
    FOREIGN KEY(shift_id)    REFERENCES shift(shift_id)    ON DELETE CASCADE,
    FOREIGN KEY(problem_id)  REFERENCES problem_detail(id) ON DELETE CASCADE
);

CREATE TABLE shift_total_flow (
    shift_id BIGINT NOT NULL,
    flow_id  BIGINT NOT NULL,
    PRIMARY  KEY(shift_id,flow_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(flow_id)  REFERENCES total_flow(id)  ON DELETE CASCADE
);

CREATE TABLE shift_employee (
    shift_id BIGINT NOT NULL,
    emp_id   BIGINT NOT NULL,
    PRIMARY  KEY(shift_id,emp_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(emp_id)  REFERENCES employee(id)    ON DELETE CASCADE
);
