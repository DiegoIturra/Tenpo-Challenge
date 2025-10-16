# 🧮 Tenpo Challenge Backend - Spring Boot + Docker

## 🚀 Descripción
Servicio REST que realiza cálculos con porcentaje dinámico, guarda un historial de llamadas y soporta caché.

## 🐳 Ejecución con Docker Compose

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/DiegoIturra/Tenpo-Challenge.git
   cd Tenpo-Challenge

2.  Archivo `.env`

   Antes de ejecutar el proyecto, debes crear un archivo `.env` en la raíz del repositorio.  
   Este archivo contiene las variables de entorno necesarias tanto para la API como para la base de datos.
   
   Ejemplo de estructura:
   
   ```env
   # corresponde a valores en application.properties
   CHALLENGE_USERNAME=<username>
   CHALLENGE_PASSWORD=<password>
   CHALLENGE_URL_DB=jdbc:postgresql://db:5432/<database>
   CHALLENGE_DATABASE=<database>
   
   # Corresponde a valores en la Base de Datos
   POSTGRES_PASSWORD=<password>
   POSTGRES_USER=<user>
   POSTGRES_DB=<database>
Los valores entre < > deben ser los mismos para ambos.

3. Ejecutar docker compose para levantar DB y API

   ```bash
   docker compose up --build