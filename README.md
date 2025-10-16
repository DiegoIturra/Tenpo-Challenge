# 🧮 Tenpo Challenge Backend - Spring Boot + Docker

## 🚀 Descripción

Servicio REST que realiza cálculos con porcentaje dinámico, guarda un historial de llamadas y soporta caché. Este proyecto cumple con los requisitos de utilizar **Spring Boot (Java 21)**, **PostgreSQL**, **caché en memoria** y **despliegue mediante Docker Compose**.

---

## 🛠️ Requisitos Previos

Asegúrate de tener instalado lo siguiente:

- Git
- Docker y Docker Compose
- JDK 21 (Para ejecución y desarrollo local)

---

## 🐳 Ejecución con Docker Compose (Recomendado)

Sigue estos pasos para levantar tanto la base de datos PostgreSQL como la API de Spring Boot en contenedores.

### 1. Clonar el repositorio

```bash
git clone https://github.com/DiegoIturra/Tenpo-Challenge.git
cd Tenpo-Challenge
```

---

### 2. Configuración de Variables de Entorno

El proyecto utiliza un archivo `.env` para gestionar las credenciales de la base de datos y la configuración de Spring Boot.

**Crear el archivo:** Copia el archivo de ejemplo para crear tu configuración.

```bash
cp .env.example .env
```

**Editar `.env`:** Modifica los valores entre `< >` con tus credenciales deseadas.  
Es crucial que los valores de `CHALLENGE_PASSWORD`, `POSTGRES_PASSWORD`, etc., sean iguales para que el servicio pueda conectar a la base de datos.

```bash
# Estructura del archivo .env
# --- Configuración de Spring Boot (application.properties)
CHALLENGE_USERNAME=<user>
CHALLENGE_PASSWORD=<password>
CHALLENGE_URL_DB=jdbc:postgresql://db:5432/<tenpo_db>
CHALLENGE_DATABASE=<tenpo_db>

# --- Configuración de PostgreSQL (para el contenedor 'db')
POSTGRES_PASSWORD=<password>
POSTGRES_USER=<user>
POSTGRES_DB=<tenpo_db>
```

---

### 3. Construir y Levantar Contenedores

Ejecuta Docker Compose. Puedes elegir si ver los logs en tiempo real o ejecutarlo en segundo plano.

| Comando | Efecto |
|----------|---------|
| `docker compose up --build` | Construye las imágenes y muestra los logs en la consola. |
| `docker compose up --build -d` | Construye las imágenes y ejecuta los servicios en modo detached (segundo plano). |

**Nota Importante sobre el Build:**  
El Dockerfile del servicio backend ejecuta todos los tests unitarios con la instrucción:

```dockerfile
RUN mvn clean verify -DforkCount=0
```

Si algún test falla o produce un error de runtime, el proceso de construcción de la imagen se detendrá y fallará.

---

## 🔗 Uso y Documentación de la API

Una vez que Docker Compose esté completamente levantado, la aplicación estará disponible en tu máquina local.

### 🏠 Base URL

```
http://127.0.0.1:8080/
```

---

### Endpoints Disponibles (GET)

| Funcionalidad | Método | URL | Descripción |
|----------------|---------|-----|--------------|
| Cálculo Dinámico | GET | `/calculate?num1={x}&num2={y}` | Realiza la suma y aplica el porcentaje dinámico. |
| Historial Completo | GET | `/history/all` | Devuelve todos los registros de historial. |
| Historial Paginado | GET | `/history/paginated?page={p}&size={s}` | Devuelve el historial con paginación y ordenación. |

---

### 📑 Documentación de la API (Swagger UI)

La documentación completa e interactiva de la API, generada con **SpringDoc OpenAPI (Swagger)**, está disponible en:

```
http://localhost:8080/swagger-ui.html
```

En esta interfaz, puedes ver la descripción de cada endpoint, los modelos de datos y probar las llamadas directamente.

---

## ⭐ Consideraciones en el desarrollo

Se mencionan algunas decisiones al momento de desarrollar el challenge:

- **Servicio externo:**  
  Se simuló la consulta a un servicio externo con un Service que genera un número aleatorio que actua como porcentaje y que puede fallar de forma aleatoria para poder simular un fallo de un servicio externo.


- **Tests en Docker:**  
  Se configuró el `maven-surefire-plugin` con la opción `-DforkCount=0` en el Dockerfile para resolver errores de runtime (`Errors: 1`) comunes en ambientes Linux/Docker, asegurando que los tests se ejecuten de manera estable durante el build.


- **Datos:**  
  El historial de llamadas se registra de manera asíncrona (`@Async`) para no penalizar el tiempo de respuesta del endpoint de cálculo.


- **Manejo de Caché:**  
  Implementación de caché en memoria con `CacheManager` para el porcentaje, aplicando una política de fallback si el servicio externo falla, se optó por no usar Redis y dejar este punto lo mas simple posible.


- **Persistencia:**  
  Uso exclusivo de **PostgreSQL** para el historial, cumpliendo el requisito de base de datos relacional en un contenedor separado.


- **Documentacion:**  
  Se usó **Swagger** para mostrar la api documentada lo mas claro posible.
