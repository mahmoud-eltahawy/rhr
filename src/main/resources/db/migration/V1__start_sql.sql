CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS employee (
    id           UUID         PRIMARY KEY,
	first_name   VARCHAR(40)  NOT NULL,
	middle_name  VARCHAR(40)  NOT NULL,
	last_name    VARCHAR(40)  NOT NULL,
    emp_position VARCHAR(20)  NOT NULL,
    username     VARCHAR(200) NOT NULL,
    password     VARCHAR(400) NOT NULL,
    CONSTRAINT emp_unique_username UNIQUE(username)
);

CREATE INDEX IF NOT EXISTS idx_by_username ON employee(username);

CREATE TABLE IF NOT EXISTS machine(
    id       UUID        PRIMARY KEY,
    catagory VARCHAR(20) NOT NULL,
    num      INTEGER     NOT NULL,
    CONSTRAINT unique_machine_identity UNIQUE(catagory,num)
);

CREATE TABLE IF NOT EXISTS total_flow (
    id             UUID        PRIMARY KEY,
    begin_time     TIME        NOT NULL,
    end_time       TIME        NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS total_flow_machine(
    total_flow_id UUID  NOT NULL,
    machine_id    UUID  NOT NULL,
    PRIMARY KEY(total_flow_id,machine_id),
    FOREIGN KEY(total_flow_id) REFERENCES total_flow(id) ON DELETE CASCADE,
    FOREIGN KEY(machine_id)    REFERENCES machine(id)    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS problem_detail (
    id           UUID       PRIMARY KEY,
    machine_id   UUID       NOT NULL,
    begin_time   TIME       NOT NULL,
    end_time     TIME       NOT NULL,
    FOREIGN KEY(machine_id) REFERENCES machine(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS problem (
    title        VARCHAR(100) PRIMARY KEY,
    description  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS problem_detail_problem (
    problem_detail_id UUID     NOT NULL,
    problem_title VARCHAR(100) NOT NULL,
    PRIMARY KEY(problem_detail_id,problem_title),
    FOREIGN KEY(problem_detail_id) REFERENCES problem_detail(id) ON DELETE CASCADE,
    FOREIGN KEY(problem_title)     REFERENCES problem(title)     ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_id (
    id          UUID          PRIMARY KEY,
    shift_order VARCHAR(7)    NOT NULL,
    shift_date  DATE          NOT NULL,
    CONSTRAINT shift_identity UNIQUE(shift_order,shift_date),
    CONSTRAINT shift_order_values CHECK(shift_order in ('FIRST', 'SECOND', 'THIRD'))
);

CREATE INDEX IF NOT EXISTS idx_by_shift_order ON shift_id (shift_order);
CREATE INDEX IF NOT EXISTS idx_by_shift_date  ON shift_id (shift_date);

CREATE TABLE IF NOT EXISTS shift (
    shift_id UUID         PRIMARY KEY,
    max_temp INTEGER      NOT NULL,
    min_temp INTEGER      NOT NULL,
    notes    VARCHAR(255),
    FOREIGN KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_problem (
    shift_id   UUID        NOT NULL,
    problem_id UUID        NOT NULL,
    PRIMARY KEY(shift_id,problem_id),
    FOREIGN KEY(shift_id)    REFERENCES shift_id(id)       ON DELETE CASCADE,
    FOREIGN KEY(problem_id)  REFERENCES problem_detail(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_total_flow (
    shift_id UUID        NOT NULL,
    flow_id  UUID        NOT NULL,
    PRIMARY  KEY(shift_id,flow_id),
    FOREIGN  KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE,
    FOREIGN  KEY(flow_id)  REFERENCES total_flow(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_employee (
    shift_id UUID        NOT NULL,
    emp_id   UUID        NOT NULL,
    PRIMARY  KEY(shift_id,emp_id),
    FOREIGN  KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE,
    FOREIGN  KEY(emp_id)   REFERENCES employee(id)    ON DELETE CASCADE
);
