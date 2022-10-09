CREATE TABLE employee (
    id           BIGSERIAL   NOT NULL PRIMARY KEY,
	first_name   VARCHAR(40) NOT NULL,
	middle_name  VARCHAR(40) NOT NULL,
	last_name    VARCHAR(40) NOT NULL,
    emp_position varchar(20) NOT NULL
);

CREATE TABLE total_flow (
    id             BIGSERIAL   NOT NULL PRIMARY KEY,
    consumers_case varchar(20) NOT NULL,
    begin_hour     INTEGER     NOT NULL,
    begin_minute   INTEGER     NOT NULL,
    end_hour       INTEGER     NOT NULL,
    end_minute     INTEGER     NOT NULL,
    min_flow       INTEGER     NOT NULL,
    max_flow       INTEGER     NOT NULL
);

CREATE TABLE problem_detail (
    id           BIGSERIAL   NOT NULL PRIMARY KEY,
    problem      varchar(40) NOT NULL,
    machine      varchar(20) NOT NULL,
    begin_hour   INTEGER     NOT NULL,
    begin_minute INTEGER     NOT NULL,
    end_hour     INTEGER     NOT NULL,
    end_minute   INTEGER     NOT NULL
);

CREATE TABLE shift_id (
    id          BIGINT     PRIMARY KEY,
    shift_order varchar(20)   NOT NULL,
    my_year     INTEGER       NOT NULL,
    my_month    INTEGER       NOT NULL,
    my_day      INTEGER       NOT NULL,
    CONSTRAINT shift_identity UNIQUE(shift_order,my_year,my_month,my_day)
);

CREATE TABLE shift (
    id       BIGINT       PRIMARY KEY,
    max_temp INTEGER      NOT NULL,
    min_temp INTEGER      NOT NULL,
    notes    VARCHAR(255),
    FOREIGN KEY(id) REFERENCES shift_id(id) ON DELETE CASCADE
);

CREATE TABLE shift_problem (
    shift_id   BIGINT        NOT NULL,
    problem_id BIGINT        NOT NULL,
    PRIMARY KEY(shift_id,problem_id),
    FOREIGN KEY(shift_id)    REFERENCES shift(id)          ON DELETE CASCADE,
    FOREIGN KEY(problem_id)  REFERENCES problem_detail(id) ON DELETE CASCADE
);

CREATE TABLE shift_total_flow (
    shift_id BIGINT        NOT NULL,
    flow_id  BIGINT        NOT NULL,
    PRIMARY  KEY(shift_id,flow_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(id)       ON DELETE CASCADE,
    FOREIGN  KEY(flow_id)  REFERENCES total_flow(id)  ON DELETE CASCADE
);

CREATE TABLE shift_employee (
    shift_id BIGINT        NOT NULL,
    emp_id   BIGINT        NOT NULL,
    PRIMARY  KEY(shift_id,emp_id),
    FOREIGN  KEY(shift_id) REFERENCES shift(id)    ON DELETE CASCADE,
    FOREIGN  KEY(emp_id)   REFERENCES employee(id) ON DELETE CASCADE
);
