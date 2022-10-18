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

CREATE INDEX idx_by_username ON employee (username);

CREATE TABLE IF NOT EXISTS total_flow (
    id             UUID        PRIMARY KEY,
    begin_time     TIME        NOT NULL,
    end_time       TIME        NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS suspended_machine(
    id UUID  NOT NULL,
    machine VARCHAR(15) NOT NULL,
    PRIMARY KEY(id,machine),
    FOREIGN KEY(id) REFERENCES total_flow(id) ON DELETE CASCADE,
    CONSTRAINT chk_suspended_machine CHECK(machine
    in ('DRAYER_ONE','DRAYER_TWO','DRAYER_THREE','DRAYER_FOUR',
    'DRAYER_FIVE','DRAYER_SIX','DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS problem_detail (
    id           UUID        PRIMARY KEY,
    machine      VARCHAR(15) NOT NULL,
    begin_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    CONSTRAINT problem_detail_machine_values
    CHECK(machine in ('KILEN_ONE','KILEN_TWO',
	'KILEN_THREE','KILEN_FOUR','KILEN_FIVE',
	'DRAYER_ONE','DRAYER_TWO','DRAYER_THREE',
	'DRAYER_FOUR','DRAYER_FIVE','DRAYER_SIX',
	'DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS problems (
    id           UUID        NOT NULL,
    problem      VARCHAR(40) NOT NULL,
    PRIMARY KEY(id,problem),
    FOREIGN KEY(id) REFERENCES problem_detail(id) ON DELETE CASCADE,
    CONSTRAINT problems_problem_values
    CHECK(problem in ('P1','P2','P3','P4','P5','P6','P7','P8','P9'))
);

CREATE TABLE IF NOT EXISTS shift_id (
    id          UUID          PRIMARY KEY,
    shift_order VARCHAR(7)    NOT NULL,
    shift_date  DATE          NOT NULL,
    CONSTRAINT shift_identity UNIQUE(shift_order,shift_date),
    CONSTRAINT shift_order_values CHECK(shift_order in ('FIRST', 'SECOND', 'THIRD'))
);

CREATE INDEX idx_by_shift_order ON shift_id (shift_order);
CREATE INDEX idx_by_shift_date  ON shift_id (shift_date);

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
