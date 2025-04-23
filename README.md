# 📘 AI Study Planner (JavaFX + HuggingFace)

**AI Study Planner** is a modern desktop app that helps students plan smarter, not harder. Organize your study goals and generate personalized weekly study plans powered by Hugging Face's AI. Built with JavaFX, SQLite, and pure motivation.

---

## 🧠 Features

### 🎯 Goal & Subject Management
- Add and manage **subjects**
- Link **multiple goals** to each subject
- Assign **deadlines** and write **notes**

### 🤖 AI-Powered Plan Generation
- Select a goal and click "Generate"
- Uses Hugging Face Inference API for study plan generation
- Produces a **14-day structured plan** with clear formatting
- Supports prompt preview and result copy to clipboard

### 💡 Streamlined UX
- Modern, responsive JavaFX layout
- Global **dark/light mode toggle** with custom themes
- Live **deadline countdown bar**
- **Saved plan history** per goal with click-to-load
- **Smart formatting** with emojis, icons, and summaries

### ✅ Under the Hood
- Java 17 + JavaFX SDK 17.0.15
- SQLite for local data persistence
- Hugging Face for LLM-based generation
- Modular MVC architecture (Models, DAOs, Controllers)

---

## 🖼️ Screenshots
> ⚠️ Screenshots coming soon!
- Subject & Goal Forms
- AI Output with summary and plan
- Dark Mode showcase

---

## 🛠️ Setup

### 1. Clone the Project
```bash
git clone https://github.com/Roy-Cheong/AIStudyPlanner.git
cd AIStudyPlanner
```

### 2. Requirements
- Java 17
- JavaFX SDK 17.0.15
- Internet connection for Hugging Face API

### 3. Add Your API Key
Create a file named `hf.key` in the root directory:
```bash
hf_abc123yourkey
```
The app securely reads this at runtime.

---

## 🚀 Run
- Open in IntelliJ IDEA or your favorite IDE
- Run `main.Main`

> JavaFX VM options:
```
--module-path /path/to/javafx-sdk-17.0.15/lib --add-modules javafx.controls,javafx.fxml
```

---

## ✅ Roadmap
- [x] Subject and goal management
- [x] AI plan generation with Hugging Face
- [x] Layout redesign with clean UI
- [x] Copy to clipboard + prompt preview
- [x] Plan history + click-to-load
- [x] Live deadline progress bar
- [x] Global dark/light mode toggle 🌗
- [ ] Export study plans to PDF/Markdown
- [ ] Goal editing & subject deletion

---

## 🧑‍💻 Author
Built with 💻 and ☕ by **Roy Cheong**

---

## 📜 License
Licensed under the **MIT License**.

