BEGIN TRANSACTION;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS teams(
    id uuid DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    threshold SMALLINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

COMMIT;