CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS shift (
    id          UUID          PRIMARY KEY,
    shift_order VARCHAR(7)    NOT NULL,
    shift_date  DATE          NOT NULL,
    CONSTRAINT shift_identity UNIQUE(shift_order,shift_date),
    CONSTRAINT shift_order_values CHECK(shift_order in ('FIRST', 'SECOND', 'THIRD'))
);

CREATE INDEX IF NOT EXISTS idx_by_shift_order ON shift(shift_order);
CREATE INDEX IF NOT EXISTS idx_by_shift_date  ON shift(shift_date);

CREATE TABLE IF NOT EXISTS machine(
    id       UUID        PRIMARY KEY,
    category VARCHAR(20) NOT NULL,
    num      INTEGER     NOT NULL,
    CONSTRAINT unique_machine_identity UNIQUE(category,num)
);

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

CREATE TABLE IF NOT EXISTS problem_detail (
    id           UUID       PRIMARY KEY,
    shift_id     UUID       NOT NUll,
    machine_id   UUID       NOT NULL,
    begin_time   TIME       NOT NULL,
    end_time     TIME       NOT NULL,
    CONSTRAINT unique_problem_detail_to_shift UNIQUE(shift_id,machine_id,begin_time,end_time),
    FOREIGN KEY(machine_id) REFERENCES machine(id) ON DELETE CASCADE,
    FOREIGN KEY(shift_id)   REFERENCES shift(id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS problem (
    title        VARCHAR(100) PRIMARY KEY,
    description  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS total_flow (
    id             UUID        PRIMARY KEY,
    shift_id       UUID        NOT NUll,
    begin_time     TIME        NOT NULL,
    end_time       TIME        NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL,
    CONSTRAINT unique_total_flow_times UNIQUE(shift_id,begin_time,end_time),
    FOREIGN KEY(shift_id)   REFERENCES shift(id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS temperature (
    id         UUID    PRIMARY KEY,
    shift_id   UUID    NOT NULL,
    machine_id UUID    NOT NULL,
    max_temp   INTEGER NOT NULL,
    min_temp   INTEGER NOT NULL,
    CONSTRAINT unique_temperature_to_shift UNIQUE(shift_id,machine_id,max_temp,min_temp),
    FOREIGN KEY(machine_id) REFERENCES machine(id) ON DELETE CASCADE,
    FOREIGN KEY(shift_id)   REFERENCES shift(id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notes (
    note     varchar(200) NOT NULL,
    shift_id UUID   NOT NULL,
    PRIMARY KEY(note,shift_id),
    CONSTRAINT unique_notes_to_shift UNIQUE(note,shift_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS total_flow_machine(
    total_flow_id UUID  NOT NULL,
    machine_id    UUID  NOT NULL,
    PRIMARY KEY(total_flow_id,machine_id),
    FOREIGN KEY(total_flow_id) REFERENCES total_flow(id) ON DELETE CASCADE,
    FOREIGN KEY(machine_id)    REFERENCES machine(id)    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS problem_detail_problem (
    problem_detail_id UUID     NOT NULL,
    problem_title VARCHAR(100) NOT NULL,
    PRIMARY KEY(problem_detail_id,problem_title),
    FOREIGN KEY(problem_detail_id) REFERENCES problem_detail(id) ON DELETE CASCADE,
    FOREIGN KEY(problem_title)     REFERENCES problem(title)     ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shift_employee (
    shift_id      UUID        NOT NULL,
    employee_id   UUID        NOT NULL,
    PRIMARY  KEY(shift_id,employee_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(id) ON DELETE CASCADE,
    FOREIGN  KEY(employee_id)   REFERENCES employee(id)    ON DELETE CASCADE
);
