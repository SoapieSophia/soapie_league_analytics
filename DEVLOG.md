# Devlog soapie_league_analytics

## Dia 1
- Criei o repositório
- Configurei o Git
- Escrevi README inicial
- Defini o MVP

## Dia 2
- Comecei a ler o livro Pro Git; esclareceu várias dúvidas que tive com os commits do dia 1.
- Criei a estrutura inicial da aplicação com Spring Boot.
- Executei a aplicação com sucesso, iniciando um servidor HTTP (Tomcat) na porta 8080.
- Entendi o papel do Spring Boot, do Maven e do servidor HTTP na aplicação.

## Dia 3
- Instalei e configurei o Postman para testar requisições HTTP.
- Gerei uma API Key de desenvolvimento da Riot e realizei minha primeira requisição com sucesso.
- Entendi o fluxo básico da Riot API: Riot ID → PUUID → histórico de partidas → detalhes da partida.
- Aprendi a diferença entre path parameters e query parameters, e por que gameName e tagLine devem fazer parte da URL.
- Obtive o PUUID da minha conta e me preparei para consultar o histórico de partidas no próximo passo.

## Dia 4
- Criei a primeira integração da aplicação Spring Boot com a Riot API usando `RestClient`.
- Aprendi como montar URLs usando código em uma chamada HTTP e como tratar corretamente caracteres especiais usando `UriComponentsBuilder`.
- Investiguei e corrigi um problema de conversão de URLs envolvendo espaços em Riot IDs, entendendo a diferença entre passar uma `String` e uma `URI` para o Spring. Esse deu uma dorzinha de cabeça até entender que o problema estava na geração de URI do RestClient. A solução foi gerar a URI e mandar já pronta para o RestClient.
- Transformei a resposta JSON da Riot em um objeto Java usando um Data Transfer Object, deixando de trabalhar com texto bruto e passando a manipular dados estruturados. O objeto 'AccountResponse' permite acessar as informações 'puuid', 'gameName' e 'tagLine' pelo código.
- Completei o fluxo inicial da aplicação: informar um Riot ID → consultar a Riot API → receber e interpretar os dados da conta.

## Dia 5
- Implementei a consulta do histórico de partidas usando o PUUID obtido pela Account API, integrando um segundo endpoint da Riot API ao projeto.
- Entendi a diferença entre respostas JSON em formato de objeto e em formato de array. Nem toda resposta de API precisa de um Data Transfer Object próprio. Para a lista de Match IDs, utilizei um array de Strings.
- Aprendi como encadear chamadas de API: primeiro buscar os dados da conta pelo Riot ID, extrair o PUUID da resposta e utilizá-lo para consultar as partidas recentes do jogador.
- Criei um endpoint na aplicação Sping Boot que recebe parâmetros através de `@RequestParam`, permitindo que o Riot ID seja informado pela requisição em vez de ficar fixo no código. Acredito que, por definição, o projeto acaba de se tornar uma aplicação backend.
- Entendi melhor a separação de responsabilidades entre Controller e Service, percebendo que o Controller deve receber requisições e delegar a lógica para os serviços responsáveis.
- Completei o fluxo de consulta de partidas: informar um Riot ID → buscar PUUID → consultar histórico de partidas → retornar os Match IDs encontrados.