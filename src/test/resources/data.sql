insert into BANK(ID, NAME) values (1, "ING");
insert into BANK(ID, NAME) values (2, "ASN");
insert into BANK(ID, NAME) values (3, "Rabobank");

insert into CUSTOMER(ID, NAME, AGE) values (1, "Michiel", 31);
insert into CUSTOMER(ID, NAME, AGE) values (2, "Hansje", 73);
insert into CUSTOMER(ID, NAME, AGE) values (3, "Jasper", 44);
insert into CUSTOMER(ID, NAME, AGE) values (4, "Sylvia", 32);
insert into CUSTOMER(ID, NAME, AGE) values (5, "Marine", 30);

insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (1, 1, 1, "CHECKING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (2, 2, 1, "SAVING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (3, 3, 1, "INVESTMENT");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (4, 4, 1, "SAVING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (5, 5, 1, "CHECKING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (6, 1, 2, "CHECKING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (7, 1, 3, "CHECKING");
insert into ACCOUNT(ID, CUSTOMER_ID, BANK_ID, TYPE) values (8, 1, 4, "CHECKING");

insert into TRANSACTION(ID, TO_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (1, 2, 10000, "DEPOSIT", true);
insert into TRANSACTION(ID, TO_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (2, 4, 20000, "DEPOSIT", true);
insert into TRANSACTION(ID, TO_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (3, 6, 30000, "DEPOSIT", true);
insert into TRANSACTION(ID, TO_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (4, 8, 40000, "DEPOSIT", true);

insert into TRANSACTION(ID, TO_ACCOUNT_ID, FROM_ACCOUNT_ID AMOUNT, TYPE, SUCCESS) values (5, 1, 2, 3333, "TRANSER", true);
insert into TRANSACTION(ID, TO_ACCOUNT_ID, FROM_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (6, 3, 4, 6789, "TRANSER", true);
insert into TRANSACTION(ID, TO_ACCOUNT_ID, FROM_ACCOUNT_ID, AMOUNT, TYPE, SUCCESS) values (7, 5, 4, 1234, "TRANSER", true);

commit;