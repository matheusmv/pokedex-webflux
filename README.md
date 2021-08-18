# Pokédex API

API reativa com Spring Webflux e MongoDB

### Obter todos os pokémons registrados na base de dados

  - GET /pokemons

### Obter os dados de um pokémon através do id

  - GET /pokemons/{id}

### Registrar um novo pokémon na base de dados

  - POST /pokemons
  
```json
  {
    "name": "Charizard",
    "category": "Flame",
    "abilities": "Blaze",
    "weight": 90.5
  }
```

### Alterar dados de um pokémon através do id

  - PUT /pokemons/{id}

```json
  {
    "weight": 87.4
  }
```

### Remover um pokémon da base de dados através do id

  - DEL /pokemons/{id}
