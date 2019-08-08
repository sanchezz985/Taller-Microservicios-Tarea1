# Taller-Microservicios-Tarea2

Tarea 1 del taller de microservicios

# Herramientas utilizadas

- Spring Boot
- Java JDK 1.8

# Requerimientos

Se debe contar con el JDK de java en su versión 8.

# Levantando la aplicación
Este repositorio consta de 2 proyectos:

- [Acme-HR-System-Employee] - Proyecto con la lógica para crear registros de empleados y ligarlos a Workstations, levanta por el puerto `8080` con el contexto `/acme-hr-system-employee`.
- [Acme-HR-System-Workstation] - Proyecto con la lógica para crear registros de workstations, levanta por el puerto `8081` con el contexto `/acme-hr-system-workstation`.

El primer paso es posicionarte en el directorio de cada uno de los proyectos donde hayas descargado el repositorio y ejecutar el siguiente comando para compilar los proyectos:

```sh
$ mvn clean package -DskipTests
```

Si todo sale bien el proyecto compilará y generará un Jar en la carpeta target el cual se ejecuta de la siguiente manera

```sh
$ java -jar target/{nombre_del_jar}.jar
```
Una vez que los proyectos estan iniciados hay que hacer lo siguiente
- Mandar una peticion al proyecto `Acme-HR-System-Employee` en el endpoint `localhost:8080/acme-hr-system-employee/v1/employees` con la siguiente estructura Json

```sh
{"firstName":"Pepe","lastName":"Perez"}
```
Si la respuesta es exitosa devolvera algo asi:

```sh
{"id":1,"firstName":"Pepe","lastName":"Perez","employeeNumber":"00001","workstations":[]}
```
Una vez que hayas hecho la peticion podras ver el registro creado en la base de datos que se creo al levantar el proyecto, puedes acceder a la base de datos desde la siguiente url `http://localhost:8080/acme-hr-system-employee/h2`, los datos de acceso son los que estan por default a excepción del siguiente

JDBC URL : `jdbc:h2:mem:acme-hr-employee-system`

Ahora lanza una petición con el siguiente Json

```sh
{"firstName":"Pedrito","lastName":"Vazquez","workstations":[{"vendor":"Mac","model":"Mackbook pro"}]}
```
La respuesta debería ser algo parecido a el siguiente Json

```sh
{"id":3,"firstName":"Pedrito","lastName":"Vazquez","employeeNumber":"00003","workstations":[{"id":2,"vendor":"Mac","model":"Mackbook pro","facilitiesSerialNumber":"00102","employeeId":3}]}
```

En esta ocasión agregamos un nuevo registro de employee pero también se genero un registro de Workstation, este registro esta ligado a el employee creado y podemos ver el registro del workstation entrando a la siguiente url `http://localhost:8081/acme-hr-system-workstation/h2`. Al igual que la base de datos de Employee los valores son por default a excepción del siguiente

JDBC URL : `jdbc:h2:mem:acme-hr-workstation-system`

Una vez dentro de la base de datos puedes ver el registro creado del objeto workstation.

Otros de los endpoints a los que podemos acceder son `localhost:8080/acme-hr-system-employee/v1/employees` el cual es un método GET que devolvera un Json con todos los empleados y todos sus workstations asignados

```sh
[{"id":1,"firstName":"Pepe","lastName":"Perez","employeeNumber":"00001","workstations":[]},{"id":2,"firstName":"Pepe","lastName":"Perez","employeeNumber":"00002","workstations":[{"id":1,"vendor":"Mac","model":"Mackbook pro","facilitiesSerialNumber":"00101","employeeId":2}]},{"id":3,"firstName":"Pedrito","lastName":"Vazquez","employeeNumber":"00003","workstations":[{"id":2,"vendor":"Mac","model":"Mackbook pro","facilitiesSerialNumber":"00102","employeeId":3}]}]
```

Si por otra parte hacemos una petición GET hacia el siguiente endpoint `localhost:8080/acme-hr-system-employee/v1/employees/{id}` nos devolvera el employee especificado o un mensaje de error si no lo encuentra. 

Caso de éxito
```sh
{"id":3,"firstName":"Pedrito","lastName":"Vazquez","employeeNumber":"00003","workstations":[{"id":2,"vendor":"Mac","model":"Mackbook pro","facilitiesSerialNumber":"00102","employeeId":3}]}
```
Caso de error
```sh
{"timestamp":"2019-08-08T04:52:10.176+0000","status":404,"error":"Not Found","message":"Employee not found","path":"/acme-hr-system-employee/v1/employees/99"}
```
