package util;

import dao.StudyGoalDAO;
import model.StudyGoal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PromptBuilder {

    public static String buildPromptFromGoal(StudyGoal g) {
        StringBuilder prompt = new StringBuilder("I'm a student with the following study goal:\n\n");

        prompt.append("Subject: ").append(g.getSubjectName()).append("\n");
        prompt.append("Goal: ").append(g.getTitle()).append("\n");
        prompt.append("Deadline: ").append(g.getDeadline()).append("\n");

        if (!g.getNotes().isEmpty()) {
            prompt.append("Notes: ").append(g.getNotes()).append("\n");
        }

        // Add today's date
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        prompt.append("\nToday is ").append(today).append(".\n");
        prompt.append("Please create a personalized weekly study plan to help me complete this goal on time. Structure it clearly by days.\n");

        return prompt.toString();
    }
}
