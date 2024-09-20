## PROYECTO PARCIAL BONO

## Descripción

Este proyecto consiste en una calculadora reflexiva que funciona a través de una arquitectura cliente-servidor. La aplicación está dividida en dos componentes principales:

ServerBackEnd: Un servidor Java que recibe solicitudes HTTP, ejecuta operaciones matemáticas utilizando reflexión y devuelve los resultados en formato JSON.

ServerCalFacade: Un servidor HTTP que actúa como fachada para el cliente. Recibe solicitudes del cliente, las reenvía al CalculatorReflexServer para su procesamiento, y devuelve la respuesta al cliente. También proporciona una interfaz web simple para interactuar con la calculadora.
Inicio



## Requisitos

Para comenzar con el proyecto, necesitas tener Java instalado en tu sistema. Asegúrate de tener JDK 11 o superior.

Es necesario tener instalado  "**Maven**" y "**Java**", preferiblemente en sus ultimas versiones.

#### * Maven
  ```
  Descarga Maven at http://maven.apache.org/download.html 

  Descarga the instructions at http://maven.apache.org/download.html#Installation
  ```
#### * Java

  ```
  Descarga Java at https://www.java.com/es/download/ie_manual.jsp
  ```


## Instalar y Empezar

1. Clona el repositorio y navega al directorio del proyecto:
    ```sh

    git clone https://github.com/Richi025/proyectoArepParcialBono.git 

    cd proyectoArepParcialBono
    ```

2. Compila el proyecto:
    ```sh
    mvn package
    ```

3. Ejecuta la aplicación:

    En una terminar correr el siguiente servidor:

    ```sh
    java -XX:+ShowCodeDetailsInExceptionMessages -cp target/classes edu.escuelaing.arep.ServerBackEnd
    ```
    Luego abrir otra terminal y correr el servidor de fachada:

    ```sh
    java -XX:+ShowCodeDetailsInExceptionMessages -cp target/classes edu.escuelaing.arep.ServerCalFacade
    ```
    


4. El proyecto corre por el siguiente enlace
    
     http://localhost:35000


    ![alt text](images/imagee.png)


## Arquitectura

Es un proyecto compuesto por dos servidos uno de fachada y otro que hace las veces de back end. El servidor fachada contiene el HTML para visualizar el cliente.

![alt text](<images/Untitled diagram-2024-09-20-033353.png>)

## Construido con:

* [Maven](https://maven.apache.org/) - Dependency management
* [java](https://www.java.com/es/) - Programming language

## Versionado

We use [Git](https://github.com/) for version control. For available versions, see the tags in this repository.

## Autor

* **Jose Ricardo Vasquez Vega** - [Richi025](https://github.com/Richi025)

## Fecha

Septiembre 19, 2024