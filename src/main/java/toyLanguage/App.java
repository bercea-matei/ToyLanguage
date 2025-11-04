package toyLanguage;

import toyLanguage.controller.Controller;
import toyLanguage.controller.MyController;
import toyLanguage.view.TUI;
import toyLanguage.repository.Repository;
import toyLanguage.repository.MyRepo;

public class App {
    public static void main(String[] args) {
        Repository repo = new MyRepo();
        Controller ctrl = new MyController(repo);
        TUI tui = new TUI(ctrl);

        tui.start();
    }
}
