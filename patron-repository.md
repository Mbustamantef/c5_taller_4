# 1. Introducción

Definición:
El patrón Repository abstrae la lógica de acceso a datos, permitiendo trabajar con objetos sin preocuparse por la base de datos.

Problema que resuelve:
Evita que la capa de negocio dependa directamente del ORM o del SQL, promoviendo separación de responsabilidades.

Flujo conceptual:
Controller → Servicio → Repositorio → Entidad

 # 2. Ventajas del Patrón Repository

Separación de capas: Aísla la lógica de negocio de la persistencia.

Legibilidad: Los métodos del repositorio son más expresivos.

Reutilización: Centraliza consultas comunes en una sola capa.

Facilita pruebas: Permite usar mocks sin tocar la base real.

Menos código: Panache simplifica CRUD con métodos predefinidos.

# 3. Desventajas del Patrón Repository

Complejidad extra: En proyectos pequeños puede ser innecesario.

Abstracción limitada: Para consultas complejas puede requerir SQL directo.

Doble mantenimiento: Cambios en entidades deben reflejarse en el repositorio.

 # 4. Aplicación en mi Proyecto

Proyecto: Gestión Empresarial
Entidad principal:GenericRepository 

Ejemplo de repositorio:

``` ```package core.generic;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public abstract class GenericRepository <I , ID > implements PanacheRepositoryBase<I , ID> {

}
