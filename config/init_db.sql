CREATE TABLE IF NOT EXISTS resume
(
  uuid      CHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT                 NOT NULL
);
CREATE TABLE IF NOT EXISTS contact
(
  id          SERIAL   NOT NULL,
  resume_uuid CHAR(36) NOT NULL,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL,
  CONSTRAINT contact_resume_uuid_fk FOREIGN KEY (resume_uuid)
  REFERENCES resume ON DELETE CASCADE
);
CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

