Instrucciones para levantar el backend de referencia
Requisitos previos
•    Docker Desktop instalado y en ejecución (Windows / macOS / Linux).
•    Git instalado para clonar el repositorio del profesor.
•    Android Studio con un emulador configurado (API 24 o superior recomendado).

Paso 1 – Clonar el repositorio
Abre una terminal y ejecuta:
git clone <URL-del-repositorio-del-profesor>
cd Docker-Flask/ORM

Paso 2 – Construir y ejecutar el contenedor
docker compose up --build
Docker descargará la imagen base, instalará las dependencias y arrancará el servidor en el puerto 5000. Deberías ver en la terminal:
 * Running on http://0.0.0.0:5000
 * Debug mode: on

Paso 3 – Verificar que el servicio funciona (opcional pero recomendado)
Desde otra terminal, ejecuta los siguientes comandos curl para probar los endpoints antes de conectarte desde Android:
# Verificar estado
curl http://localhost:5000/

# Registrar un usuario
curl -X POST http://localhost:5000/register \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"alumno01\",\"password\":\"pass123\"}"

# Iniciar sesión
curl -X POST http://localhost:5000/login \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"alumno01\",\"password\":\"pass123\"}"

Dirección IP para el emulador de Android
El emulador de Android NO puede acceder a localhost de tu computadora de la misma manera que un navegador. Usa la dirección especial del emulador:

Entorno    URL base a usar en Android    Nota
Emulador AVD (Android Studio)    http://10.0.2.2:5000    IP especial del emulador
Dispositivo físico (misma red Wi-Fi)    http://<IP-de-tu-PC>:5000    Consulta tu IP con ipconfig / ip addr
