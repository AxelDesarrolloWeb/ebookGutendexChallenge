# Sistema de Gestión de Libros - Gutendex

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

Este proyecto es una aplicación de línea de comandos que permite buscar, gestionar y analizar libros utilizando la API de Gutendex (Proyecto Gutenberg). Los datos de los libros se almacenan en una base de datos PostgreSQL para realizar consultas posteriores sin necesidad de volver a la API.

## Características principales

- **Búsqueda de libros por título o autor**: Guarda los resultados en la base de datos para consultas posteriores
- **Top 10 libros más descargados**: Muestra los libros más populares según su número de descargas
- **Búsqueda por idioma**: Filtra libros por su idioma (código de 2 letras)
- **Búsqueda por tema**: Encuentra libros que traten sobre un tema específico
- **Libros con más de 25000 descargas**: Filtra los libros más populares
- **Búsqueda de autores vivos en un año**: Encuentra autores que vivían en un año específico
- **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos
- **Listar autores registrados**: Muestra todos los autores únicos almacenados en la base de datos

## Tecnologías utilizadas

- **Java 17**: Lenguaje de programación principal
- **Spring Data JPA**: Para la persistencia de datos
- **PostgreSQL**: Base de datos relacional para almacenar los libros
- **Maven**: Gestión de dependencias y construcción del proyecto
- **API Gutendex**: Fuente de datos de libros

## Configuración y ejecución

### Requisitos previos

- Java 17
- PostgreSQL instalado y en ejecución
- Maven

### Pasos para ejecutar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/AxelDesarrolloWeb.git
   cd AxelDesarrolloWeb
   ```

2. **Crear la base de datos en PostgreSQL**:
   - Crea una base de datos llamada `challenge_literatura` (o el nombre que prefieras)

3. **Configurar las credenciales de la base de datos**:
   - Edita el archivo `src/main/resources/application.properties` y configura las siguientes propiedades:
     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/gutendex
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     spring.jpa.hibernate.ddl-auto=update
     ```

5. **Usar la aplicación**:
   - Una vez iniciada, la aplicación mostrará un menú interactivo en la consola con las opciones disponibles

## Capturas y demostración

### Video demostración


https://github.com/user-attachments/assets/40801932-b2c0-4b9a-b75d-c33dce1b3974



### Imágenes

**Consola mostrando el top 10 de libros**  
![ejemploConsola_](https://github.com/user-attachments/assets/9037b523-fea8-432f-8169-3d8a0041b15c)


**Datos en TablePlus**  
![ejemploTablePlus](https://github.com/user-attachments/assets/57650577-19f1-4efd-a4b0-1d750fb64f71)


## Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── alvax/
│   │           └── ebookgutendex/
│   │               ├── model/       # Entidades (Libro, etc.)
│   │               ├── repository/  # Repositorios de datos
│   │               ├── service/     # Servicios (API, conversión de datos)
│   │               ├── principal/   # Lógica de la aplicación y menú
│   │               └── EbookGutendexApplication.java # Clase principal
│   └── resources/
│       └── application.properties   # Configuración
└── test/                            # Pruebas (opcional)
```

## Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir los cambios que quieras hacer, o envía un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.
