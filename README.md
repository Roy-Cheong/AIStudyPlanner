# 📘 AI Study Planner (JavaFX + HuggingFace)

**AI Study Planner** is a sleek desktop application that helps students organize subjects, track study goals, and generate personalized weekly study plans using Hugging Face's AI API. Built with JavaFX and SQLite, the app blends clean UI with practical AI assistance.

---

## 🧠 Features

### 🎯 Subject & Goal Management
- Add, edit, or delete **study subjects** with notes
- Link **multiple goals** to each subject
- Set **deadlines** and enter optional goal notes

### 🤖 AI-Powered Study Plan Generator
- Choose a subject and goal → click generate
- Personalized **7-day plan** using Hugging Face Inference API
- Highlights daily tasks, estimates, and summary

### 💡 Smart UI/UX
- Modern JavaFX interface with light/dark mode toggle 🌙
- Balanced layout using `VBox`, `GridPane`, and spacing
- Navigation bar with emoji-enhanced sections:
  `📅 Dashboard`, `📚 Subjects`, `🎯 Goals`, `💡 AI Planner`
- Tooltips for every control to guide new users
- Copy-to-clipboard + word/char counter + progress bar

### 💾 Local & Persistent
- Data saved locally with **SQLite**
- Study plans stored in history (recent-first)
- Auto-refresh on action or delete

---

## 🖼️ Screenshots

### 🌞 Light Mode
![Light Dashboard](assets/dashboard_light.png)
![Light Planner](assets/planner_light.png)

### 🌙 Dark Mode
![Dark Dashboard](assets/dashboard_dark.png)
![Dark Planner](assets/planner_dark.png)

---

## 🛠️ Tech Stack

| Layer         | Tech Used                                  |
|---------------|---------------------------------------------|
| Language      | Java 17                                     |
| UI Framework  | JavaFX 17.0.15                              |
| Backend API   | Hugging Face Inference API                 |
| Database      | SQLite (with DAO pattern)                  |
| Architecture  | MVC (Model-View-Controller)                |

---

## 🚀 How to Run

### 1. Clone the Repo
```bash
git clone https://github.com/Roy-Cheong/AIStudyPlanner.git
cd AIStudyPlanner
```

### 2. Requirements
- Java 17+
- JavaFX SDK 17.0.15+
- Internet (for API calls)

### 3. Hugging Face API Key
Create a file named `hf.key` in the project root:
```
hf_abc123yourkey
```

---

## ✅ Roadmap

- [x] Full CRUD for goals and subjects
- [x] Clean UI with dark mode
- [x] Personalized study plan generator
- [x] Save plan history + word counter
- [x] Copy-to-clipboard & deadline progress bar
- [ ] Export study plan to PDF or Markdown (future)
- [ ] Custom AI prompt builder (stretch goal)

---

## 👨‍💻 Author

Made with ☕ and JetBrains IDEs by **Roy Cheong**  
→ Learning through building and shipping projects 🚀

---

## 📜 License

MIT — open source, use freely, credit appreciated!
```
