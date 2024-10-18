import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║ ▆▃▅█▇█▅▃▂  MUSICALIZE  ▂▃▅█▇█▅▃▆ ║");
        System.out.println("║                                  ║");
        System.out.println("╠═ [#] login                       ║");
        System.out.println("║                                  ║");
        System.out.println("╠═ [#] register                    ║");
        System.out.println("║                                  ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();
        System.out.println("TYPE A COMMAND TO CONTINUE");
        System.out.print("[#] ");
        String command = input.nextLine().toLowerCase();
        System.out.println();

        while (!command.equals("login") && !command.equals("register")) {
            System.out.println("[!] INVALID COMMAND");
            System.out.print("[#] ");
            command = input.nextLine();
        }
        if (command.equals("login")) {
            User.login();
        } else {
            User.register();
        }
    }
}
