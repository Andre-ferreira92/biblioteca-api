CREATE TABLE book_copies(
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    inventory_code VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_copy_book FOREIGN KEY (book_id) REFERENCES books(id)
    );