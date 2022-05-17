-- Seeder
INSERT INTO tb_pedido(id, valor_desconto, descricao) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', 0.0, 'pedido1')

INSERT INTO tb_item(id, tipo, valor, descricao, active) VALUES ('67967d13-a8e4-4204-a8ff-60c21c66f6e2', 'PRODUTO', 10.5, 'fio', true)
INSERT INTO tb_item(id, tipo, valor, descricao, active) VALUES ('22f6c4ad-104a-48cb-847c-1177ae574266', 'PRODUTO', 4.5, 'tecido', true)
INSERT INTO tb_item(id, tipo, valor, descricao, active) VALUES ('d4530f2f-f247-47a8-b779-4acdf84508a7', 'SERVICO', 50, 'costura', true)

INSERT INTO tb_pedido_item(pedido_id, item_id) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', 'd4530f2f-f247-47a8-b779-4acdf84508a7')
INSERT INTO tb_pedido_item(pedido_id, item_id) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', '22f6c4ad-104a-48cb-847c-1177ae574266')

