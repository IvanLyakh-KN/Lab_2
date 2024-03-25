import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class DungeonWarrior {

    /**
     * Represents a character in the game, either a player or an enemy.
     */
    static class Character {
        String name;
        int health;
        int attack;

        public Character(String name, int health, int attack) {
            this.name = name;
            this.health = health;
            this.attack = attack;
        }

        @Override
        public String toString() {
            return name + " [Health: " + health + ", Attack: " + attack + "]";
        }
    }

    /**
     * Represents a player character in the game.
     */
    static class Player extends Character {

        public Player(String name, int health, int attack) {
            super(name, health, attack);
        }
    }

    /**
     * Represents an enemy character in the game.
     */
    static class Enemy extends Character {
        public Enemy(String name, int health, int attack) {
            super(name, health, attack);
        }
    }

    /**
     * An ArrayList to store the enemies in the game.
     */
    static ArrayList<Enemy> enemies = new ArrayList<>();

    /**
     * Index of the current enemy in the enemies ArrayList.
     */
    static int currentEnemyIndex = -1;

    /**
     * The main method that starts the game.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the dungeon!");
        System.out.println("Choose your hero:");

        // Selecting a player character
        Player player = selectCharacter(scanner);

        System.out.println("You have chosen " + player.name + "! Get ready for an adventure!");

        // Initializing enemies
        initializeEnemies();

        // Main game loop
        while (true) {
            if (currentEnemyIndex < enemies.size() - 1) {
                currentEnemyIndex++;
                Enemy currentEnemy = enemies.get(currentEnemyIndex);
                System.out.println("\nAn enemy has appeared: " + currentEnemy);
                battle(player, currentEnemy, scanner);
                if (player.health <= 0) {
                    System.out.println("You lost the battle...");
                    break;
                }
            } else {
                System.out.println("\nYou have defeated all enemies in the dungeon!");
                break;
            }
        }
    }
    public static Player selectCharacter(Scanner scanner) {
        // Inner classes representing different character classes
        class Warrior extends Player {
            public Warrior() {
                super("Warrior", 100, 22);
            }
        }

        class Mage extends Player {
            public Mage() {
                super("Mage", 80, 25);
            }
        }

        class Rogue extends Player {
            public Rogue() {
                super("Rogue", 120, 20);
            }
        }
        int choice;
        do {
            System.out.println("1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Rogue");
            System.out.print("Choose the character number: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                return switch (choice) {
                    case 1 -> new Warrior();
                    case 2 -> new Mage();
                    case 3 -> new Rogue();
                    default -> {
                        System.out.println("Invalid choice, please try again.");
                        yield selectCharacter(scanner);
                    }
                };
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                choice = 0;
            }
        } while (choice < 1 || choice > 3);

        return null;
    }

    /**
     * Initializes the enemies in the game.
     */
    public static void initializeEnemies() {
        enemies.add(new Enemy("Goblin", 50, 8));
        enemies.add(new Enemy("Skeleton", 70, 10));
        enemies.add(new Enemy("Evil Wizard", 90, 15));
    }

    public static void battle(Player player, Enemy enemy, Scanner scanner) {
        Random random = new Random();

        while (player.health > 0 && enemy.health > 0) {
            System.out.println("\nIt's your turn to attack!");
            int playerDamage = random.nextInt(player.attack) + 1;
            enemy.health -= playerDamage;
            System.out.println("You dealt " + playerDamage + " damage to " + enemy.name + "! Enemy's health left: " + enemy.health);

            if (enemy.health <= 0) {
                System.out.println("You have defeated " + enemy.name + "!");
                break;
            }

            System.out.println("\nIt's the enemy's turn to attack!");
            int enemyDamage = random.nextInt(enemy.attack) + 1;
            player.health -= enemyDamage;
            System.out.println(enemy.name + " dealt " + enemyDamage + " damage to you! Your health left: " + player.health);

            if (player.health <= 0) {
                System.out.println("You lost the battle...");
                return;
            }

            System.out.print("\nPress Enter to continue the battle...");
            scanner.nextLine();
        }
    }
}
