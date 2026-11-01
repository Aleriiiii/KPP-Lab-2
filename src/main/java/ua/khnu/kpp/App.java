package ua.khnu.kpp;

import java.time.LocalDate;

public final class App {
    private App() {}

    public static void main(String[] args) {
        System.out.println("=== TaskFlow Demo (Variant 6) ===");
        
        User user = new User(1L, "Олександр");
        Project project = new Project(10L, "Польове опитування", user);
        LocalDate created = LocalDate.of(2026, 9, 10);
        LocalDate dueDate = LocalDate.of(2026, 9, 12);
        
        Task task = new Task(100L, "Збір анкет", project, created, dueDate, new VariantPolicy());
        
        System.out.println("Initial Status: " + task.status());
        
        task.assign(user);
        task.start();
        System.out.println("Status after start: " + task.status());

        // Спроба завершити з невалідними доказами (очікуємо відмову)
        try {
            task.complete(LocalDate.of(2026, 9, 11), 5, 0, false);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected rejection: " + e.getMessage());
        }
        
        System.out.println("Status after failed completion: " + task.status());

        // Успішне завершення
        task.complete(LocalDate.of(2026, 9, 11), 8, 3, true);
        System.out.println("Final Status after valid completion: " + task.status());
    }
}