# JPASpice
# Author: Hao Pan
https://haolgpan.github.io/JPASpice/

Prototip de maneig d'una base de dades

Permet fer les principals operacions de CRUD (inserir, crear, modificar i eliminar) sobre una base de dades en postgres i amb JPA Hibernate.
En aquest projecte la base de dades es troba en una màquina Vagrant i utilitza POSTGRESQL com a sistema de gestió.

En cas de realitzar un CRUD en camps que no concorden, retorna al menu principal sense haver realitzat canvis en la base de dades.
Permet a més a més borrar totes les taules de la base de dades i tornar a crear-les de nou amb informació predeterminada o no.
Totes les taules esta en format annotation i el programa funciona amb entity managers.

Agraeixo tota la informació provinguda de Rubén(https://github.com/rusben).

