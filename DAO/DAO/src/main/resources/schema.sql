DROP TABLE IF EXISTS books,authors;

CREATE TABLE authors(
    id BIGINT NOT NULL,
    name text,
    age integer,
    CONSTRAINT author_pkey PRIMARY KEY(id)
);

CREATE TABLE books(
    isbn TEXT NOT NULL,
    title TEXT,
    author_id BIGINT,
    CONSTRAINT books_pkey PRIMARY KEY(isbn),
    CONSTRAINT author_fkey FOREIGN KEY(author_id) REFERENCES authors (id)
);