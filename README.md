# DOCUMENTACIÓN PRUEBA TÉCNICA

## INSTALACIÓN DEL PROYECTO.

Para instalar este proyecto es suficiente con clonar el repositorio de gitHub.
Una vez descargado, entrar en eclipse y en la opcióm import -> maven -> existing maven project
Antes de poder lanzar el proyecto, es necesario desde la línea de comandos acceder a la carpeta donde se encuentra el proyecto, y al mismo nivel donde esté el archivo pom.xml hay que lanzar el comando:
mvn clean install (se entiende que previamente se ha instalado maven)

## CONSIDERACIONES Y SUPUESTOS.

Para la realización de esta demostración me he apoyado en una base de datos H2 que se lanza directamente al levantar el servidor. 

La BDD consta de dos tablas, ROOM (salas) y BOOK (reserva), la tabla ROOM tiene una relación 1 a muchos con la tabla BOOK, siendo el FK en la tabla BOOK el id de la tabla ROOM.

El servicio de población de datos crea 52 salas diferentes, nombradas como las provincias españolas y les dá el código formado por los cuatro primeros caracteres del nombre en mayúscula y si hubiera algún espacio lo sustituye por un guión, así por ejemplo, la sala “Sevilla” tiene código “SEVI” y la sala “A Coruña” tiene código “A-CO”. La capacidad y elementos disponibles se generan de manera aleatoria.

La capacidad y los elementos de los que disponen las salas se generan de forma aleatoria.

El servicio de población de datos crea reservas aleatorias para las 52 salas en los próximos 15 días contando como primer día, el día que se llama al servicio. No se tienen en cuenta sábados, domingos ni festivos. 

Se ha supuesto que el edificio abre sus puertas de 7:00 a 22:00h, por lo que no es posible reservar fuera de este rango.

El tramo mínimo de reserva es de 15 minutos y sólo es posible reservar de 15 en 15 minutos.

Se ha interpretado la optimización de la ocupación de salas contabilizando todos los tramos de 15 minutos que tiene ya ocupados una sala para una fecha concreta, y de esta forma se da prioridad a las salas con menos tramos libres de 15 minutos. 

## ENDPOINTS

1. Se puede acceder a la consola de la misma una vez levantado el servidor a través de la url: http://localhost:8080/console-h2

2. Cuando se lanza la aplicación las tablas anteriores se crean pero no contienen datos, por tanto, se ha habilitado un endpoint: http://localhost:8080/init/application que ejecuta un servicio encargado de poblar estas. Obviamente, es necesario llamar a este endpoint para que la aplicación funcione correctamente.

3. Para reservar una sala hay que llamar al endpoint: 

    http://localhost:8080/book/room?day=&month=&year=&members=&fromHour=&fromMinute=&toHour=&toMinute=&needTv=&needProyector=&needDigitalBlackboard=&needVideoconference=&needWifi=

    Todos los parámetros de este endpoint son obligatorios y, por otra parte:

    - day tiene que ser un entero
    - month tiene que ser un entero
    - year tiene que ser un entero
    - fromHour tiene que ser un entero
    - fromMinute tiene que ser un entero
    - toHour tiene que ser un entero
    - toMinute tiene que ser un entero
    - needTv tiene que ser un booleano
    - needProyector tiene que ser un booleano
    - needDigitalBlackboard tiene que ser un booleano
    - needVideoconference tiene que ser un booleano
    - needWifi tiene que ser un booleano

    Llamada a endpoint de ejemplo:
        http://localhost:8080/book/room?day=25&month=3year=2020&members=5&fromHour=7&fromMinute=30&toHour=9&toMinute=30&needTv=true&needProyector=false&needDigitalBlackboard=false&needVideoconference=false&needWifi=false

4. Para obtener una lista de todas las salas hay que llamar al endpoint:
	http://localhost:8080/room/getAll

5. Para crear una sala hay que llamar al endpoint:
    http://localhost:8080/room/create?code=&name=&capacity=&hasTv=&hasProyector=&hasDigitalBlackboard=&hasVideoconference=&hasWifi=

	Todos los parámetros de este endpoint son obligatorios y, por otra parte:

    - code tiene que ser un string
    - name tiene que ser un string
    - capacity tiene que ser un entero
    - hasTv tiene que ser un booleano
    - hasProyector tiene que ser un booleano
    - hasDigitalBlackboard tiene que ser un booleano
    - hasVideoconference tiene que ser un booleano
    - hasWifi tiene que ser un booleano

    Llamada a endpoint de ejemplo:
        http://localhost:8080/room/create?code=TAHO&name=Tahoma&capacity=20&hasTv=true&hasProyector=true&hasDigitalBlackboard=true&hasVideoconference=true&hasWifi=true

