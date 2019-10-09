insert into BANK(ID, NAME) values (1, 'ING');
insert into BANK(ID, NAME) values (2, 'ASN');
insert into BANK(ID, NAME) values (3, 'Rabobank');

insert into CUSTOMER(ID, NAME, AGE) values (1, 'Michiel', 31);
insert into CUSTOMER(ID, NAME, AGE) values (2, 'Hansje', 73);
insert into CUSTOMER(ID, NAME, AGE) values (3, 'Jasper', 44);
insert into CUSTOMER(ID, NAME, AGE) values (4, 'Sylvia', 32);
insert into CUSTOMER(ID, NAME, AGE) values (5, 'Marine', 30);

insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (1, 1, 1, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (2, 2, 2, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (3, 3, 3, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (4, 3, 0, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (5, 2, 0, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (6, 2, 1, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (7, 3, 1, 0);
insert into ACCOUNT(ID, BANK_ID, TYPE, BALANCE) values (8, 2, 1, 0);

insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (1, 1);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (2, 2);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (3, 3);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (4, 4);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (5, 5);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (1, 6);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (1, 7);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (1, 8);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (2, 8);
insert into CUSTOMER_ACCOUNTS(CUSTOMER_ID, ACCOUNT_ID) values (3, 8);

commit;