CREATE TABLE IF NOT EXISTS employee (
    id           BIGSERIAL    NOT NULL PRIMARY KEY,
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
    id             BIGSERIAL   NOT NULL PRIMARY KEY,
    begin_time     TIME        NOT NULL,
    end_time       TIME        NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS suspended_machine(
    id BIGINT NOT NULL,
    machine VARCHAR(15) NOT NULL,
    PRIMARY KEY(id,machine),
    FOREIGN KEY(id) REFERENCES total_flow(id) ON DELETE CASCADE,
    CONSTRAINT chk_suspended_machine CHECK(machine
    in ('DRAYER_ONE','DRAYER_TWO','DRAYER_THREE','DRAYER_FOUR',
    'DRAYER_FIVE','DRAYER_SIX','DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS problem_detail (
    id           BIGSERIAL   NOT NULL PRIMARY KEY,
    problem      VARCHAR(40) NOT NULL,
    machine      VARCHAR(15) NOT NULL,
    begin_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL
    CONSTRAINT problem_detail_machine_values
    CHECK(machine in ('KILEN_ONE','KILEN_TWO',
	'KILEN_THREE','KILEN_FOUR','KILEN_FIVE',
	'DRAYER_ONE','DRAYER_TWO','DRAYER_THREE',
	'DRAYER_FOUR','DRAYER_FIVE','DRAYER_SIX',
	'DRAYER_SEVEN','ATM_ONE','ATM_TWO','PROJECT'))
);

CREATE TABLE IF NOT EXISTS shift_id (
    id          BIGSERIAL     PRIMARY KEY,
    shift_order VARCHAR(7)    NOT NULL,
    shift_date  DATE          NOT NULL,
    CONSTRAINT shift_identity UNIQUE(shift_order,shift_date),
    CONSTRAINT shift_order_values CHECK(shift_order in ('FIRST', 'SECOND', 'THIRD'))
);

CREATE INDEX idx_by_shift_order ON shift_id (shift_order);
CREATE INDEX idx_by_shift_date  ON shift_id (shift_date);

CREATE TABLE IF NOT EXISTS shift (
    shift_id BIGINT       PRIMARY KEY,
    max_temp INTEGER      NOT NULL,
    min_temp INTEGER      NOT NULL,
    notes    VARCHAR(255),
    FOREIGN KEY(shift_id) REFERENCES shift_id(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_problem (
    shift_id   BIGINT        NOT NULL,
    problem_id BIGINT        NOT NULL,
    PRIMARY KEY(shift_id,problem_id),
    FOREIGN KEY(shift_id)    REFERENCES shift(shift_id)    ON DELETE CASCADE,
    FOREIGN KEY(problem_id)  REFERENCES problem_detail(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_total_flow (
    shift_id BIGINT        NOT NULL,
    flow_id  BIGINT        NOT NULL,
    PRIMARY  KEY(shift_id,flow_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(flow_id)  REFERENCES total_flow(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_employee (
    shift_id BIGINT        NOT NULL,
    emp_id   BIGINT        NOT NULL,
    PRIMARY  KEY(shift_id,emp_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(shift_id) ON DELETE CASCADE,
    FOREIGN  KEY(emp_id)   REFERENCES employee(id)    ON DELETE CASCADE
);