6. Para actualizar una sala hay que llamar al endpoint:
    http://localhost:8080/room/update?code=&name=&capacity=&hasTv=&hasProyector=&hasDigitalBlackboard=&hasVideoconference=&hasWifi=

	El parámetro code es necesario, el resto son opcionales, por otra parte:

    - code tiene que ser un string
    - name tiene que ser un string
    - capacity tiene que ser un entero
    - hasTv tiene que ser un booleano
    - hasProyector tiene que ser un booleano
    - hasDigitalBlackboard tiene que ser un booleano
    - hasVideoconference tiene que ser un booleano
    - hasWifi tiene que ser un booleano

    Llamada a endpoint de ejemplo:
        http://localhost:8080/room/update?code=TAHO&hasDigitalBlackboard=false
        
    7. Para actualizar una sala hay que llamar al endpoint:
        http://localhost:8080/room/delete?code=
    
	El parámetro code es necesario, por otra parte:
	
    - code tiene que ser un string

    Llamada a endpoint de ejemplo:
        http://localhost:8080/room/delete?code=TAHO

## LÓGICA DE SERVICIOS

Elements (Elementos)

Para resolver los elementos de los que dispone una sala se ha creado una interfaz con una constantes que dan valor a cada uno de los elementos posibles, las clases que vayan a usar los elementos deberán implementar esta interfaz, como no tiene métodos, sólo constantes, no es necesario implementar métodos.

Cada elemento tiene un valor que es un número primo, así TV = 2 y WIFI = 11. Para resolver si una sala tiene alguno de estos elementos en la columna Elements de la tabla Room se le da un valor igual a la multiplicación de los valores de los elementos de que dispone, así si una sala sólo tiene WIFI tendría en el campo Elements el valor 11, y si tuviera TV y WIFI tendría el valor 22 (2 * 11), Si no dispone de ningún elemento tendría el valor 1.

Cuando se quiere filtrar si una sala tiene un elemento determinado sólo hay que comprobar que el valor del campo Elements de esa sala es divisible por el Valor del elemento, por ejemplo, si quiero obtener las salas con WIFI sólo tengo que filtrar las salas cuyo valor Elements sea divisible entre 11 (room.getElements() % WIFI == 0).

Como en la tabla sólo se guarda como un entero, es muy sencillo ordenar los resultado posibles por valor, es decir, si se busca una sala con WIFI y se dispone de una sala con WIFI y otra con TV y WIFI, al ordenar los valores, la sala con sólo WIFI tendría un valor 11 que tendría preferencia sobre la sala con TV y WIFI que tendría un valor de 22. De esta forma, las salas con igual capacidad y menos recursos de los necesarios tienen preferencia sobre las salas con más recursos.

BookARoom (Elementos)

Para facilitar la contabilización de la ocupación de las salas, cada tramo de 15 minutos se ha reducido a 1 unidad, de tal forma que de 7:00 a 22:00 se ha reducido a 60 tramos (0 a 59). De 7:00 a 7:15 sería el tramo 0 de 21:45 a 22:00 sería el tramo 59.

La persistencia en BDD ya tiene en cuenta esta conversión

El controlador llama a un convertidor para convertir las horas y minutos a tramos controlando los posibles errores.

Cuando se va a reservar una sala el funcionamiento del servicio es el siguiente:
Se obtienen todas las reservas de todas la salas para el día indicado
Se descartan todas las reservas en salas cuya capacidad sea inferior a los miembros de la reunión
Se descartan todas las reservas en las salas que no dispongan de los elementos requeridos como se indica en el apartado anterior (Elements)
Se descartan todas las reservas en una misma sala en la que ya haya una reserva que interseccione con el tramo de reserva deseado.
Si quedan reservas, se contabilizan los espacios ocupados por estas reservas y se obtiene las salas en las que están.
Las salas disponibles se ordenan por capacidad, elementos y ocupación libre.
De la lista ordenada resultante se extrae el primer elemento como mejor opción y se realiza la reserva.
