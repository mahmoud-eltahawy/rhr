CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS employee (
    id           uuid DEFAULT uuid_generate_v4(),
	first_name   VARCHAR(40)  NOT NULL,
	middle_name  VARCHAR(40)  NOT NULL,
	last_name    VARCHAR(40)  NOT NULL,
    emp_position VARCHAR(20)  NOT NULL,
    username     VARCHAR(200) NOT NULL,
    password     VARCHAR(400) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT emp_unique_username UNIQUE(username)
);

CREATE INDEX idx_by_username ON employee (username);

CREATE TABLE IF NOT EXISTS total_flow (
    id             uuid DEFAULT uuid_generate_v4(),
    begin_time     TIME        NOT NULL,
    end_time       TIME        NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS suspended_machine(
    id uuid  NOT NULL,
    machine VARCHAR(15) NOT NULL,
    PRIMARY KEY(id,machine),
    FOREIGN KEY(id) REFERENCES total_flow(id) ON DELETE CASCADE,
    CONSTRAINT chk_suspended_machine CHECK(machine
    in ('DRAYER_ONE','DRAYER_TWO','DRAYER_THREE','DRAYER_FOUR',
    'DRAYER_FIVE','DRAYER_SIX','DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS problem_detail (
    id           uuid DEFAULT uuid_generate_v4(),
    machine      VARCHAR(15) NOT NULL,
    begin_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT problem_detail_machine_values
    CHECK(machine in ('KILEN_ONE','KILEN_TWO',
	'KILEN_THREE','KILEN_FOUR','KILEN_FIVE',
	'DRAYER_ONE','DRAYER_TWO','DRAYER_THREE',
	'DRAYER_FOUR','DRAYER_FIVE','DRAYER_SIX',
	'DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS problems (
    id           uuid        NOT NULL,
    problem      VARCHAR(40) NOT NULL,
    PRIMARY KEY(id,problem),
    FOREIGN KEY(id) REFERENCES problem_detail(id) ON DELETE CASCADE,
    CONSTRAINT problems_problem_values
    CHECK(problem in ('P1','P2','P3','P4','P5','P6','P7','P8','P9'))
);

CREATE TABLE IF NOT EXISTS shift_id (
    id          uuid DEFAULT uuid_generate_v4(),
    shift_order VARCHAR(7)    NOT NULL,
    shift_date  DATE          NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT shift_identity UNIQUE(shift_order,shift_date),
    CONSTRAINT shift_order_values CHECK(shift_order in ('FIRST', 'SECOND', 'THIRD'))
);

CREATE INDEX idx_by_shift_order ON shift_id (shift_order);
CREATE INDEX idx_by_shift_date  ON shift_id (shift_date);

CREATE TABLE IF NOT EXISTS shift (
    shift_id uuid         PRIMARY KEY,
    max_temp INTEGER      NOT NULL,
    min_temp INTEGER      NOT NULL,
    notes    VARCHAR(255),
    FOREIGN KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_problem (
    shift_id   uuid        NOT NULL,
    problem_id uuid        NOT NULL,
    PRIMARY KEY(shift_id,problem_id),
    FOREIGN KEY(shift_id)    REFERENCES shift(shift_id)    ON DELETE CASCADE,
    FOREIGN KEY(problem_id)  REFERENCES problem_detail(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_total_flow (
    shift_id uuid        NOT NULL,
    flow_id  uuid        NOT NULL,
    PRIMARY  KEY(shift_id,flow_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(flow_id)  REFERENCES total_flow(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_employee (
    shift_id uuid        NOT NULL,
    emp_id   uuid        NOT NULL,
    PRIMARY  KEY(shift_id,emp_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(emp_id)   REFERENCES employee(id)    ON DELETE CASCADE
);
