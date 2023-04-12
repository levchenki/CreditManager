# CreditManager
## Запуск программы
#### 1. Установите Docker и docker-compose на свой компьютер.
#### 2. Склонируйте репозиторий программы.
```bash
git clone https://github.com/levchenki/CreditManager
```
#### 3. Создайте файл `.env` из `.env.origin`:
```bash
cd CreditManager
cp .env.origin .env
```
#### 4. Откройте файл `.env` в текстовом редакторе и заполните значения переменных
<pre>
POSTGRES_USER=           # имя пользователя базы данных PostgreSQL 
POSTGRES_PASSWORD=       # пароль для доступа к базе данных PostgreSQL
POSTGRES_DB=             # название базы данных PostgreSQL
POSTGRES_PORT=           # внешний порт PostgreSQL
POSTGRES_CONTAINER_PORT= # внутренний порт PostgreSQL 
SERVER_PORT=             # порт, на котором будет запущен сервер
CLIENT_PORT=             # порт, на котором будет запущен клиент
</pre>
#### 5. Запустите приложение с помощью команды 
```bash
docker-compose up -d
```
#### 6. Приложение доступно по адресу: 
http://localhost:${CLIENT_PORT} \
где `CLIENT_PORT` - значение порта, указанное в файле `.env`