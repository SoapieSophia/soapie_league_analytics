# soapie_league_analytics

Projeto desenvolvido para aprender desenvolvimento backend em Java utilizando a API da Riot Games.

## Objetivos

- Consumir APIs REST
- Processar dados JSON
- Calcular estatísticas de partidas
- Aprender Spring Boot
- Desenvolver um projeto completo para portfólio

## Funcionalidades planejadas

- Buscar jogador
- Mostrar últimas partidas
- Calcular Win Rate
- Calcular KDA médio
- Mostrar campeões mais utilizados

## Restrições

- A busca de partidas é limitada a 100 registros por consulta devido aos limites de requisições impostos pela Development API Key da Riot.

## Endpoints

- `/history` -> Retorna o histórico de partidas de um jogador alvo, contendo informações de desempenho do jogador em cada partida.
- `/history/champion` -> Retorna partidas de um jogador alvo em que um campeão específico esteve presente, exibindo os dados do jogador que utilizou esse campeão.
- `/history/statistics` -> Retorna estatísticas agregadas de um jogador com base em uma quantidade definida de partidas. Permite filtrar resultados por modo de jogo.