# Make sure your table data is empty before inserting the data

INSERT INTO CARD
(CARD_ID, CARD_NO, CARD_EXPIRY_YEAR, CARD_EXPIRY_MONTH, CVV, CARDHOLDER_NAME)
VALUES
(20000, 2222400070000005, 2030, 03, 737, 'Shaun'),
(20001, 5555341244441115, 2033, 02, 736, 'Jackson'),
(20002, 5577000055770004, 2026, 03, 237, 'Joe'),
(20003, 5555444433331111, 2029, 01, 731, 'Jolly'),
(20004, 2222410740360010, 2032, 03, 722, 'Molly'),
(20005, 5555555555554444, 2031, 05, 937, 'May'),
(20006, 2222410700000002, 2030, 03, 927, 'John'),
(20007, 2222400010000008, 2030, 07, 934, 'Sam'),
(20008, 2223000048410010, 2030, 03, 833, 'Carl'),
(20009, 2222400060000007, 2036, 09, 812, 'Samuel'),
(20010, 2223520443560010, 2030, 04, 233, 'Zack'),
(20011, 2222400030000004, 2029, 02, 234, 'Ally'),
(20012, 5100060000000002, 2028, 03, 237, 'Ken'),
(20013, 2222400050000009, 2027, 06, 334, 'Jacky'),
(20014, 4988438843884305, 2025, 03, 337, 'Violet');

SELECT * FROM CARD;

INSERT INTO CUSTOMER
(CUSTOMER_ID, CUSTOMER_F_NAME, CUSTOMER_L_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NO, CARD_ID)
VALUES
(10000, 'Shaun', 'Liew', 'shaun@gmail.com', '+60161234567', 20000),
(10001, 'Jackson', 'Liew', 'jackson@gmail.com', '+60161234444', 20001),
(10002, 'Joe', 'Lum', 'joe@gmail.com', '+60161234568', 20002),
(10003, 'Jolly', 'Lim', 'jolly@gmail.com', '+60161288567', 20003),
(10004, 'Molly', 'Lau', 'molly@gmail.com', '+60161299567', 20004),
(10005, 'May', 'Liew', 'may@gmail.com', '+60161234511', 20005),
(10006, 'John', 'Lee', 'john@gmail.com', '+60161232267', 20006),
(10007, 'Sam', 'Teh', 'sam@gmail.com', '+60161234500', 20007),
(10008, 'Carl', 'Teh', 'carl@gmail.com', '+60161234667', 20008),
(10009, 'Samuel', 'Teh', 'samuel@gmail.com', '+60162334567', 20009),
(10010, 'Zack', 'Chee', 'zack@gmail.com', '+60161444567', 20010),
(10011, 'Ally', 'Chai', 'ally@gmail.com', '+60161324567', 20011),
(10012, 'Ken', 'Ho', 'ken@gmail.com', '+60161234327', 20012),
(10013, 'Jacky', 'Chan', 'jacky@gmail.com', '+60161231767', 20013),
(10014, 'Violet', 'Evergarden', 'violet@gmail.com', '+60161944567', 20014);

SELECT * FROM CUSTOMER;

INSERT INTO RENTAL
(RENTAL_ID, CUSTOMER_ID, RENTAL_DATE, RENTAL_STATUS, TOTAL_RENTAL_COST)
VALUES
(30000, 10000, '2022-01-01', 'RETURNED', 10.00),
(30001, 10001, '2022-01-02', 'RETURNED', 10.00),
(30002, 10002, '2022-01-03', 'RETURNED', 10.00),
(30003, 10003, '2022-01-04', 'RETURNED', 10.00),
(30004, 10004, '2022-01-05', 'RETURNED', 20.00),
(30005, 10005, '2022-01-06', 'RETURNED', 20.00),
(30006, 10006, '2022-01-07', 'RETURNED', 10.00),
(30007, 10007, '2022-01-08', 'RETURNED', 10.00),
(30008, 10008, '2022-01-09', 'RETURNED', 20.00),
(30009, 10009, '2022-01-10', 'RETURNED', 10.00),
(30010, 10010, '2022-01-11', 'RETURNED', 10.00),
(30011, 10011, '2022-01-12', 'RETURNED', 10.00),
(30012, 10012, '2022-01-13', 'RETURNED', 30.00),
(30013, 10013, '2022-01-14', 'RETURNED', 10.00),
(30014, 10014, '2022-01-15', 'RETURNED', 20.00);

