// PROYECTO INTEGRADOR PROGRAMACIÓN II
// INTEGRANTES: LUCIANO CANOVAS - LUCIANO SUAREZ
// REPRODUCTOR DE MUSICA

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("║ ♫ MUSICALIZE ♫");
        System.out.println("╠═ [~] LOGIN");
        System.out.println("╠═ [~] REGISTER");
        System.out.println("╠═ [~] EXIT");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        while (!command.equals("login") && !command.equals("register") && !command.equals("exit")) {
            System.out.println("[!] INVALID COMMAND");
            System.out.print("[#] ⇒ ");
            command = input.nextLine();
        }
        switch (command) {
            case "login":
                User.login();
                break;
            case "register":
                User.register();
                break;
            case "exit":
                break;
        }
    }
}
