insert into cozinha (nome) values ('Tailandesa')
insert into cozinha (nome) values ('Chinesa')

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Restaurante la Prata', 10, 2)
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Restaurante Carbonara', 15, 2)

insert into estado(nome) values ("Minas Gerais")
insert into estado(nome) values ("São Paulo")
insert into estado(nome) values ("Rio de Janeiro")
insert into estado(nome) values ("Acre")

insert into cidade(nome, estado_id) values ("Urucuia", 1)
insert into cidade(nome, estado_id) values ("São Paulo", 2)
insert into cidade(nome, estado_id) values ("Santos", 3)
insert into cidade(nome, estado_id) values ("Rio Branco", 4)