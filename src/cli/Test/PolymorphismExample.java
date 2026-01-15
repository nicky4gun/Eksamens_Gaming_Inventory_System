package cli.Test;

import java.util.ArrayList;
import java.util.List;

public class PolymorphismExample {
    public abstract class Role {
        protected String name;

        public Role (String name) {
            this.name = name;
        }

        // Fælles metode
        public String getName() {
            return name;
        }

        // Abstrakt metode - hver person implementerer forskelligt
        public abstract String printTitle();
    }

    // Konkrete klasser
    public class Player extends Role {
        public Player(String name) {
            super(name);
        }

        @Override
        public String printTitle() {
            return "Player";
        }
    }

    public class Administrator extends Role {
        public Administrator(String name) {
            super(name);
        }

        @Override
        public String printTitle() {
            return "Admin";
        }
    }

    // Output-Klasse
    public class Register {
        public void getPrintedTitle() {
            List<Role> roles = new ArrayList<>();

            roles.add(new Player("Player 1"));
            roles.add(new Administrator("Admin"));

            for (Role role : roles) {
                System.out.println(role.printTitle());
            }
        }

        // === Output ===
        // Player 1
        // Admin
    }
}
