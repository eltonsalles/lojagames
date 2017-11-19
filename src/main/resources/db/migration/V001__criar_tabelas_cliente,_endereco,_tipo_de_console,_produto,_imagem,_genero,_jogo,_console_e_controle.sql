CREATE TABLE cliente (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL,
    celular VARCHAR(11) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(150) NOT NULL,
    sexo VARCHAR(9) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    UNIQUE INDEX cpf_UNIQUE (cpf ASC),
    UNIQUE INDEX email_UNIQUE (email ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE endereco (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    id_cliente BIGINT(20) NOT NULL,
    cep CHAR(8) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    referencia VARCHAR(255),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cliente (nome, cpf, celular, telefone, sexo, data_nascimento, email, senha) VALUES 
('Fillipe Pinheiro', '25531243928', '11941211079', '1158343425', 'MASCULINO', '1995-08-25', 'fillipe@pinheiro.com', '1234'),
('Vitoria Rezende', '73275321889', '11978689066', '1154456556', 'FEMININO', '1996-03-23', 'vitoria@rezende.com', '1234'),
('Julia Silva', '42887012363', '11981873590', '1156759801', 'FEMININO', '2000-07-28', 'julia@silva.com', '1234'),
('Melissa Lobo', '73472357070', '11967581345', '1158546781', 'FEMININO', '1996-10-01', 'melissa@lobo.com', '1234'),
('Yuri Moraes', '97707142756', '11965432323', '1158353223', 'MASCULINO', '1996-06-06', 'yuri@moraes.com', '1234'),
('Abner Clein', '50950817686', '11978456677', '1156712345', 'MASCULINO', '1992-03-30', 'abner@clein.com', '1234'),
('Erick Jesus', '21630191108', '11965324545', '1156713900', 'MASCULINO', '1996-05-23', 'erick@jesus.com', '1234'),
('Camila Lopes', '06885073105', '11987654321', '1155667788', 'FEMININO','1998-06-29', 'camila@lopes.com', '1234'),
('Mariana Losacco', '38420486108', '11987095645', '1154347768', 'FEMININO', '1996-10-14', 'mariana@losacco.com', '1234'),
('Maria Eloise', '36977632453', '11934989081', '1154638978', 'FEMININO', '1996-11-11', 'maria@eloise.com', '1234'),
('Glebson Lourenço', '38915157664', '11935981081', '1154538778', 'MASCULINO', '1990-07-26', 'glebson@lego.com', '1234'),
('Elton Rodrigues Melo', '32701581303', '11935781082', '1134538778', 'MASCULINO', '1990-07-05', 'elton@rodrigues.com', '1234'),
('João Hamasaki', '83524856101', '11935282082', '1134435777', 'MASCULINO', '1993-01-22', 'joao@hamasaki.com', '1234'),
('Fabiano Bibiano', '57587518207', '11944981080', '1144536778', 'MASCULINO', '1996-05-29', 'fabiano@bibiano.com', '1234'),
('Douglas Oliveira', '41982623306', '11933882070', '1134536898', 'MASCULINO', '1996-09-09', 'douglas@oliveira.com', '1234');

INSERT INTO endereco (id_cliente, cep, logradouro, numero, complemento, referencia, bairro, cidade, uf) VALUES
(1, '05603001', 'Av. Lopes de Azevedo', '702', null, 'Praça Alfredo Volpi', 'Jardim Guedala', 'São Paulo', 'SP'),
(1, '04520013', 'R. Inhambu', '1090', 'CJ 95', 'St. Marche - Moema Pavão', 'Moema', 'São Paulo', 'SP'),
(2, '04663060', 'R. Sérgio Milliet', '209', null, '22º BPM/M', 'Vila Campo Grande', 'São Paulo', 'SP'),
(3, '05466070', 'R. Silva Prado', '65', null, 'Teatro do Colégio Santa Cruz', 'Alto de Pinheiros', 'São Paulo', 'SP'),
(4, '05675160', 'R. das Zínias', '112', null, 'Praça Ematuba', 'Cidade Jardim', 'São Paulo', 'SP'),
(5, '05603001', 'Av. Lopes de Azevedo', '702', null, 'Praça Alfredo Volpi', 'Jardim Guedala', 'São Paulo', 'SP'),
(6, '04523001', 'Av. Macuco', '404', 'CJ 25 Bloco A', 'Ford Caoa', 'Indianópolis', 'São Paulo', 'SP'),
(6, '04902010', 'R. José Andreotti', '130', null, 'Prefeitura Regional MBoi Mirim', 'Socorro', 'São Paulo', 'SP'),
(7, '04552050', 'R. Helena', '210', 'CJ 345 Bloco B', 'Shopping Vila Olímpia', 'Vila Olimpia', 'São Paulo', 'SP'),
(8, '04714001', 'R. Antônio das Chagas', '1032', null, 'UNIP Chacara Santo Antonio Campus I', 'Chacara Santo Antonio', 'São Paulo', 'SP'),
(9, '05717270', 'R. José Ramon Urtiza', '766', null, 'Colégio Visconde de Porto Seguro', 'Vila Andrade', 'São Paulo', 'SP'),
(9, '01405002', 'R. Pamplona', '1534', 'CJ 237 Bloco A', 'Carrefour Pamplona', 'Jardim Paulista', 'São Paulo', 'SP'),
(10,'03066010', 'R. Padre Antônio de Sá', '67', null, 'Shopping Metrô Boulevard Tatuapé', 'Tatuapé', 'São Paulo', 'SP'),
(11,'04710200', 'R. Joerg Bruder', '155', null, 'Morumbi Shopping', 'Vila São Francisco', 'São Paulo', 'SP'),
(12,'04711060', 'R. Despraiado', '149', null, 'Consulado Americano', 'Vila São Francisco', 'São Paulo', 'SP'),
(13,'01508020', 'R. Pirapitingui', '111', 'CJ 116 Bloco A', 'Estação São Joaquim', 'Liberdade', 'São Paulo', 'SP'),
(14,'04714002', 'R. Antônio das Chagas', '1528', null, 'Defensoria Pública SP', 'Chacara Santo Antonio', 'São Paulo', 'SP'),
(15,'03182070', 'R. França Carvalho', '237', null, 'Instituto CEMA', 'Água Rasa', 'São Paulo', 'SP');

CREATE TABLE tipo_console (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE produto (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    id_tipo_console BIGINT(20) NOT NULL,
    tipo_produto VARCHAR(8) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    nome VARCHAR(150) NOT NULL,
    preco_compra DECIMAL(10, 2) NOT NULL,
    preco_venda DECIMAL(10, 2) NOT NULL,
    estoque INTEGER NOT NULL,
    itens_inclusos VARCHAR(150) NOT NULL,
    garantia VARCHAR(2) NOT NULL,
    descricao TEXT NOT NULL,
    FOREIGN KEY (id_tipo_console) REFERENCES tipo_console(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE imagem (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    id_produto BIGINT(20) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE genero (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE jogo (
	id_produto BIGINT(20) NOT NULL,
    id_genero BIGINT(20) NOT NULL,
    edicao VARCHAR(25) NOT NULL,
    produtora VARCHAR(50) NOT NULL,
    lancamento DATE NOT NULL,
    idioma VARCHAR(15) NOT NULL,
    legenda VARCHAR(15) NOT NULL,
    idade VARCHAR(5) NOT NULL,
    quantidade_jogadores_off INTEGER NOT NULL,
    quantidade_jogadores_on INTEGER NOT NULL,
    resolucao VARCHAR(6) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE,
    FOREIGN KEY (id_genero) REFERENCES genero(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE console (
	id_produto BIGINT(20) NOT NULL,
    fabricante VARCHAR(50) NOT NULL,
    capacidade VARCHAR(10) NOT NULL,
    modelo VARCHAR(15) NOT NULL,
    voltagem VARCHAR(10) NOT NULL,
    cor VARCHAR(15) NOT NULL,
    resolucao VARCHAR(6) NOT NULL,
    midias VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE controle (
	id_produto BIGINT(20) NOT NULL,
    fabricante VARCHAR(50) NOT NULL,
    conexao VARCHAR(20) NOT NULL,
    alimentacao VARCHAR(35) NOT NULL,
    alcance VARCHAR(3) NOT NULL,
    cor VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tipo_console (nome) VALUES
('Playstation 3'),
('Playstation 4'), 
('Xbox One');

INSERT INTO produto (tipo_produto, id_tipo_console, nome, preco_compra, preco_venda,  estoque, itens_inclusos, garantia, descricao) VALUES 
('JOGO', 1, 'The Elder Scrolls V: Skyrim', 79.90, 119.99, 10, 'Mídia, Manual', '3', 'The Elder Scrolls V: Skyrim se destaca pelos cenários colossais e nomeépicos, no melhor estilo medieval possível. No entanto, o título conta com uma nova engine, capaz de entregar gráficos muito melhores do que os vistos em Oblivion e Fallout 3. O trono está vago e sem nenhum imperador para defender, os Blades desaparecem um a um. Como estava escrito nos Pergaminhos Antigos, a guerra em Skyrim é o último passo para o retorno de Alduin, o deus dragão da destruição. Agora, você deve encarar seu destino como o último Dovahkiin, um caçador de dragões ungido pelos deuses para combater a ameaça de Alduin.'),
('JOGO', 1, 'PES 2017', 89.90, 139.99, 15, 'Mídia, Manual', '3', 'O novo slogan de PES 2017 é simples: realidade de controle. Palavras que querem mais uma vez destacar a dedicação da equipe de desenvolvimento de dar mais profundidade ao controle e movimento dos jogadores no gramado. A principal função é chamada de Real Touch, que é a evolução do conhecido sistema First Touch system, o que dá mais controle à bola assim que ela chega ao atleta. Como parar a bola e para onde chutar em seguida fazem parte disso agora. Tudo é realizado por meio da alavanca analógica da esquerda, a mesma que controla o jogador no campo'),
('JOGO', 1, 'Mortal Kombat', 29.90, 69.90, 8, 'Mídia, Manual', '3', 'Depois de séculos de Mortal Kombat, o imperador Shao Kahn finalmente derrotou Raiden e seus aliados. Enfrentando sua própria destruição, Raiden tem uma última chance. Para desfazer a vitória do imperador, ele precisa atingir Shao Kahn onde ele é mais vulnerável, que é no passado. Trazendo mais uma aventura ao Raiden. Em Mortal Kombat Komplete Edition, mais quatro lutadores chegam para disputar o torneio: Skarlet, Rain, Kenshi e o Freddy Krueger. O game também possui 15 Klassic Skins, uniformes clássicos para os personagens originais, exclusivamente para a edição brasileira do game.'),
('JOGO', 1, 'Resident Evil 6', 19.90, 59.99, 16,'Mídia, Manual', '3', 'Resident Evil 6 é novo capítulo da renomada franquia de horror e ação, marcando o retorno dos veteranos Chris Redfield e Leon Kennedy lutando juntos contra o enxame de zumbis. O jogo se passa no ano de 2013 na cidade fictícia de Lanshiang, na região costeira da China, onde um novo tipo de vírus ameaça contagiar a população de todo o país. Quanto ao gameplay, Resident Evil 6 deve manter a mecânica utilizada nos RE4 e 5, sendo que a possibilidade de correr e até esquivar-se enquanto você atira deixam o jogo um pouco mais inclinado à ação rápida. Desta vez, os mortos-vivos são ligeiramente mais inteligentes, podem usar armas de fogo e são ágeis o suficiente para correr e saltar sobre o personagem.'),
('JOGO', 1, 'Grand Theft Auto V (GTA 5)', 49.90, 124.99, 80,'Mídia, Manual', '3', 'O novo título da franquia traz de volta grandes elementos do tão adorado GTA San Andreas e muitas novidades. Desta vez há três protagonistas jogáveis, cada um com habilidades exclusivas: Michael, um homem rico de meia-idade, Franklin, um mal-encarado que vive nos subúrbios da cidade de Los Santos e Trevor, um ex-militar sociopata. No mínimo o que se podia esperar de um novo título da Rockstar. Sendo o décimo quinto jogo da série, em Grand Theft Auto V a história irá decorrer na cidade fictícia de Los Santos, no estado de San Andreas e nos seus arredores, baseada na moderna Los Angeles e na Califórnia do Sul. Los Santos era uma das três cidades do jogo Grand Theft Auto: San Andreas, lançado em 2004. De acordo com a Rockstar, Grand Theft Auto V será o seu maior jogo de mundo aberto até à data.'),
('JOGO', 1, 'SSX', 49.90, 129.99, 5,'Mídia, Manual', '3', 'SSX traz para o Playstation 3 uma nova versão da popular série de snowboard arcade, oferecendo aos jogadores a possibilidade de realizar manobras incríveis apenas possíveis dentro do jogo. Com cenários fantásticos e fiéis a lugares reais, SSX te desafia nas maiores e mais difíceis montanhas de todo o mundo.'),
('JOGO', 1, 'Injustice: Gods Among Us', 19.90, 59.99, 20,'Mídia, Manual, 6 Personagens adicionais, 30 Skins adicionais, 60 Missões exclusivas', '3', 'Injustice: Gods Among Us é um jogo de luta que traz heróis como Superman, Batman, Mulher Maravilha e Flash em um universo alternativo no qual os papéis pelos quais os conhecemos serão diferentes. Eles enfrentarão os vilões mais poderosos do universo nesse game. Suas lutas são bidimensionais, seus gráficos impressionam pela qualidade e realismo, comparados aos jogos que foram lançados na mesma época. Escolha um herói ou vilão e veja quem ira sobreviver.'),
('JOGO', 1, 'Terra Média: Sombras de Mordor', 19.90, 49.99, 14,'Mídia, Manual', '3', 'O jogo se baseia em ação e batalhas contra ferozes monstros, como Dark Souls e Darksiders, e uma das principais apostas do game é dar a cada inimigo uma característica única, e não apenas isso, mas também dar aos adversários uma história para com seu personagem, com a ajuda do novo sistema de implementação nemesis. Como a mecânica irá refletir a jogabilidade? Cada encontro entre personagens fica registrado no game, bem como quem venceu ou perdeu. Se o protagonista for derrotado por um orc, por exemplo, a criatura vai lembrá-lo de que já o matou antes, e que pode fazê-lo novamente. Junte esta mecânica a criaturas com um comportamento único e pode esperar muita variação nas batalhas.'),
('JOGO', 1, 'Dead Space 2', 39.90, 119.99, 8,'Mídia, Manual', '3', 'Lembra-se do horror, suspense e tensão que se encontra no universo de Dead Space? Pois o segundo título desta assustadora franquia é ainda mais assustador e aterrorizante para seus respectivos jogadores. Dead Space 2 causa ainda mais medo, trazendo horror e criaturas apavorantes para preencher seus pesadelos. Sem perder seu brilhantismo e desafio o jogo conta com novos inimigos muito bem desenhados para causar ainda mais medo e terror no jogador. Com novos personagens e ambientes sem qualquer gravidade (chamados de zero-G), as lutas são ainda mais brutais. A infecção que teve início no primeiro jogo continua a se espalhar, e só você é capaz de detê-la, exterminando os infectados pelo caminho.'),
('JOGO', 3, 'Fallout 4', 19.99, 49.99, 14, 'Mídia, Manual', '3', 'Fallout 4 é a continuação de uma das franquias de RPG de Ação mais prestigiada de todos os tempos. Fallout é um RPG desenvolvido pela Bethesda Softworks, a mesma empresa responsável pela franquia de The Elder Scrolls, mas Fallout possui um plot diferente, ao invés de um mundo mágico medieval, a história se passa em um mundo futurista pós apocalíptico. Fallout é um game movimentado pelo pânico e pela paranóia, gerados pela iminência de uma catástrofe nuclear decorrente da Guerra Fria entre os Estados Unidos e a União Soviética nas décadas de 60 e 70. O jogo ainda permite destruir e contruir bases gigantescas, utilizando as defesas e estruturas que bem entender.'),
('JOGO', 3, 'F1 2017', 89.99, 219.99, 20, 'Mídia, Manual', '3', 'Crie a sua própria lenda em F1 2017, e prepare-se para entrar com tudo no mundo do maior esporte automotivo de todos os tempos. Crie um piloto, escolha uma das equipes da Fórmula 1 e participe das temporadas aumentando seus pontos, para no final poder conseguir o título tão sonhado. O jogo também traz a volta do modo clássico, que foi um enorme sucesso no F1 2013. Formula 1 2017 traz pela primeira vez a interação entre a experiência do modo clássico no modo carreira, onde você será convidado para correr nos dias atuais em vários eventos com os diferentes carros clássicos durante a temporada. Os carros clássicos também estão disponíveis em vários outros modos, incluindo o multiplayer online e no time trial.'),
('JOGO', 3, 'Dragon Ball: Xenoverse 2', 99.99, 209.99, 3, 'Mídia, Manual', '3', 'Desenvolvido para utilizar plenamente o poder dos consoles da nova geração Dragon Ball: Xenoverse 2 se baseia no popular jogo anterior "Dragon Ball: Xenoverse" com gráficos melhorados que continuará a mergulhar os jogadores no maior e mais detalhado mundo de Dragon Ball ja desenvolvido. Dragon Ball: Xenoverse 2 terá renovações nos cenários, terá mais opções de personalização dos personagens, e uma infinidade de novos recursos e atualizações. Você também poderá carregar e continuar o save do jogo anterior.'),
('JOGO', 3, 'Resident Evil 7: Biohazard', 89.99, 185.99, 70, 'Mídia, Manual', '3', 'O novo jogo da gloriosa franquia da Capcom, Resident Evil, traz possibilidades que antes eram inimagináveis no game. Resident Evil 7: Biohazard trouxe mudanças significativas em sua gameplay. Além de apresentar um visual muito mais realista esse vai ser o primeiro jogo da saga a utilizar visão em primeira pessoa. Os eventos ocorridos em Resident Evil 7: Biohazard ocorrem em uma zona rural situada nos Estados Unidos, após os acontecimentos de Resident Evil 6. Com o trailer e a demo disponibilizada para PlayStation 4. Os jogadores puderam perceber todas as mudanças ocorridas. Resident Evil 7: Biohazard promete ser um dos jogos mais aterrorizantes de 2017.'),
('JOGO', 3, 'Assassinss Creed Unity', 9.99, 24.99, 10, 'Mídia, Manual', '3', 'Assassins Creed Unity, tomando um rumo completamente diferente de Black Flag é ambientado em meio a revolução francesa, uma época cheia de dor e sofrimento, mas que precisam ser suportados para alcançar a liberdade. O jogo contém gráficos excepcionais graças a potência dos consoles da nova geração, permitindo colocar na tela dezenas de pessoas batalhando simultaneamente. A famosa hidden blade do novo game tem uma modificação que permite atirar a lâmina como uma besta, oferecendo muito mais potência e pontaria quando comparada as facas de atirar. De acordo com os desenvolvedores Unity será o começo de uma nova saga dentro da franquia de assassins creed, juntamente com as missões co-op.'),
('JOGO', 3, 'Tekken 7', 89.99, 204.99, 16, 'Mídia, Manual', '3', 'Levante seus punhos e se prepare para a batalha na atual geração de consoles. A histórica franquia de luta retorna para mais uma luta em Tekken 7. Com o sistema original de batalha e gameplay em 3D, Tekken 7 leva a franquia para o próximo nível com gráficos realistas, recursos inovadores e novas mecânicas de batalha. Tekken 7 ressuscita a atitude, a competividade e o carisma existente no DNA arcade da série para criar a melhor experiência de luta nos videogames. O estilo de luta se aproxima muito com os movimentos da vida real. Suas sequencias de golpes e combos são únicos e isso faz com que o jogo torne-se diferentes dos outros do mesmo gênero.'),
('JOGO', 3, 'The Witcher 3: Wild Hunt', 70.99, 159.99, 50, 'Mídia, Manual', '3', 'O retorno das memórias de Geralt permitiu que uma história muito mais pessoal e intimista fosse criada. Ele modificou a história de reinos e regiões do mundo, mas pode parar de fazer isso caso você deseje. No título, o destino de todos está em suas mãos e cabe a você escolher o que, e como fazer. Para a tristeza de todos, esse episódio também será o último da saga de Geralt of Rivia, colocando-o frente a frente com seus maiores inimigos para recuperar sua amada Yennefer. O protagonista não está mais assombrado pela amnésia que tomava conta dele nos dois primeiros games da série. E agora, parte com tudo para cima dos Wild Hunt, contando com a ajuda de velhos companheiros e encontrando novos parceiros ao longo de uma jornada.'),
('JOGO', 3, 'Dead Rising 3', 39.99, 119.99, 24, 'Mídia, Manual, 4 pacotes de DLC, 1 item digital extra', '3', 'Dead Rising 3 (Apocalypse Edition) contém o jogo completo de mundo aberto com zumbis, quatro pacotes de DLC e um item digital extra. Este título se passa dez anos depois dos eventos de Dead Rising 2 em Fortune City. Agora você jogará na pele de Nick Ramos, um jovem mecânico com um passado misterioso e que luta para fugir de uma cidade enfestada por zumbis. Isso antes que os militares destruam a cidade de Los Perdidos, na Califórnia, incluindo tudo que estiver dentro dela. Esta não é uma tarefa para se fazer sozinho, Nick precisará da ajuda de outros sobreviventes para se manter em pé, fazendo o possível e o impossível para fugir da cidade antes que seja tarde demais.'),
('JOGO', 2, 'Dark Souls III', 79.99, 199.99, 7, 'Mídia, Manual', '3', 'O game começa em um ritmo lento, mas logo apresenta alguns dos elementos que serão chave desse capítulo. Os cenários, de castelos a vilarejos inteiros, estão imensos. A arquitetura gótica mistura-se também com o ar de destruição do game. Criaturas assustadoras, como um grupo macabro de monges, dão o tom sombrio. Dark Souls é um RPG de ação extremamente hardcore, onde é possível morrer para certos inimigos levando apenas um golpe. O jogo oferece várias artimanhas para derrotar os inimigos, contando com armas de longo alcance, curto alcance, rolamento, armaduras leves e pesadas, e cada um destes atributos irá impactar no combate de maneira extravagante.'),
('JOGO', 2, 'EA Sports UFC 2', 49.99, 159.99, 3, 'Mídia, Manual', '3', 'A principal novidade do game de luta mais esperado do ano é jogabilidade, a qual conta com novo sistema de física, deixando os movimentos muito mais impactantes. Segundo os produtores, a intenção é que cada nocaute seja único, além de oferecer uma dinâmica inédita de impacto dos golpes. Para deixar o jogo ainda mais realista, alguns lutadores terão acesso também a alguns de seus movimentos característicos na vida real, os movimentos que definem o nome e a fama que o lutador possui.'),
('JOGO', 2, 'God of War', 79.99, 179.99, 15, 'Mídia, Manual', '3', 'Sua vingança contra os deuses do Olimpo agora é passado, em God of War, Kratos vive como um homem comum nas terras dos deuses Nórdicos, ensinando seu filho a sobreviver a todos os perigos que aparecerem por sua frente. Kratos está empenhado em passar todos seus conhecimentos ao seu filho, que está determinado a conquistar o respeito de seu pai. Finalmente o assassino de deuses tem a oportunidade de corrigir seus erros do passado. God of War traz um cenário totalmente diferente dos jogos anteriores, repleto de florestas e áreas montanhosas, repleto de criaturas e deuses da mitologia nórdica. Kratos agora possui um machado poderoso e mágico que traz muita ação no combate corpo a corpo. Além de possuir outros armamentos que fazem com que este grande guerreiro torne-se ainda mais poderoso e destrutivo.'),
('JOGO', 2, 'The Last of Us', 25.99, 98.50, 9, 'Mídia, Manual', '3', 'The Last of Us é uma experiência que promete redefinir o gênero de jogos de apocalipse zumbi com uma mistura de sobrevivência e ação, contando uma história centrada nos personagens, que são muito carismáticos devido ao senso interno de sobrevivência que cada ser humano possui dentro de si, sobre uma praga moderna que dizima a espécie humana há 20 anos atrás. A remasterização de um dos melhores jogos do Playstation 3 promete gráficos incríveis, um sistema de movimentação melhorado e alguns novos itens relevantes adicionados no jogo, que irão auxiliar o jogador em sua jornada pós apocalíptica. O jogo também irá contar com várias adições em seu modo multiplayer, aumentando o realismo.'),
('JOGO', 2, 'Uncharted: The Lost Legacy', 49.99, 143.89, 50, 'Mídia, Manual', '3', 'A franquia de sucesso - Uncharted – traz mais uma novidade para seus fãs, Uncharted: The Lost Legacy a primeira aventura independente da história do jogo, liderada pela personagem favorita de muitos fãs, Chloe Frazer. Ela e a mercenária Nadine Ross (de Uncharted 4: A Thiefs End) irão embarcar em uma aventura recheada de ação, a busca de um artefato nas montanhas da Índia. Chloe Frazer está à procura de um antigo e lendário artefato indiano, por isso ela vai para as montanhas indianas tentar acha-lo antes que a região caia no caos. Para isso ela precisou recorrer à ajuda da renomada mercenária Nadine Ross, juntas deverão trabalhar em equipe, desvendar muitos mistérios e lutar contra os inimigos.'),
('JOGO', 2, 'F1 2017', 79.99, 214.89, 100, 'Mídia, Manual', '3', 'Crie a sua própria lenda em F1 2017, e prepare-se para entrar com tudo no mundo do maior esporte automotivo de todos os tempos. Crie um piloto, escolha uma das equipes da Fórmula 1 e participe das temporadas aumentando seus pontos, para no final poder conseguir o título tão sonhado. O jogo também traz a volta do modo clássico, que foi um enorme sucesso no F1 2013. Formula 1 2017 traz pela primeira vez a interação entre a experiência do modo clássico no modo carreira, onde você será convidado para correr nos dias atuais em vários eventos com os diferentes carros clássicos durante a temporada. Os carros clássicos também estão disponíveis em vários outros modos, incluindo o multiplayer online e no time trial.'),
('JOGO', 2, 'Street Fighter V', 39.99, 99.89, 30, 'Mídia, Manual', '3', 'Street Fighter V tem o mesmo tipo de jogabilidade em deslocação lateral que os seus antecessores, na qual dois lutadores usam uma variedade de ataques e de habilidade especiais para derrotar o seu oponente. Também manteve a barra EX introduzida em Street Fighter IV, que vai sendo preenchida à medida que o jogador vai atacando. A barra EX pode ser usada para fazer movimentos especiais ou para fazer super combos, com o nome Critical Arts. Street Fighter V introduz a "Barra-V", que é preenchida à medida que o jogador vai recebendo danos; a "Barra-V" dá três novas técnicas: V-Skills, V-Reversals e V-Triggers. V-Skills são ataques especiais únicos em cada um dos personagens.'),
('JOGO', 2, 'Bloodborne', 39.99, 99.89, 20, 'Mídia, Manual', '3', 'Em Bloodborne, a ambientação do game é inspirada nas tradições góticas do século XIX, período conhecido como Era Vitoriana (1837 a 1901). O reinado da rainha Victoria foi uma época de paz e prosperidade, mas havia também um lado sombrio, como a aparição de Jack, o estripador, um dos mais famosos serial killers de toda a História. Os cenários de Bloodborne parecem espelhar o lado escuro de Londres nesse período, capital da Inglaterra. Quase todos os ambientes são noturnos e a situação é de caos. Yharnam, onde acontece a ação do game, é uma cidade amaldiçoada por uma praga chamada Flagelo da Besta e habitada por criaturas horríveis.'),
('JOGO', 2, 'Until Dawn', 39.99, 89.89, 20, 'Mídia, Manual', '3', 'Lembra-se dos filmes de terror adolescente clássicos dos anos 80 e 90, como “Pânico” e “Sexta-feira 13”? Pois o gênero retorna, desta vez em Until Dawn, um game que conta a história de oito jovens que precisam sobreviver até o amanhecer a um perigo desconhecido. Uma proposta simples e muito utilizada nos cinemas, mas que parece funcionar muito bem no console. Reunindo todos os clichês do gênero, acompanharemos o ponto de vista de cada um dos personagens envolvidos. Mais do que isso, nossas escolhas e ações vão afetar o desenvolvimento da história, apresentando conclusões diferentes de acordo com o que acontece em seu caminho. Outro destaque é a utilização do uso remoto no Playstation Vita.'),
('JOGO', 1, 'Pro Evolution Soccer 2018', 79.90, 189.99, 100, 'Mídia, Manual', '3', 'Pro Evolution Soccer 2018 traz agora uma parceria com o Barcelona, trazendo muitos jogadores do time espanhol, incluindo Messi, um dos melhores do mundo atualmente. O novo título destaca-se pelos gráficos, que tiveram uma grande melhoria se comparado ao seu antecessor, trazendo mudanças significativas com a reestruturação do sistema de animação e modelagem dos jogadores para uma melhor percepção visual. PES 2018 também traz uma mecânica chamada Real Touch+, que possibilita um maior controle de todas as partes do corpo permitidos na prática do futebol para o domínio da bola. Esse sistema será controlado por um dos analógicos do controle. E também no direcionamento da bola quando ela “pinga” no chão, podendo levá-la para diversas direções, e um posicionamento mais confiável dos goleiros.'),
('JOGO', 2, 'Pro Evolution Soccer 2018', 79.90, 189.99, 100, 'Mídia, Manual', '3', 'Pro Evolution Soccer 2018 traz agora uma parceria com o Barcelona, trazendo muitos jogadores do time espanhol, incluindo Messi, um dos melhores do mundo atualmente. O novo título destaca-se pelos gráficos, que tiveram uma grande melhoria se comparado ao seu antecessor, trazendo mudanças significativas com a reestruturação do sistema de animação e modelagem dos jogadores para uma melhor percepção visual. PES 2018 também traz uma mecânica chamada Real Touch+, que possibilita um maior controle de todas as partes do corpo permitidos na prática do futebol para o domínio da bola. Esse sistema será controlado por um dos analógicos do controle. E também no direcionamento da bola quando ela “pinga” no chão, podendo levá-la para diversas direções, e um posicionamento mais confiável dos goleiros.'),
('JOGO', 3, 'Pro Evolution Soccer 2018', 79.90, 189.99, 100, 'Mídia, Manual', '3', 'Pro Evolution Soccer 2018 traz agora uma parceria com o Barcelona, trazendo muitos jogadores do time espanhol, incluindo Messi, um dos melhores do mundo atualmente. O novo título destaca-se pelos gráficos, que tiveram uma grande melhoria se comparado ao seu antecessor, trazendo mudanças significativas com a reestruturação do sistema de animação e modelagem dos jogadores para uma melhor percepção visual. PES 2018 também traz uma mecânica chamada Real Touch+, que possibilita um maior controle de todas as partes do corpo permitidos na prática do futebol para o domínio da bola. Esse sistema será controlado por um dos analógicos do controle. E também no direcionamento da bola quando ela “pinga” no chão, podendo levá-la para diversas direções, e um posicionamento mais confiável dos goleiros.'),
('JOGO', 2, 'Destiny 2', 99.90, 219.99, 100, 'Mídia, Manual, Assalto Cooperativo, Rifle de Precisão Exótico, Nave, Mapa Multijogador, 3x Armaduras Lendárias', '3', 'A última cidade segura da humanidade caiu perante uma força invasora esmagadora, liderada por Ghaul, o grandioso comandante da brutal Legião Vermelha. Ele tomou o poder dos Guardiões da cidade e forçou os sobreviventes a fugir. Em seu último suspiro, o Viajante enviou Fantasmas para encontrar aqueles que podem usar sua Luz como arma.  Ao ser escolhido, você se torna um Guardião, e deve seguir um caminho: Caçador, Titã, ou Arcano e se aventurar em mundos misteriosos e inexplorados do sistema solar e descobrir um arsenal de armas e novas habilidades de combate devastadoras. Para derrotar a Legião Vermelha e enfrentar Ghaul, você precisa reunir os heróis da humanidade e, juntos, retomar o planeta.'),
('JOGO', 3, 'Destiny 2', 99.90, 219.99, 100, 'Mídia, Manual, Assalto Cooperativo, Rifle de Precisão Exótico, Nave, Mapa Multijogador, 3x Armaduras Lendárias', '3', 'A última cidade segura da humanidade caiu perante uma força invasora esmagadora, liderada por Ghaul, o grandioso comandante da brutal Legião Vermelha. Ele tomou o poder dos Guardiões da cidade e forçou os sobreviventes a fugir. Em seu último suspiro, o Viajante enviou Fantasmas para encontrar aqueles que podem usar sua Luz como arma.  Ao ser escolhido, você se torna um Guardião, e deve seguir um caminho: Caçador, Titã, ou Arcano e se aventurar em mundos misteriosos e inexplorados do sistema solar e descobrir um arsenal de armas e novas habilidades de combate devastadoras. Para derrotar a Legião Vermelha e enfrentar Ghaul, você precisa reunir os heróis da humanidade e, juntos, retomar o planeta.'),
('JOGO', 1, 'NBA 2K18', 99.90, 209.99, 100, 'Mídia, Manual', '3', 'NBA 2K18 traz toda emoção das partidas de basquete da liga mais famosa do mundo a NBA (National Basketball Association), que seria a liga de basquetebol profissional da América do Norte. Trazendo todos os times que compõem essa liga, dentre eles temos: os Warriors, Chicago Bulls, Detroit Pistons, Cavaliers e muitos outros times que participam deste campeonato, assistido pelo mundo todo. Além do melhoramento gráfico, comparado ao seu antecessor NBA 2K17, o game traz um nível de exigência muito maior, mantendo um nível de excelência que a 2K sempre busca alcançar em seus jogos. O jogo é muito competitivo, tanto no singleplayer, quanto no multiplayer. O jogo te possibilita jogar com até quatro pessoas no modo off-line e dez pessoas em seu modo online.'),
('JOGO', 2, 'NBA 2K18', 99.90, 209.99, 100, 'Mídia, Manual', '3', 'NBA 2K18 traz toda emoção das partidas de basquete da liga mais famosa do mundo a NBA (National Basketball Association), que seria a liga de basquetebol profissional da América do Norte. Trazendo todos os times que compõem essa liga, dentre eles temos: os Warriors, Chicago Bulls, Detroit Pistons, Cavaliers e muitos outros times que participam deste campeonato, assistido pelo mundo todo. Além do melhoramento gráfico, comparado ao seu antecessor NBA 2K17, o game traz um nível de exigência muito maior, mantendo um nível de excelência que a 2K sempre busca alcançar em seus jogos. O jogo é muito competitivo, tanto no singleplayer, quanto no multiplayer. O jogo te possibilita jogar com até quatro pessoas no modo off-line e dez pessoas em seu modo online.'),
('JOGO', 3, 'NBA 2K18', 99.90, 209.99, 100, 'Mídia, Manual', '3', 'NBA 2K18 traz toda emoção das partidas de basquete da liga mais famosa do mundo a NBA (National Basketball Association), que seria a liga de basquetebol profissional da América do Norte. Trazendo todos os times que compõem essa liga, dentre eles temos: os Warriors, Chicago Bulls, Detroit Pistons, Cavaliers e muitos outros times que participam deste campeonato, assistido pelo mundo todo. Além do melhoramento gráfico, comparado ao seu antecessor NBA 2K17, o game traz um nível de exigência muito maior, mantendo um nível de excelência que a 2K sempre busca alcançar em seus jogos. O jogo é muito competitivo, tanto no singleplayer, quanto no multiplayer. O jogo te possibilita jogar com até quatro pessoas no modo off-line e dez pessoas em seu modo online.'),
('JOGO', 2, 'Knack 2', 99.90, 169.99, 100, 'Mídia, Manual', '3', 'Knack é um ser energizado por relíquias antigas e misteriosas, que o tornam capaz de se transformar em um brutamontes poderoso, um gigante destruidor, ou até mesmo um anão flexível, tudo isto apenas utilizando peças que você encontra pelo cenário. Após salvar o mundo da invasão orc no primeiro jogo, Knack é convocado novamente para uma nova aventura, e desta vez, ele não está sozinho. Em Knack 2 o modo cooperativo online e local do jogo se torna uma realidade, com mais de 20 combinações de golpes conjuntos e muitos puzzles pelo caminho, onde você deve impedir um temível vilão de recuperar armas antigas e reviver titãs mortíferos. Explore este mundo de cores vibrantes e gráficos incríveis, que oferece uma jogabilidade acessível à crianças e desafiadora para adultos.'),
('CONSOLE', 2, 'Playstation 4', 1099.90, 2199.99, 15, 'Dualshock 4, Cabo HDMI, Cabo de Alimentação, USB 2.0, Manual, Headset', '12', 'O novo console PlayStation 4 Pro, traz muito mais poder de processamento gráfico, além de contar com suporte as tecnologias HDR e 4K. Ele conta com uma nova GPU (4.20 TFLOPS, AMD Radeon™). Deixando o console com uma taxa de frames muito mais estável. O PS4 Pro é muito mais robusto, e seu visual é muito mais parecido com o PS4 Slim, do que com o do PS4 “FAT”. Essa nova versão do PlayStation 4, é muito mais poderosa, trazendo ao jogador muito mais fluidez e qualidade de imagem aos seus jogos. Além de suporte 4K para serviços de streaming, como Netflix e YouTube.'),
('CONSOLE', 3, 'Xbox One', 790.90, 1799.99, 15, 'Controle, Cabo HDMI, Cabo de Alimentação, Manual, Código de Jogo', '12', 'No novo Xbox One S, você pode se divertir com a maior linha de jogos, incluindo jogos clássicos do Xbox 360, em um console 40% menor. Armazene mais jogos do que nunca com um enorme disco rígido de 500Gb. Stream de vídeo 4K no Netflix e Amazon Video, e assista a filmes Blu-ray UHD na impressionante fidelidade visual. Em seguida, experimente o maior conforto e sensação do novo controle sem fio do Xbox, com analógico texturizado e tecnologia Bluetooth. Com todos os maiores sucessos deste ano, tudo o que você ama sobre Xbox 360 ainda melhor no Xbox One. Nesta versão o console acompanha o jogo Gears of War 4 que é um dos jogos mais aclamados e elogiados de 2016.'),
('CONTROLE', 2, 'Controle Dualshock 4', 99.90, 249.99, 10, 'Manual', '3', 'O DualShock 4 apresenta inovações para permitir experiências de jogo mais envolventes, incluindo um sensor altamente sensível de seis eixos assim como um touch pad localizado no topo do controle, o que oferece aos jogadores formas completamente novas de jogar e interagir com os jogos. O novo botão Share disponibiliza streaming de vídeo e compartilhamento com apenas um toque. Quatro LEDs coloridos oferecem uma forma mais simples de identificar os jogadores e ver rapidamente informações úteis do jogo, como quando o personagem está com pouca energia ou um ferimento grave. Desfrute dos efeitos sonoros de alta qualidade tanto na TV quanto no controle, enquanto vários mecanismos de vibração proporcionam um maior senso de envolvimento aos jogadores.'),
('CONTROLE', 3, 'Controle Microsoft', 110.90, 269.99, 10, 'Manual', '3', 'Utilizar cabos para se divertir em um console é coisa do passado, já que desde a 3º geração de consoles isto não é mais necessário, e na Next Gen isto não é diferente. O novo controle do Xbox One S promete ser uma ponte entre o controle convencional e a versão elite, com texturas de aderência nos analógicos e na parte traseira, proporcionando uma pegada melhor nas mãos do jogador, e um design diferenciado em sua parte superior. O controle não acompanha uma playn charge como os outros kits do console, mas com ele vão duas pilhas AA totalmente carregadas, para você que não quer esperar na hora de jogar e partir direto para o mundo dos jogos. Sinta a ação como nunca com os gatilhos de impulso do controle e domine o campo de batalha com as forças sombrias.');


INSERT INTO imagem (id_produto, nome, descricao) VALUES 
(1, '1.jpg', 'Descrição da imagem'),
(2, '2.jpg', 'Descrição da imagem'),
(3, '3.jpg', 'Descrição da imagem'),
(4, '4.jpg', 'Descrição da imagem'),
(5, '5.jpg', 'Descrição da imagem'),
(6, '6.jpg', 'Descrição da imagem'),
(7, '7.jpg', 'Descrição da imagem'),
(8, '8.jpg', 'Descrição da imagem'),
(9, '9.jpg', 'Descrição da imagem'),
(10, '10.jpg', 'Descrição da imagem'),
(11, '11.jpg', 'Descrição da imagem'),
(12, '12.jpg', 'Descrição da imagem'),
(13, '13.jpg', 'Descrição da imagem'),
(14, '14.jpg', 'Descrição da imagem'),
(15, '15.jpg', 'Descrição da imagem'),
(16, '16.jpg', 'Descrição da imagem'),
(17, '17.jpg', 'Descrição da imagem'),
(18, '18.jpg', 'Descrição da imagem'),
(19, '19.jpg', 'Descrição da imagem'),
(20, '20.jpg', 'Descrição da imagem'),
(21, '21.jpg', 'Descrição da imagem'),
(22, '22.jpg', 'Descrição da imagem'),
(23, '23.jpg', 'Descrição da imagem'),
(24, '24.jpg', 'Descrição da imagem'),
(25, '25.jpg', 'Descrição da imagem'),
(26, '26.jpg', 'Descrição da imagem'),
(27, '27.jpg', 'Descrição da imagem'),
(28, '28.jpg', 'Descrição da imagem'),
(29, '29.jpg', 'Descrição da imagem'),
(30, '30.jpg', 'Descrição da imagem'),
(31, '31.jpg', 'Descrição da imagem'),
(32, '32.jpg', 'Descrição da imagem'),
(33, '33.jpg', 'Descrição da imagem'),
(34, '34.jpg', 'Descrição da imagem'),
(35, '35.jpg', 'Descrição da imagem'),
(36, '36.jpg', 'Descrição da imagem'),
(37, '37.jpg', 'Descrição da imagem'),
(38, '38.jpg', 'Descrição da imagem'),
(39, '39.jpg', 'Descrição da imagem');

INSERT INTO genero (nome) VALUES
('Ação'), 
('Esporte'), 
('Luta'), 
('RPG'), 
('Terror');

INSERT INTO jogo (id_produto, id_genero, edicao, produtora, lancamento, idioma, legenda, idade, quantidade_jogadores_off, quantidade_jogadores_on, resolucao) VALUES
(1, 4, 'N/A', 'Bethesda', '2011-11-01','INGLES','INGLES', '18', 1, 0, 'R720P'),
(2, 2, 'N/A', 'Konami', '2016-09-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 1, 22, 'R1080P'),
(3, 3, 'Komplete Edition', 'Warner Bros', '2012-02-01','INGLES','PORTUGUES_BR', '18', 2, 2, 'R1080P'),
(4, 5, 'N/A', 'Campcom', '2012-10-01','INGLES','PORTUGUES_BR', '18', 2, 4, 'R1080P'),
(5, 1, 'N/A', 'Rockstar', '2013-09-01','INGLES','PORTUGUES_BR', '18', 1, 16, 'R1080P'),
(6, 2, 'N/A', 'EA Games', '2012-02-01','INGLES','INGLES', 'Livre', 1, 0, 'R720P'),
(7, 3, 'Ultimate Edition', 'WB Games', '2013-04-01','PORTUGUES_BR','PORTUGUES_BR', '12', 2, 8, 'R1080P'),
(8, 4, 'N/A', 'WB Games', '2014-11-01','PORTUGUES_BR','PORTUGUES_BR', '18', 1, 0, 'R1080P'),
(9, 1, 'N/A', 'EA Games', '2011-01-01','INGLES','INGLES', '18', 2, 8, 'R1080P'),
(10, 4,'N/A', 'Bethesda', '2015-11-01','INGLES','PORTUGUES_BR', '18', 1, 0, 'R1080P'),
(11, 2,'Edição Especial', 'Codemasters', '2018-01-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 1, 20, 'R1080P'),
(12, 3,'N/A', 'Bandai Namco', '2016-10-01','INGLES','PORTUGUES_BR', '10', 2, 6, 'R1080P'),
(13, 5,'N/A', 'Capcom', '2017-01-01','INGLES','PORTUGUES_BR', '16', 1, 0, 'R1080P'),
(14, 1,'N/A', 'Ubisoft', '2014-11-01','PORTUGUES_BR','PORTUGUES_BR', '18', 4, 4, 'R1080P'),
(15, 3,'N/A', 'Namco Bandai', '2017-05-01','INGLES','PORTUGUES_BR', '12', 2, 8, 'R1080P'),
(16, 4,'N/A', 'CD Project Red', '2015-05-01','PORTUGUES_BR','PORTUGUES_BR', '16', 1, 0, 'R1080P'),
(17, 5,'Apocalypse Edition', 'Capcom', '2014-08-01','PORTUGUES_BR','PORTUGUES_BR', '18', 1, 2, 'R1080P'),
(18, 4,'N/A', 'Bandai Namco', '2016-04-01','INGLES','PORTUGUES_BR', '14', 1, 6, 'R1080P'),
(19, 2,'N/A', 'Eletronic Arts', '2016-03-01','PORTUGUES_BR','PORTUGUES_BR', '14', 1, 2, 'R1080P'),
(20, 1,'N/A', 'Sony', '2018-01-01','PORTUGUES_BR','PORTUGUES_BR', '18', 1, 0, 'R1080P'),
(21, 1,'Remasterizado', 'Sony', '2014-07-01','PORTUGUES_BR','PORTUGUES_BR', '18', 1, 8, 'R1080P'),
(22, 1,'N/A', 'Sony', '2017-08-01','PORTUGUES_BR','PORTUGUES_BR', '14', 1, 10, 'R1080P'),
(23, 2,'Edição Especial', 'Codemasters', '2018-01-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 1, 20, 'R1080P'),
(24, 3,'N/A', 'Capcom', '2016-02-01','INGLES','PORTUGUES_BR', '12', 2, 8, 'R1080P'),
(25, 4,'N/A', 'Sony', '2015-03-01','PORTUGUES_BR','PORTUGUES_BR', '16', 1, 3, 'R1080P'),
(26, 5,'N/A', 'Sony', '2015-09-01','PORTUGUES_BR','PORTUGUES_BR', '18', 1, 0, 'R1080P'),
(27, 2,'Edição Premium', 'Konami', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 4, 22, 'R1080P'),
(28, 2,'Edição Premium', 'Konami', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 4, 22, 'R1080P'),
(29, 2,'Edição Premium', 'Konami', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 4, 22, 'R1080P'),
(30, 4,'N/A', 'Activision', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', '14', 0, 9, 'R1080P'),
(31, 4,'N/A', 'Activision', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', '14', 0, 9, 'R1080P'),
(32, 2,'N/A', '2K Games', '2017-09-01','INGLES','INGLES', 'Livre', 4, 10, 'R1080P'),
(33, 2,'N/A', '2K Games', '2017-09-01','INGLES','INGLES', 'Livre', 4, 10, 'R1080P'),
(34, 2,'N/A', '2K Games', '2017-09-01','INGLES','INGLES', 'Livre', 4, 10, 'R1080P'),
(35, 1,'N/A', 'Sony', '2017-09-01','PORTUGUES_BR','PORTUGUES_BR', 'Livre', 2, 2, 'R1080P');

INSERT INTO console (id_produto, fabricante, capacidade, modelo, voltagem, cor, resolucao, midias) VALUES
(36, 'Sony','HD1TB','Pro','BIVOLT','PRETO','R4K','BLUERAY'),
(37, 'Microsoft','HD1TB','S','BIVOLT','BRANCO','R1080P','BLUERAY');

INSERT INTO controle (id_produto, fabricante, conexao, alimentacao, alcance, cor) VALUES
(38, 'Sony', 'WIRELESS_BLUETOOTH', 'BATERIA_RECARREGAVEL', '10m', 'PRETO'),
(39, 'Microsoft', 'WIRELESS_BLUETOOTH', 'PILHAS_BATERIA_RECARREGAVEL', '10m', 'PRETO');