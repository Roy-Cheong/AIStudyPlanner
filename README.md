# 📘 AI Study Planner (JavaFX + HuggingFace)

**AI Study Planner** is a sleek desktop application that helps students organize their study goals and generate personalized weekly study plans using Hugging Face's AI API. Built with JavaFX and SQLite, this project combines intuitive UI with practical AI assistance.

---

## 🧠 Features

### 🎯 Goal & Subject Management
- Add, view, and manage your **study subjects**
- Link **multiple goals** to each subject
- Set **deadlines** and include **notes** for each goal

### 🤖 AI-Powered Study Plan Generator
- Choose a subject and its associated goal
- One-click to generate a structured **7-day plan** via Hugging Face AI
- Clear daily breakdown to help you stay on track
- Displays prompt and result directly in the app

### 💡 Smart UI Enhancements
- Clean and modern JavaFX interface
- Responsive layout with balanced spacing and insets
- Tips section on dashboard to guide users
- Emoji-enhanced navigation bar (📅 📚 🎯 💡 ⚙️)
- Styled output area with title label

### ✅ Under the Hood
- Java 17 + JavaFX SDK 17.0.15
- SQLite for persistent local storage
- Hugging Face Inference API for text generation
- MVC structure (Controllers, DAOs, Models)

---

## 🖼️ Screenshots
*Coming soon – clean dashboards, goal inputs, and AI output previews!*

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
- Internet connection (for Hugging Face API)

### 3. Place Your API Key
Create a file named `hf.key` in the project root with your Hugging Face API key:
```
hf_abc123yourkey
```
> The app loads it securely at runtime.

---

## 🚀 Run
Compile and launch the project in IntelliJ or using your preferred JavaFX run configuration.

---

## ✅ To-Do / Roadmap
- [x] Subject and goal linking
- [x] AI study plan output
- [x] UI polish and layout rework
- [ ] Copy to clipboard button for AI result 
- [ ] Add option to edit or delete goals/subjects
- [ ] Export study plans to PDF/Markdown

---

## 🧑‍💻 Author
Made with 💻 by Roy Cheong – powered by curiosity and too much coffee ☕

---

## 📜 License
Open source under the MIT License.

