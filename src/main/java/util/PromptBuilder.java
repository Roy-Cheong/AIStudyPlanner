package util;

import dao.StudyGoalDAO;
import model.StudyGoal;
import java.util.List;

// Builds the AI prompt based on all current goals and subjects

public class PromptBuilder {

    public static String buildPromptFromGoals() {
        StudyGoalDAO dao = new StudyGoalDAO();
        List<StudyGoal> goals = dao.getAllGoals();

        if (goals.isEmpty()) {
            return "I have no study goals yet.";
        }

        StringBuilder prompt = new StringBuilder("I'm a student with the following study goals:\n\n");
        for (StudyGoal g : goals) {
            prompt.append("Subject: ").append(g.getSubjectName()).append("\n");
            prompt.append("Goal: ").append(g.getTitle()).append("\n");
            prompt.append("Deadline: ").append(g.getDeadline()).append("\n");
            if (!g.getNotes().isEmpty()) {
                prompt.append("Notes: ").append(g.getNotes()).append("\n");
            }
            prompt.append("\n");
        }

        prompt.append("Please create a personalized weekly study plan to help me complete these goals on time. Structure it clearly by days.\n");

        return prompt.toString();
    }
}
