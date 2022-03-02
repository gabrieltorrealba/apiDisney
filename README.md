# Disney_api_rest

API REST de películas

Reseña de la API:
Desarrollada con Java 11 siguiendo el estándar de java 8. Spring framework. Spring Boot. Gradle. Hibernate/JPA.
Autenticacion con Spring Security y JWT.
Patrón de diseño MVC.
Paginación.
Filtros con JPA Specifications y Querys.
Configuración de CORS para llamadas externas.
Implementación de lombok.
Integrado con Swagger.
Base de datos H2 console para poder subirlo al servidor de heroku.
Especial focus en las buenas prácticas en el desarrollo de APIs REST.


Autenticación de usuarios
Para realizar solicitudes a los endpoints subsiguientes el usuario deberá contar con un token que obtendrá al autenticarse. Para ello, deberán desarrollarse los endpoints de registro y login, que permitan obtener el token.

El endpoint encargado de la autenticación es:
- POST /auth/login

Para loguearse se puede utilizar las siguientes credenciales:
- Email: gabriel.torrealba33@gmail.com
- Password: gabriel1234

ACLARACIÓN: todas las solicitudes GET son de acceso público por lo que no se necesita enviar el token de acceso.

*Registro de usuarios*
También se puede registrar un usuario nuevo para posteriormente loguearse y obtener el token de acceso.

El endpoint encargado del registro es:
- POST /auth/register

Para esta solicitud es necesario enviar un objeto JSON con los atributos del usuario:
El JSON debe tener la siguiente estructura:

- POST /auth/register
  {                          
  "firstName": "Jhon",
  "lastName": "Doe",
  "email": "jhond@gmail.com",
  "password": "jhon123"    
  }

*REST API*
Para ingresar a la API via rest se debe contar con permisos de administrador, el endpoint es:

- /rest
  Ejemplo de solicitud:
- /rest/users
- /rest/movies
- /rest/characters
- /rest/genders

***** PELICULAS *****

*Listado de películas:*
El listado muestra:
- ID
- Image
- Year
- Title

El endpoint es
-GET /movies

*Detalles de película:*
En el detalle se muestran todos los atributos de las peliculas como asi tambien su genero y personajes relacionados.
- ID
- Image
- Title
- Gender
- Year
- Rating
- Characters

Para especificar la pelicula que se desea obtener se debe pasar el id como parametro:
-GET /movies/{id}

El número de página debe especificarse por parámetro:
- GET /movies?page=numPage
-
El orden debe especificarse por parámetro:
- GET /movies?sort=atributo,asc|desc
-
Ejemplo de solicitud:
- GET /movies?page=0&sort=titulo,desc

*Búsqueda de películas:*
Se puede buscar peliculas por título y/o año.

Para especificar el termino de búsqueda se deberán enviar como parámetros de query.
- GET /movies?name=nombre
- GET /movies?year=año

Igualmente se puede unificar la búsqueda con todos los parametros
- GET /movies?name=nombre&premiereDate=fecha&gender=genero

Ejemplo de querys:
- GET /movies?title=fantasia
- GET /movies?year=1940
- GET /movies?name=fantasia&year=1940

*Creación, edición y eliminación de películas*
Para las siguientes solicitudes es necesario estar autenticado y enviar el token de acceso en el header de la solicitud.

Debe enviarse con la key Authorization y la palabra "Bearer " + espacio, seguido del token de acceso, de la siguiente manera
Authorization: "Bearer token_access"

Ejemplo:
Authorization: "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicml0b21sdXpAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NDUwNzUwMDR9.PGP9TyyKGAuP0bRQrYJLEoxx_HS_VyGxuiAp0_OD2WI"

*Creación de película*
Para crear una película es necesario enviar un objeto JSON con los atributos de la película

El endpoint es:
- POST /movies
  {                          
  "image": "string(url)",
  "title": "string",
  "gender": "string", 
  "year": "string(yyyy-mm-dd)",    
  "rating": "string(del 1 al 5)"     
  }


*Edición de película:*
Para editar una película es necesario especificar el ID de la película en el path y se debe enviar un objeto JSON con los atributos de la película

El endpoint es:
- PUT /movies/{id}
  {                         
  "image": "string",
  "title": "string",
  "gender": "string", 
  "year": "string",    
  "rating": "string",    
  "overview": "string"    
  }


*Eliminación de película:*
Para especificar la película que será eliminada es necesario enviar el ID de la película en el path

El endpoint es:
- DELETE /movies/{id}

Ejemplo de solicitud:
- DELETE /movies/3


**** PERSONAJES *****

*Listado de personajes*
El listado muestra:
- ID
- Image
- Name

El endpoint es:
- GET /characters

El número de página debe especificarse por parámetro:
- GET /characters?page=numPage

El orden debe especificarse por parámetro:
- GET /characters?sort=atributo,asc|desc
-
Ejemplo de solicitud:
- GET /characters?page=0&sort=age,desc

*Detalles de personaje*
En el detalle se muestran todos los atributos de los personajes como asi tambien sus películas relacionados.
- ID
- Image
- Name
- Gender
- Age
- Weight
- History
- Movies

Para especificar el personaje que se desea obtener se debe pasar el id como parametro:
- GET /characters/{id}

*Búsqueda de personajes*
Se puede filtrar por nombre, edad y peso.
Para especificar el término de búsqueda se deberán enviar como parámetros de query.
- GET /characters?name=nombre
- GET /characters?age=edad
- GET /characters?weight=peso

*Creación de personajes:*
Para esta solicitud es necesario estar autenticado y enviar el token de acceso en el header de la solicitud.
Debe enviarse con la key Authorization y la palabra "Bearer " + espacio, seguido del token de acceso, de la siguiente manera

Authorization: "Bearer token_access"
Ejemplo:
Authorization: "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicml0b21sdXpAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NDUwNzUwMDR9.PGP9TyyKGAuP0bRQrYJLEoxx_HS_VyGxuiAp0_OD2WI"

*Creación de personaje:*
Para crear un personaje es necesario enviar un objeto JSON con los atributos del mismo

El endpoint es:
- POST /characters
  {                          
  "image": "string",
  "name": "string",                            
  "weight": Float,
  "age": Integer,
  "history": "string"
  }

*Eliminación de personaje:*
Para especificar el personaje que será eliminado es necesario enviar el ID del mismo en el path

El endpoint es:
- DELETE /characters/{id}

***** GENEROS *****
Listado de géneros

El listado muestra:
- ID
- Name

El endpoint es
- GET /genders

Detalles de género
En el detalle se muestran todos los atributos de los géneros como asi tambien sus películas relacionados.
- ID
- Name
- Movies

Para especificar el género que se desea obtener se debe pasar el id como path:
- GET /genders/{id}

Creación de géneros
Para esta solicitud es necesario estar autenticado y enviar el token de acceso en el header de la solicitud.
Debe enviarse con la key Authorization y la palabra "Bearer " + espacio, seguido del token de acceso, de la siguiente manera
Authorization: "Bearer token_access"
Ejemplo:
Authorization: "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicml0b21sdXpAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NDUwNzUwMDR9.PGP9TyyKGAuP0bRQrYJLEoxx_HS_VyGxuiAp0_OD2WI"

Creación de género
Para crear un género es necesario enviar un objeto JSON con los atributos del mismo

El endpoint es:
- POST /genders
  {   
  "name": "string"                                                     
  }

