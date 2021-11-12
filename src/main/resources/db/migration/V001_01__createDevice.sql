CREATE TABLE t_device
(
    id                   UUID NOT NULL,
    operating_system     TEXT NOT NULL,
    user_id              TEXT NOT NULL,
    device_id            TEXT NOT NULL,
    created_on           TIMESTAMP NOT NULL,
    updated_on           TIMESTAMP
);

ALTER TABLE t_device
    ADD CONSTRAINT PK_t_device PRIMARY KEY (id),
    ADD CONSTRAINT UQ_user_id_device_id UNIQUE (user_id, device_id);
