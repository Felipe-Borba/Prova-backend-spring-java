-- Seeder
INSERT INTO tb_pedido(id, valor_desconto, descricao) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', 0.0, 'pedido1')

INSERT INTO tb_item(id, tipo, valor, descricao) VALUES ('67967d13-a8e4-4204-a8ff-60c21c66f6e2', 'produto', 10.5, 'fio')
INSERT INTO tb_item(id, tipo, valor, descricao) VALUES ('22f6c4ad-104a-48cb-847c-1177ae574266', 'produto', 4.5, 'tecido')
INSERT INTO tb_item(id, tipo, valor, descricao) VALUES ('d4530f2f-f247-47a8-b779-4acdf84508a7', 'servico', 50, 'costura')

INSERT INTO tb_pedido_item(pedido_id, item_id) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', 'd4530f2f-f247-47a8-b779-4acdf84508a7')
INSERT INTO tb_pedido_item(pedido_id, item_id) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', '22f6c4ad-104a-48cb-847c-1177ae574266')
INSERT INTO tb_pedido_item(pedido_id, item_id) VALUES ('0efd4845-fc94-46d4-a36b-591375526042', '67967d13-a8e4-4204-a8ff-60c21c66f6e2')

