import java.util.ResourceBundle;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Room wald;
    private Room friedhof;
    private Room kirche;
    private Room schloss;
    private Room taverne;
    boolean objectTaken = false;

    public Game() {
        createRooms();
    }

    public class GameTexts {
        private static final ResourceBundle texts = ResourceBundle.getBundle("dialog");

        public static String get(String key) {
            return texts.getString(key);
        }
    }

    private void createRooms() {
        schloss = new Room(GameTexts.get("room.schloss.description"));
        taverne = new Room(GameTexts.get("room.taverne.description"));
        kirche = new Room(GameTexts.get("room.kirche.description"));
        friedhof = new Room(GameTexts.get("room.friedhof.description"));
        wald = new Room(GameTexts.get("room.wald.description"));

        // Ausgänge setzen (n, e, s, w)
        schloss.setExits(null, null, taverne, wald); // Schloss hat Taverne im Süden und Wald im Westen
        taverne.setExits(schloss, null, null, kirche); // Taverne hat Schloss im Norden und Kirche im Westen
        kirche.setExits(wald, taverne, null, friedhof); // Kirche hat Wald im Norden, Taverne im Osten und Friedhof im
                                                        // Westen
        friedhof.setExits(wald, kirche, null, null); // Friedhof hat Wald im Norden und Kirche im Osten
        wald.setExits(null, schloss, kirche, friedhof); // Wald hat Schloss im Osten, Kirche im Süden und Friedhof im
                                                        // Westen

        currentRoom = schloss;
    }

    private void goRoom(String direction, Scanner scanner) {
        Room nextRoom = null;

        switch (direction) {
            case "north":
                nextRoom = currentRoom.getNorth();
                break;
            case "east":
                nextRoom = currentRoom.getEast();
                break;
            case "south":
                nextRoom = currentRoom.getSouth();
                break;
            case "west":
                nextRoom = currentRoom.getWest();
                break;
            default:
                System.out.println(GameTexts.get("room.direction.unknown"));
                return;
        }

        if (nextRoom == null) {
            System.out.println(GameTexts.get("game.direction.invalid"));
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getDescription());

            if (currentRoom == wald) {
                System.out.println(GameTexts.get("event.wald.gameover"));
                System.exit(0);
            } else if (currentRoom == friedhof && objectTaken == false) {
                System.out.println(GameTexts.get("event.friedhof.fund"));
                System.out.println(GameTexts.get("event.friedhof.frage"));
                System.out.print("> ");
                String antwort = scanner.nextLine().trim().toLowerCase();

                if (antwort.equals("ja")) {
                    System.out.println(GameTexts.get("event.friedhof.mitgenommen"));
                    objectTaken = true;
                } else {
                    System.out.println(GameTexts.get("event.friedhof.gelassen"));
                }
            } else if (currentRoom == kirche && objectTaken == true) {
                System.out.println(GameTexts.get("event.kirche.gewonnen"));
                System.exit(0);
            }
        }
    }

    private void showMap() {
        System.out.println("╔═════════════════════════════════╗     ╔═════════════════════════╗");
        System.out.println("║           _  ~~~  _             ║     ║  [][][]  /\"\"\\   [][][]  ║");
        System.out.println("║          /~~     ~~\\            ║     ║    |::| /____\\  |::|    ║");

        if (currentRoom == wald) {
            System.out.println("║        /~~         ~~\\     ║─────║   |[]|_|::::|_|[]|    ║");
            System.out.println("║           {           }           ║     ║   |::::::__::::::|    ║");
            System.out.println("║         \\  _-     -_  /     ║─────║    |:::::/| |\\:::::|    ║");
            System.out.println("║             ~  \\\\ //  ~    ║     ║    |:#:::|| ||::#::|    ║");
            System.out.println("║       _- -   | | _- _         ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║          _ -  | |   -_          ║     ║         SCHLOSS         ║");
            System.out.println("║               // \\\\                      ║     ║                         ║");

        } else if (currentRoom == schloss) {
            System.out.println("║         {           }           ║─────║    |:::::/| |\\:::::|    ║");
            System.out.println("║          \\ _-   -_ /            ║     ║    |:#:::|| ||::#::|    ║");
            System.out.println("║          ~  \\\\ //  ~            ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║        _- -  | | _- _           ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║         _ -  | |   -_           ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║             // \\\\               ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║             WALD                ║     ║      SCHLOSS    [X]     ║");

        } else {
            System.out.println("║         {           }           ║─────║    |:::::/| |\\:::::|    ║");
            System.out.println("║          \\ _-   -_ /            ║     ║    |:#:::|| ||::#::|    ║");
            System.out.println("║          ~  \\\\ //  ~            ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║        _- -  | | _- _           ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║         _ -  | |   -_           ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║             // \\\\               ║     ║  @@@@@@@@@@@@@@@@@@@@@  ║");
            System.out.println("║              WALD               ║     ║         SCHLOSS         ║");

        }

        System.out.println("╚══════╦═══════════════════╦══════╝     ╚══════════════════╦══════╝");
        System.out.println("       │                   │                               │");
        System.out.println("       │                   │                               │");
        System.out.println("╔══════╩══════╗     ╔══════╩═══════════════╗     ╔═════════╩══════╗");
        System.out.println("║             ║     ║              +       ║     ║                ║");

        if (currentRoom == friedhof) {
            System.out.println("║             ║     ║            /_\\       ║     ║                ║");
            System.out.println("║             ║     ║  ,%%%______|O|       ║     ║                ║");
            System.out.println("║             ║     ║  %%%/_________\\      ║     ║                ║");
            System.out.println("║             ║     ║  `%%| /\\[][][]|%     ║     ║                ║");
            System.out.println("║             ║     ║ ___||_||______|%&,__ ║     ║                ║");
            System.out.println("║FRIEDHOF [X] ║     ║        KIRCHE        ║     ║   TAVERNE      ║");

        } else if (currentRoom == kirche) {
            System.out.println("║             ║     ║            /_\\       ║     ║                ║");
            System.out.println("║             ║     ║  ,%%%______|O|       ║     ║                ║");
            System.out.println("║             ║     ║  %%%/_________\\      ║     ║                ║");
            System.out.println("║             ║     ║  `%%| /\\[][][]|%     ║     ║                ║");
            System.out.println("║             ║     ║ ___||_||______|%&,__ ║     ║                ║");
            System.out.println("║FRIEDHOF     ║     ║      KIRCHE [X]      ║     ║   TAVERNE      ║");

        } else if (currentRoom == taverne) {
            System.out.println("║             ║     ║            /_\\       ║     ║                ║");
            System.out.println("║             ║     ║  ,%%%______|O|       ║     ║                ║");
            System.out.println("║             ║     ║  %%%/_________\\      ║     ║                ║");
            System.out.println("║             ║     ║  `%%| /\\[][][]|%     ║     ║                ║");
            System.out.println("║             ║     ║ ___||_||______|%&,__ ║     ║                ║");
            System.out.println("║FRIEDHOF     ║     ║        KIRCHE        ║     ║   TAVERNE [X]  ║");

        } else {
            System.out.println("║             ║     ║            /_\\       ║     ║                ║");
            System.out.println("║             ║     ║  ,%%%______|O|       ║     ║                ║");
            System.out.println("║             ║     ║  %%%/_________\\      ║     ║                ║");
            System.out.println("║             ║     ║  `%%| /\\[][][]|%     ║     ║                ║");
            System.out.println("║             ║     ║ ___||_||______|%&,__ ║     ║                ║");
            System.out.println("║FRIEDHOF     ║     ║        KIRCHE        ║     ║   TAVERNE      ║");
        }

        System.out.println("╚═════════════╝     ╚══════════════════════╝     ╚════════════════╝");
        System.out.println("\nDein aktueller Standort ist mit [X] markiert.");
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        System.out.println(GameTexts.get("game.welcome"));
        System.out.println(currentRoom.getDescription());

        while (playing) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit") || input.equals("exit")) {
                playing = false;
            } else if (input.equals("look")) {
                System.out.println(currentRoom.getDescription());
            } else if (input.startsWith("go ")) {
                String direction = input.substring(3);
                goRoom(direction, scanner);
            } else if (input.equals("help")) {
                System.out.println(GameTexts.get("game.command.help"));
            } else if (input.equals("map")) {
                showMap();
            } else {
                System.out.println(GameTexts.get("game.command.unknown"));
            }
        }

        System.out.println(GameTexts.get("game.command.quit"));
        scanner.close();
    }
}