SELECT * FROM RENTAL;

INSERT INTO ALBUM
(ALBUM_ID, ALBUM_NAME, ARTIST, GENRE, YEAR_OF_RELEASE, QUANTITY_ON_HAND, ALBUM_UNIT_PRICE)
VALUES
(40000, 'Senorita', 'Shawn Mendes', 'canadian pop', 2019, 50, 10.00),
(40001, 'boyfriend (with Social House)', 'Ariana Grande', 'dance pop', 2019, 50, 10.00),
(40002, 'Beautiful People (feat. Khalid)', 'Ed Sheeran', 'pop', 2019, 50, 10.00),
(40003, 'I Don''t Care (with Justin Bieber)', 'Ed Sheeran', 'pop', 2019, 50, 10.00),
(40004, 'bad guy', 'Billie Eilish', 'electropop', 2019, 50, 10.00),
(40005, 'If I Can''t Have You', 'Shawn Mendes', 'canadian pop', 2019, 50, 10.00),
(40006, 'Takeaway', 'The Chainsmokers', 'edm', 2019, 50, 10.00),
(40007, '7 rings', 'Ariana Grande', 'dance pop', 2019, 50, 10.00),
(40008, 'Never Really Over', 'Katy Perry', 'dance pop', 2019, 50, 10.00),
(40009, 'Antisocial (with Travis Scott)', 'Ed Sheeran', 'pop', 2019, 50, 10.00),
(40010, 'You Need To Calm Down', 'Taylor Swift', 'dance pop', 2019, 50, 10.00),
(40011, 'One Thing Right', 'Marshmello', 'brostep', 2019, 50, 10.00),
(40012, 'Happier', 'Marshmello', 'brostep', 2019, 50, 10.00),
(40013, 'Call You Mine', 'The Chainsmokers', 'edm', 2019, 50, 10.00),
(40014, 'Cross Me (feat. Chance the Rapper & PnB Rock)', 'Ed Sheeran', 'pop', 2019, 50, 10.00);

SELECT * FROM ALBUM;

### Bug: Unable to insert into table, don't run this command first
INSERT INTO ALBUM_RENTAL
(LINE_NUMBER, RENTAL_ID, ALBUM_ID, QUANTITY_ALBUM_RENTED, ALBUM_UNIT_PRICE_WHEN_RENTED, TOTAL_ALBUM_COST, RENTAL_STATUS_ALBUM_RENTAL)
VALUES
(50000, 30000, 40000, 1, 10.00, 10.00, 'RETURNED'),
(50001, 30001, 40001, 1, 10.00, 10.00, 'RETURNED'),
(50002, 30002, 40002, 1, 10.00, 10.00, 'RETURNED'),
(50003, 30003, 40003, 1, 10.00, 10.00, 'RETURNED'),
(50004, 30004, 40004, 1, 10.00, 10.00, 'RETURNED'),
(50005, 30004, 40005, 1, 10.00, 10.00, 'RETURNED'),
(50006, 30005, 40006, 1, 10.00, 10.00, 'RETURNED'),
(50007, 30005, 40007, 1, 10.00, 10.00, 'RETURNED'),
(50008, 30006, 40008, 1, 10.00, 10.00, 'RETURNED'),
(50009, 30007, 40009, 1, 10.00, 10.00, 'RETURNED'),
(50010, 30008, 40010, 2, 10.00, 20.00, 'RETURNED'),
(50011, 30009, 40011, 1, 10.00, 10.00, 'RETURNED'),
(50012, 30010, 40012, 1, 10.00, 10.00, 'RETURNED'),
(50013, 30011, 40013, 1, 10.00, 10.00, 'RETURNED'),
(50014, 30012, 40014, 1, 10.00, 10.00, 'RETURNED'),
(50015, 30012, 40000, 2, 10.00, 20.00, 'RETURNED'),
(50016, 30013, 40001, 1, 10.00, 10.00, 'RETURNED'),
(50017, 30014, 40002, 2, 10.00, 20.00, 'RETURNED');

SELECT * FROM ALBUM_RENTAL;

