# Kotlin Crash Course

Learning resources from the Kotlin Crash Course book.

- [Book website](https://bpbonline.com/products/kotlin-crash-course)
- [GitHub repo](https://github.com/bpbpublications/Kotlin-Crash-Course)

Code examples will be added to chapter branches.

- Chapter 03: Setting up the Development Environment
  - [ComplimentGenerator](./ComplimentGenerator/)
- Chapter 04: Fundamental Building Blocks of Kotlin
  - [F1RaceSimulatorWithoutCoroutine](./F1RaceSimulatorWithoutCoroutine/)
- Chapter 05: Object-oriented Programming
  - [DentalClinic](./DentalClinic/)
- Chapter 06: Kotlin Collection Framework
  - [ProductCatalog](./ProductCatalog/)
- Chapter 07: Scope Functions
  - [ProductCatalog](./ProductCatalog/)
- Chapter 08: Functional Programming
  - [KotlinVoyager](./KotlinVoyager/)
- Chapter 09: Exploring Delegation Design Pattern
  - [KotlinBrew](./KotlinBrew/)
- Chapter 10: Concurrency and Parallelism
  - [F1RaceSimulatorWithCoroutine](./F1RaceSimulatorWithCoroutine/)
- Chapter 11: Unit Testing in Kotlin
- Chapter 12: Building a Simple REST API
- Chapter 13: Building Event-Driven Cloud Native Serverless Application

## Notebooks

```bash
python -m venv venv
source venv/bin/activate
pip install -r notebooks/requirements.txt
## open jupyter lab on localhost:8888
JUPYTER_ENABLE_LAB=yes jupyter lab --ServerApp.token='' --ServerApp.password=''
```

### Editor Settings

- `Settings > Theme > JLDracula`
- `Settings > Settings Editor > Code Completion > Enable autocompletion`
- `Settings > Settings Editor > Jupyter Ruff > Format on Save`
