 Proyecto de Lectura de Balanza y Almacenamiento en SQLServer

Este proyecto permite leer datos desde una balanza conectada a un puerto serie y almacenar los valores en una base de datos SQL Server. Se ha desarrollado en Java utilizando Visual Studio Code.

📂 Estructura del Proyecto

El espacio de trabajo contiene las siguientes carpetas:
	
•	src: Contiene el código fuente del proyecto.

•	lib: Almacena las librerías necesarias (jtds-1.3.1.jar y jSerialComm-2.11.0.jar).

•	bin: Carpeta donde se generan los archivos compilados (.class).



⸻

📌 Librerías Utilizadas

El proyecto hace uso de dos librerías esenciales:

1.	jTDS

•	Se utilizo para conectarse a SQL Server a través de JDBC.

•	Archivo: lib/jtds-1.3.1.jar

2.	jSerialComm


•	Permite la comunicación con dispositivos conectados al puerto serie (en este caso, la balanza).

•	Archivo: lib/jSerialComm-2.11.0.jar


	📌 Importante: Ambas librerías deben estar en la carpeta lib y referenciadas en .vscode/settings.json.

⸻

⚙️ Ejecución

1.	Conectar la balanza al puerto correspondiente (COM8 ).

2.	Tener en cuenta que la base de datos se encuentre conectada, la base de datos se encuentra en la carpeta de pruebas, en la seccion de tablas y el nombre de la base de datos es: dbo.registros_peso 

3.	Ejecutar el programa desde la terminal :


 	          & 'C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot\bin\java.exe' '@C:\Users\cguerra\AppData\Local\Temp\cp_629is7etd3zfelu0vi8fuhh3a.argfile' 'Balanza' 
            

 4.	Otra forma de ejecutar el programa es dando click derecho sobre el codigo fuente del proyecto que en este caso es "Balanza.java" y apretar en Run Java , esto ejecutara el programa.

⸻

Si todo está correctamente configurado, verás en la interfaz gráfica el peso en tiempo real, y los valores serán almacenados en la base de datos.

⸻

🛠 Funcionalidad del Código

El código realiza las siguientes acciones:

✅ Se conecta a la balanza a través del puerto serie usando jSerialComm.

✅ Recibe los datos en formato de texto, extrae el peso y lo muestra en la interfaz gráfica.

✅ Conecta con SQL Server utilizando jTDS y almacena los datos en la tabla registros_peso.

✅ Asegura la precisión de los valores almacenados, guardando exactamente 3 decimales.

