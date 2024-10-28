# PROYECTO INTEGRADOR PROGRAMACION II
## INTEGRANTES:
- Luciano Canovas
- Luciano Suarez

## DESCRIPCIÓN:
El proyecto consiste en un reproductor de música, utilizando librerías como "JDBC" para la conexión a una base de datos y "JLayer" para la reproducción de la música.

## FUNCIONALIDADES:
### USUARIO:
- Reproducir música
- Crear playlist
- Escuchar Album
### MUSICO:
- Cargar música
- Eliminar música
- Cargar Album
- Eliminar Album
### HOST:
- Cargar Podcast
- Eliminar Podcast

## REQUISITOS DEL INTEGRADOR:
- 3 Niveles de herencia: Usuario -> Artista -> Musico/Host.
- Sobrecarga de métodos: En los tipos de usuarios y en los tipos de elementos Multimedia.
- Clases abstractas: Media, Artista.
- Clases concretas: Usuario, Músico, Host.
- Conexión a base de datos: SQLite.
- Sistema CRUD: Crear, Leer, Actualizar, Eliminar.
- Interfaz por consola, manejo de excepciones correspondientes.
- Encapsulamiento: Atributos protegidos accesibles por clases hijas.
- Interfaces con polimorfismo: Menus de usuario, músico y host.
- Composición: Album, compuesto por Música.
- Agregación: Playlist, compuesta por Música y Podcast, pero no dependiente de ellos.

## USUARIOS DE PRUEBA:
### Usuario
- USUARIO: lucho | CONTRASEÑA: password
### Musico
- USUARIO: Coldplay | CONTRASEÑA: password
- USUARIO: El Cuarteto de NOS | CONTRASEÑA: password
### Host
- USUARIO: Ramiro Diz | CONTRASEÑA: password