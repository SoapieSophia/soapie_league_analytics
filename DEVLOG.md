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

## Dia 6
- Implementei a consulta de detalhes de uma partida usando um Match ID, integrando um terceiro endpoint da Riot API ao projeto.
- Aprendi a lidar com respostas JSON contendo objetos aninhados e arrays de participantes, e comecei a criar DTOs para representar dados específicos da resposta.
- Utilizei o PUUID do jogador para identificar sua participação dentro dos 10 participantes de uma partida, entendendo como relacionar dados vindos de diferentes endpoints da Riot API.
- Completei o fluxo de consulta de informações de uma partida: informar um Riot ID → buscar PUUID → consultar histórico de partidas → obter detalhes da partida → localizar os dados do jogador.
- Refatorei a arquitetura da aplicação criando um `AnalyticsService`, separando a lógica de manipulação de dados das chamadas externas realizadas pelo `RiotApiService`.
- Separei os controllers em `RiotController`, responsável pelas funcionalidades da aplicação usando o `AnalyticsService`, e `RiotTestController`, responsável por testes individuais de chamadas da Riot API.
- Entendi melhor a divisão de responsabilidades em uma aplicação Spring Boot: Controllers recebem requisições, Services coordenam regras da aplicação e classes de integração lidam com comunicação externa.

## Dia 7
- Criei o DTO (`HistoryEntry`) para representar o histórico de partidas, deixando de expor diretamente os objetos retornados pela Riot API.
- Implementei a conversão dos dados da Riot API para o `HistoryEntry`, combinando informações do participante e da partida em um único objeto voltado ao consumo da aplicação.
- Desenvolvi a lógica para consultar múltiplas partidas recentes, localizar o jogador em cada uma delas através do PUUID e gerar um histórico contendo informações relevantes de cada partida.
- Aprendi a transformar dados brutos em informações mais legíveis, agrupando kills, deaths e assists em um único campo `kda` e organizando a ordem dos atributos do JSON usando `@JsonPropertyOrder`.
- Entendi melhor a diferença entre DTOs de integração, que representam respostas da Riot API, e DTOs da aplicação, que representam exatamente os dados que desejo expor ao cliente.
- Completei a primeira versão funcional do histórico de partidas: informar um Riot ID → buscar PUUID → consultar as partidas recentes → obter os detalhes de cada partida → extrair os dados do jogador → retornar um histórico estruturado.

## Dia 8
- Explorei uma extensão da funcionalidade de histórico de partidas, implementando uma busca por partidas que contenham um campeão específico, independentemente de qual jogador da partida o tenha utilizado.
- Aprendi a lidar com buscas de tamanho variável, percebendo que a quantidade de partidas analisadas pode ser diferente da quantidade de resultados encontrados quando existem filtros aplicados.
- Refatorei o método de busca para utilizar `List<HistoryEntry>` ao invés de arrays fixos, eliminando posições vazias no retorno quando não há resultados suficientes.
- Adicionei um limite máximo de partidas analisadas para evitar excesso de requisições e lidar melhor com as limitações da Development API Key da Riot.
- Investiguei o erro HTTP 429 (Too Many Requests) e implementei uma estratégia inicial de tratamento para informar ao usuário quando o limite de requisições da API for atingido.
- Aprendi melhor como equilibrar funcionalidades desejadas com restrições de APIs externas, considerando desempenho, limites de uso e experiência do usuário.

## Dia 9
- Criei o DTO `PlayerStatistics` para representar estatísticas agregadas de um jogador ao longo de múltiplas partidas.
- Implementei o cálculo de vitórias, derrotas, taxa de vitória, médias de kills, deaths e assists a partir do histórico de partidas gerado pela aplicação.
- Adicionei o cálculo do KDA médio, separando a lógica de apresentação dos dados e os valores utilizados internamente.
- Reutilizei o histórico estruturado de partidas como base para gerar estatísticas, mantendo separadas as responsabilidades de consulta à Riot API e processamento dos dados.
- Completei a primeira versão do endpoint de estatísticas: informar um Riot ID → obter o histórico de partidas → calcular estatísticas agregadas → retornar um resumo do desempenho do jogador.

## Dia 10
- Expandi o sistema de estatísticas do jogador adicionando métricas mais detalhadas a partir do histórico de partidas, incluindo melhor e pior KDA, sequência de vitórias e totais de kills, deaths e assists.
- Aprendi a utilizar estruturas de chave e valor Map para agrupar informações durante a análise de dados, evitando múltiplas iterações desnecessárias sobre o histórico.
- Implementei a análise de campeões utilizados pelo jogador, utilizando Map para contabilizar a quantidade de partidas por campeão e identificar o campeão mais jogado dentro do limite de partidas analisadas.
- Adicionei o cálculo de vitórias com o campeão mais jogado, permitindo comparar a frequência de uso do campeão com o desempenho obtido nele.
- Revisei a estrutura do AnalyticsService, identificando oportunidades de refatoração e mantendo a separação entre consulta de dados externos, transformação em DTOs e cálculo de estatísticas.

## Dia 11
- Ampliei a flexibilidade das consultas de histórico e estatísticas, permitindo filtrar partidas por modo de jogo sem duplicar a lógica de processamento já existente.
- Refatorei a API do `AnalyticsService` utilizando sobrecarga de métodos para oferecer diferentes níveis de configuração, mantendo uma única implementação principal da lógica.
- Padronizei os métodos de busca de partidas presentes em `AnalyticsService`.
- Documentei os endpoints disponíveis no `README`, tornando a utilização da API mais clara para futuros usuários e colaboradores.

## Dia 12
- Corrigi a funcionalidade de `winStreakCurrent`, que previamente não funcionava.
- Corrigi a paginação de partidas em `buscarDadosPartidas`. Previamente, se `numeroPartidas` fosse menor que 20, o método não analisaria as partidas entre `numeroPartidas` e 20, fazendo com que históricos fossem montados erroneamente, o que também implicava em estatísticas errôneas.
- Adicionei filtro por campeão jogado a `buscarDadosPartidas` e `calcularEstatísticas`.
- Refatorei código que iterava por partidas/participantes em `buscarDadosPartidas`, eliminando casos redundantes.