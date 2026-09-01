INSERT INTO tb_nutriz(nome, email, senha, telefone, dt_nascimento, cpf, dt_cadastro) VALUES('Carla Dias', 'carla@gmail.com', 'senha123', '11998138799', '2000-05-20', '51345588844', '2026-05-05');
INSERT INTO tb_nutriz(nome, email, senha, telefone, dt_nascimento, cpf, dt_cadastro) VALUES('Juliana Paes', 'juliana@gmail.com', 'senha123', '11998138799', '2000-05-20', '51345588800', '2026-05-05');
INSERT INTO tb_nutriz(nome, email, senha, telefone, dt_nascimento, cpf, dt_cadastro) VALUES('Lara Clarson', 'lara@gmail.com', 'senha123', '11998138799', '2000-05-20', '51345588822', '2026-05-05');

-- Ponto de Coleta 1
INSERT INTO tb_ponto_coleta (tipo_coleta, endereco, cidade, estado, telefone) VALUES('PONTO_COLETA', 'Rua das Flores, 123', 'São Paulo', 'SP', '11987654321');
-- Ponto de Coleta 2
INSERT INTO tb_ponto_coleta (tipo_coleta, endereco, cidade, estado, telefone) VALUES('DOMICILIAR', 'Av. Brasil, 456', 'Mogi das Cruzes', 'SP', '11991234567');
-- Ponto de Coleta 3
INSERT INTO tb_ponto_coleta (tipo_coleta, endereco, cidade, estado, telefone) VALUES('DOMICILIAR', 'Rua das Palmeiras, 789', 'Guarulhos', 'SP', '11999887766');

INSERT INTO tb_agendamento(nutriz_id, ponto_coleta_id, data_hora, tipo_coleta, status) VALUES(1,1, '2026-09-01T10:00:00', 'DOMICILIAR', 'AGENDADO');
INSERT INTO tb_agendamento(nutriz_id, ponto_coleta_id, data_hora, tipo_coleta, status) VALUES(2,2, '2026-09-02T14:30:00', 'PONTO_COLETA', 'AGENDADO');
INSERT INTO tb_agendamento(nutriz_id, ponto_coleta_id, data_hora, tipo_coleta, status) VALUES(2, 3,'2026-09-02T14:30:00', 'PONTO_COLETA', 'CANCELADO');


-- Triagem para Carla Dias (nutriz_id = 1)
INSERT INTO tb_triagem(data_triagem, apta_doacao, nutriz_id) VALUES('2026-08-31', true, 1);
-- Triagem para Juliana Paes (nutriz_id = 2)
INSERT INTO tb_triagem(data_triagem, apta_doacao, nutriz_id) VALUES('2026-08-31', false, 2);