//
// [][][] /""\ [][][]
// |::| /____\ |::|
// |[]|_|::::|_|[]|
// |::::::__::::::|
// |:::::/||\:::::|
// |:#:::||||::#::|
// @@@@@@@@@@@@@@@@@@@
// @@@@@@@@@@@@@@@@@@@@@
//

// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⡠⠔⠒.
// ⠀⢀⠔⠚⠙⠒⠒⠒⠤⠀⠀ ⠀⠀⢀⣠⣴⣦⠀⠈⠘⣦
// ⣸⠁⠀⠀⠀⠀⠀⠀⢰⠃:⣀ ⣀⡠⣞⣉⡀⡜⡟⣷⢟⠟
// ⣗⠀⠀⢀⣀⣀⣀⣀⣀⣓⡞ ⢽⡚⣑⣛⡇⢸⣷⠓⢻⣟⡿
// ⠈⠒⠊⠻⣷⣿⣚⡽⠃⠉⠀⠀ ⠙⠿⣌⠳⣼⡇⠀⣸⣟⡑
// ⠀⡀⠀⠀.⣿⠉⠀⠀⠀⠀⠀⠀ ⠀⠈⢧⣸⡇⢐⡟⠀⠙
// ⣿.⠀⠀⠀⠀⠀⠀⠀ ⠀⠀⠀⠙⣟⠋
// ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀ ⠀ ⣿⠀
// ⠻⢷⠾⠦⠤⠬⣅⣹⣿⣖⣶⣲⣈⡥⠤⠶⡖⠛::⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
