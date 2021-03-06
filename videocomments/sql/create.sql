CREATE EXTENSION "uuid-ossp";

DROP TABLE IF EXISTS VIDEO_COMMENTS;

CREATE TABLE VIDEO_COMMENTS (
  ID                        UUID NOT NULL DEFAULT uuid_generate_v4() CONSTRAINT VIDEO_COMMENTS_PK PRIMARY KEY ,
  CREATED_AT                TIMESTAMP     NOT NULL,
  UPDATED_AT                TIMESTAMP     NOT NULL,
  FORMAT                    VARCHAR(10),
  VIDEO                     NUMERIC(1, 0),
  THUMBNAIL                 NUMERIC(1, 0),
  COMPLETE                  NUMERIC(1, 0)
);
