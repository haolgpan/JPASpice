package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private int option;

    public Menu() {
        super();
    }

    /**
     * Menu visual per el menu principal, aquí permet que l'usuari esculli de les diferents opcions que permet realitzar
     * a la base de dades.
     * @return
     */

    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Carrega dades. ");
            System.out.println("2. Elimna dades. ");
            System.out.println("3. Creació de taules.");
            System.out.println("4. Eliminar les taules.");
            System.out.println("5. Consultar espècia per id.");
            System.out.println("6. Esborrar espècia per id.");
            System.out.println("7. Modificar el nom d'una espècia per id.");
            System.out.println("8. Insertar nova espècia.");
            System.out.println("9. Mostra totes les espècias.");
            System.out.println("10. Mostra totes els països.");
            System.out.println("11. Mostra totes els productes.");
            System.out.println("12. Modificar el nom d'un paìs per id.");
            System.out.println("13. Modificar el format d'un producte per id.");
            System.out.println("14. Eliminar per ID un país.");
            System.out.println("15. Eliminar per ID un format.");
            System.out.println("0. Sortir. ");

            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option < 0 && option > 15);

        return option;
    }






}