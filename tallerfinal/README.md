# Taller Final

Este proyecto es una aplicación de gestión de pagos y transacciones, implementada utilizando Spring Boot y Reactive Programming con Project Reactor. La aplicación permite realizar retiros, validar pagos, y gestionar usuarios.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Uso de la API](#uso-de-la-api)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Contribución](#contribución)

## Características

- Validación de pagos.
- Gestión de retiros de efectivo.
- Consulta de transacciones por usuario.
- Gestión de usuarios y su saldo.

## Requisitos

- Java 11 o superior.
- Maven.
- Spring Boot.

## Configuración

1. **Clonar el repositorio:**

   ```bash
   git clone <url-del-repositorio>
   cd tallerfinal
   ```
2. **Construir el proyecto:**

```bash
gradle clean build
```
3. **Ejecutar la aplicación:**

```bash
gradle bootRun
```
La aplicación se ejecutará en http://localhost:8080.

4. **Uso de la API:**

4.1. Validar Pago
   POST /payments/validate

    Request Body:

    json
    {
    "userId": "usuario123",
    "amount": 100.0
    }
Response:
    
    json
    true
4.2. Crear Retiros

   POST /cashouts
    
    Request Body:
    
    json
    {
    "userId": "usuario123",
    "amount": 100.0
    }

Response:
    
    json
    {
    "id": "retiro123",
    "userId": "usuario123",
    "amount": 100.0
    }
4.3. Obtener Retiros por Usuario

   GET /cashouts/user/{userId}

Response:

    json
    [
    {
    "id": "retiro123",
    "userId": "usuario123",
    "amount": 100.0
    },
    ...
    ]
4.4. Obtener Usuario por ID

   GET /users/{id}

Response:

    json
    {
    "id": "usuario123",
    "balance": 500.0
    }
4.5. Actualizar Saldo del Usuario

   PUT /users/{id}/balance

Request Body:

    json
    {
    "amount": 200.0
    }
Response:

    json
    {
    "id": "usuario123",
    "balance": 700.0
    }

5. **Estructura del Proyecto:**


```
tallerfinal
│
├── controller
│   ├── auxiliarycontroller
│   │   ├── PaymentController.java
│   │   ├── TransactionController.java
│   ├── CashoutController.java
│   └── UserController.java
│
├── dto
│   └── CashoutDto.java
│
├── model
│   ├── Cashout.java
│   ├── Transaction.java
│   └── User.java
│
├── repository
│   ├── CashoutRepository.java
│   ├── TransactionRepository.java
│   └── UserRepository.java
│
└── service
├── CashoutServiceImpl.java
├── PaymentServiceImpl.java
├── TransactionServiceImpl.java
└── UserServiceImpl.java
```
Contribución
Las contribuciones son bienvenidas. Por favor, abre un pull request para cualquier mejora o corrección.



### Notas Adicionales
- Asegúrate de sustituir `<url-del-repositorio>` por la URL real de tu repositorio.
- Puedes ajustar los ejemplos de la API según sea necesario para reflejar con precisión la funcionalidad de tu aplicación.
- Considera agregar secciones adicionales, como "Licencia", "Autor", o "Contacto", según sea relevante para tu proyecto.