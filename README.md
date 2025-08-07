## PokemonAPI

API REST en Java 17 que consulta datos de Pokémon desde [PokeAPI](https://pokeapi.co/) y devuelve información simplificada en formato JSON. Además, guarda un historial de búsquedas en una base de datos H2 en memoria.

---

## Características

- Consulta datos de cualquier Pokémon desde `/pokemon/{nombre}`
- Devuelve información limpia: nombre, especie, tipo(s), habilidades, ataques, estadísticas e imagen en base64
- Guarda historial de búsquedas en base de datos H2
- Documentación de la API con Swagger/OpenAPI
- Contenedor Docker listo para desplegar

---

## Tecnologías usadas

- Java 17
- Spring Boot 3
- H2 Database (en memoria)
- PokeAPI (API externa)
- Docker
- Maven
- JUnit & Mockito (para pruebas)

---

## Instalación y ejecución local

### Clona el proyecto

```bash
git clone https://github.com/tu_usuario/PokemonAPI.git
cd PokemonAPI

#Ejecutar con maven 
./mvnw spring-boot:run


./mvnw clean package -DskipTests
docker build -t pokemonapi:latest .
docker run -p 8080:8080 pokemonapi:latest
http://localhost:8080/swagger-ui.html




http://localhost:8080/swagger-ui/index.html


./mvnw test


http://localhost:8080/h2-console
Configuración
JDBC URL: jdbc:h2:mem:db

Usuario: admin

Contraseña: (vacío)




GET http://localhost:8080/pokemon/pikachu

ejeplo
{
  "nombre": "pikachu",
  "especie": "pikachu",
  "tipos": ["electric"],
  "habilidades": ["static", "lightning-rod"],
  "ataques": [...],
  "estadisticas": {
    "hp": 35,
    "attack": 55,
    ...
  },
  "imagenBase64": "iVBORw0KGgoAAAANSUhEUg..."
}




