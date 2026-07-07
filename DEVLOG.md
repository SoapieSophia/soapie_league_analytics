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