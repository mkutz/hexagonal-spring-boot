CREATE TABLE articles (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price_amount DECIMAL(19, 4) NOT NULL CHECK (price_amount >= 0),
    price_currency VARCHAR(3) NOT NULL
);
