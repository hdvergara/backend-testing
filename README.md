# API Testing project

**Requisitos:**
* Java 18
* Maven

Este proyecto fue diseñado en el IDE IntelliJ utilizand Java en su verison 18, se utiliza Maven como gestor de 
dependencias y las pruebas se crearon con RestAssured y se utiliza JUnit para su ejecución.

**Instrucciones para la Ejecucion de las pruebas**

Las pruebas se pueden ejecutar de 2 formas:

* Ejecutando la clases **ApiTest** que se encuentran ubicadas en el siguiente path:
  **src/test/java/backend/test**, dentro se encuentra la clase Test se ejecuta con JUnit.
* Por medio de la terminal ejecutando el comando mvn clean test el cual ejecuta todos los test del proyecto.

Una ves ejecutadas las pruebas, se creará un carpeta llamada **reports** dentro del proyecto, la cual genera un archivo
.html que contiene el reporte de las pruebas, que al abrirlo en el IDE y seleccionando un navegador de preferencia se
puede visualizar el informe de las pruebas ejecutadas.