---
name: Bug report
about: Report a bug to help us improve
title: ''
labels: bug
assignees: ''

---

**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Sample code snippet to reproduce the problem:
```kotlin
val ollamaClient = OllamaClient(CIO)

val request = chatRequest {
    model("tinyllama")
    messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
}

val response = ollamaClient.chat(request)

println(response)
```

**Expected behavior**
A clear and concise description of what you expected to happen.

**Environment (please complete the following information):**
- Kotlin version: [eg. 2.1.0]
- JDK: [eg. OpenJDK 17]

**Additional context**
Add any other context about the problem here.
