--테이블 생성
create table product(
    pid     number,
    pname   varchar2(30),
    quantity number(10),
    price   number(10)
);
--기본키
alter table product add constraint product_pid primary key(pid);
--시퀀스 생성
create sequence product_pid_seq;

--등록
insert into product(pid, pname, quantity, price)
values(product_pid_seq.nextval, :pname, :quantity, :price);
--조회
select pid, pname, quantity, price
from product
where pid = :id
--수정
update product
set pname = :pname,
quantity = :quantity,
price = :price
where pid = :id;
--삭제
delete from product where pid = :id;
--목록
select pid, pname, quantity, price
from product;