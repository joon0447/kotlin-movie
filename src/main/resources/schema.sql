CREATE TABLE IF NOT EXISTS movie (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    running_time_minutes INT NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_screening (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id VARCHAR(64) NOT NULL,
    screen_start TIMESTAMP NOT NULL,
    screen_end TIMESTAMP NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    screening_id BIGINT NOT NULL,
    seat_row VARCHAR(1) NOT NULL,
    seat_column INT NOT NULL,
    total_price INT,
    used_point INT,
    payment_method VARCHAR(20),
    FOREIGN KEY (screening_id) REFERENCES movie_screening(id),
    UNIQUE (screening_id, seat_row, seat_column)
);