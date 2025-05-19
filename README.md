# SISCCEL - Sistema de Carrito de Compras En Línea
## SISTEMA: TOKYO RADIANCE

** Descripción del Sistema **
Plataforma web para la venta de productos asiáticos, con gestión de usuarios, carrito de compras, buzón de contacto y panel administrativo.

## Módulos principales:

• Usuarios y seguridad: Registro, autenticación JWT y control de accesos.

• Productos: Alta, actualización y consulta de productos.

• Carrito de compras: Gestión de artículos, cálculo de montos y simulación de compra.

• Pedidos: Historial de pedidos y seguimiento de estatus.

• Inventario: Reportes en PDF y actualización de existencias.

• Contacto: Formulario de quejas/sugerencias con filtros para administradores.

## 🛠 Requerimietnos Funcionales

• Registro: Formulario público para clientes y registro privado para administradores.
• Autenticación: Inicio de sesión con JWT y cookies.
• Gestión de productos: Lectura, actualización y alta de productos con categorías e imágenes.
• Carrito de compras: Añadir/eliminar productos, calcular totales y generar PDF de compra.
• Gestión de Pedidos: Actualización de estatus de los pedidos realizados por el cliente
• Inventario:Actualizar existencias y generar reportes en PDF.
• Gestión de Contacto: Los clientes pueden enviar quejas/sugerencias mediante un formulario, con registro previo o sin registro.El administrador visualiza las solicitudes desde un buzón exclusivo que las detalla. El administrador puede realizar un filtrado de las solicitudes por medio del tipo: Queja o
Sugerencia

## 🛠 Requerimientos No Funcionales

La interfaz utilizará un diseño responsivo que podrá ejecutarse en distintas pantallas.
• Se utilizará una base de datos en MariaDB para el almacenamiento de los productos, usuarios y los
productos seleccionados para el carrito.


### Seguridad:

• USO DE TOKEN JWT:
1. Generación de token:
- JWTTokenProvider genera el token usando el algoritmo HS512 y una clave secreta.
- El token incluye el correo del usuario y perfil

2. Validación del Token:
- JWTAuthenticationFilter extrae el token de las cookies, verifica su firma y validez, y carga los
detalles del usuario para establecer la autenticación en el contexto de seguridad.

3. Autenticación:
- El usuario envía credenciales (email y contraseña) mediante el formulario de inicio de
sesión.
- UsuarioController realiza la validación de las credenciales y permite el acceso a la pantalla
correspondiente a su perfil (administrador o cliente)
- JwtAuthenticationSuccessHandler genera el token JWT y lo almacena en una cookie.
- En solicitudes posteriores, el filtro JWT valida el token y autoriza el acceso según el perfil.

• AUTORIZACIÓN:
1. Los roles se asignan mediante UserDetailsImpl, que transforma el perfil del usuario en una
autoridad de Spring Security.
2. En SecurityConfiguration se establecen los accesos a los que cada perfil tiene la autoridad para
ingresar mediante el uso de .hasAuthority("Administrador") o .permitAll().

• CIFRADO DE CONTRASEÑAS:
Las contraseñas se cifran con BCryptPasswordEncoder antes de almacenarse en la base de datos.

• GESTIÓN DE SESIONES:
1. Política de Sesiones:
Se configura como SessionCreationPolicy.IF_REQUIRED, permitiendo sesiones solo cuando el
usuario esta autenticado
2. Tiempo de sesión: Se establece un tiempo se sesión, una vez que expira, se cierra la sesión y
no permite al usuario realizar acciones.

• MANEJO DE ERRORES:
- Validaciones con @Valid y BindingResult en controladores
- Mensajes de error: Indican al usuario de manera clara cuando se presentan errores.


## Configuración Inicial

Para instalar y ejecutar correctamente TOKYO RADIANCE correctamente, se deben tener las siguientes consideraciones:

###Base de datos:

***Configuración de Base de Datos (MariaDB)***

• Ejecutar el script schema.sql proporcionado en el path: src/main/resources/schema.sql
• Datos de prueba inciales: Ejecutar data.sql en el path: src/main/resources/data.sql

• IMAGENES
- Se debe utilizar la carpeta de imagenes.zip que se encuentra en el path: src/main/resources/static/imagenes.zip
    Dicha carpeta deberá estar en C:/imagenes/ ya que ahí se indica en el application.properties

    ----------------------------------------------------
    application.properties
    #ruta de archivos
    ejemplo.imagen.ruta=C:/imagenes/
    spring.resources.static-locations=file:///C:/imagenes/
    -----------------------------------------------------

- Para añadir productos de prueba se puede utilizar la carpeta de imagenes Test.zip que se encuentra en el path: src/main/resources/static/Imagenes Test.zip


###Configurar las variables en application.properties:
Para que el sistema se conecte correctamente a la base de datos, es necesario configurar los siguientes parámetros en el archivo `application.properties`. Reemplazar los valores de `spring.datasource.username` y `spring.datasource.password` con el usuario y la contraseña correspondientes a tu instancia de MariaDB.

EJEMPLO:
spring.datasource.url=jdbc:mariadb://localhost:3306/SISCCEL
spring.datasource.username= tu_usuario
spring.datasource.password= tu_contraseña
Variables de entorno:

Clave secreta JWT: Configurar en JWT_SECRET (ej: miClaveSecreta123).


### Especificaciones del Proyecto pom.xml
Se utiliza Maven para la gestión de dependencias y compilación. Se pueden encontrar las librerías y plugins utilizados en el archivo 'pom.xml'

## Acceder al sistema
- Cliente: http://localhost:8080/tokyo

📋 Autor:
Fecha	     Responsable
18/05/2025	 Samantha Gutiérrez
