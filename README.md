### Toy Language Interpreter

This project is a Java-based interpreter for a simple imperative "Toy Language," developed as a university assignment for an Advanced Programming Methods course. The interpreter is built using the Model-View-Controller (MVC) architectural pattern and demonstrates core concepts of language design, data structures, and concurrent execution.
### ✨ Core Features
- MVC Architecture: Clear separation of concerns between the data model (language components), the view (text menu), and the controller (execution logic).
- Custom Abstract Data Types: Generic, custom-built ADTs for the Execution Stack (MyIStack), Symbol Table (MyIDictionary), and Output List (MyIList) that wrap Java's standard collections.
- Core Language Constructs: Support for variable declarations, assignments, conditional If statements, and Print statements.
- Expressions: Evaluation of arithmetic expressions (+, -, \*, /) and logical expressions (and, or).
- Type System: A basic static type system with int and bool types.
- Exception Handling: A custom exception hierarchy to manage runtime errors like division by zero, type mismatches, and undefined variables.
### Complexity & Safety 
*   **Multi-Threaded Execution Engine:** Orchestrates concurrent program states using a `FixedThreadPool`. Features cooperative multitasking where threads "yield" the CPU when blocked by synchronization primitives.
*   **Manual Heap & Garbage Collection:** Implements a shared global heap with a **Conservative Garbage Collector**. The GC performs reachability analysis across all active thread stacks to prevent memory leaks.
*   **Synchronization Suite:** A complete library of concurrency primitives:
    *   **Latches:** For one-way event synchronization.
    *   **Barriers:** For multi-thread rendezvous points.
    *   **Semaphores:** For resource throttling.
    *   **Locks:** For strict mutual exclusion (owner-based).
*   **Scoped Procedures:** Supports modular programming with a **Call Stack**. Each procedure call creates a new local scope (Symbol Table), protecting global state from unintended side effects.
*   **JavaFX Reactive Dashboard:** A professional GUI that visualizes the VM's internal state (Heap, FileTable, Call Stacks) in real-time without blocking the execution engine.


### 🏛️ Shared Memory Model

*   **Model (The Virtual Machine):** The `PrgState` represents an execution unit. While each thread has a private **Execution Stack** and **Symbol Table Stack**, they share global references to the **Heap**, **FileTable**, and **Synchronization Tables**.
*   **Concurrency Model:** All shared ADTs (Tables) are thread-safe, utilizing Java's `synchronized` monitors to ensure atomic updates and prevent race conditions.
*   **Data Integrity:** Implements a **Deep Snapshot** pattern when transferring data to the UI thread, ensuring the GUI never crashes due to background memory modifications.

### 📋 Implemented Language Constructs
#### Statements (based on Stmt interface)

| Statement              | Syntax Example                                                     | Description                                                                                                                                                                                                                                                                 |
| ---------------------- | ------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Compound               | { Stmt1; Stmt2 }                                                   | Executes statements sequentially.                                                                                                                                                                                                                                           |
| Var Declaration<br>    | Type var_name;                                                     | Declares a variable of a given type.                                                                                                                                                                                                                                        |
| Assignment<br>         | var_name = expression;                                             | Assigns the result of an expression to a variable.                                                                                                                                                                                                                          |
| Print<br>              | print(expression);                                                 | Prints the value of an expression to the console.                                                                                                                                                                                                                           |
| If                     | if (exp) then { Stmt1 } else { Stmt2 }                             | Conditional execution.                                                                                                                                                                                                                                                      |
| openRFile              | openRFile(string_exp);                                             | Opens a file for reading.                                                                                                                                                                                                                                                   |
| readFile               | readFile(file_exp, var_name);                                      | Reads an integer from a file into a variable.                                                                                                                                                                                                                               |
| closeRFile             | closeRFile(string_exp);                                            | Closes an opened file.                                                                                                                                                                                                                                                      |
| No Operation           | nop;                                                               | Does nothing.                                                                                                                                                                                                                                                               |
| New                    | new int, new bool                                                  | Allocates memory on the heap for the type we specified.                                                                                                                                                                                                                     |
| Write Heap             | wH(var, exp)                                                       | Writes the result of the evaluation of 'exp' at the location hold by the 'var' variable.                                                                                                                                                                                    |
| While                  | while(exp) {stmt}                                                  | While 'exp' gets evaluated to true 'stmt' will continue to be run.                                                                                                                                                                                                          |
| Switch                 | switch(exp) case(exp1) {stmt1} case(exp2) {stmt2} default: {stmt3} | Evaluates an expression and branches to the matching case. Includes an implicit default branch for safety.                                                                                                                                                                  |
| Conditional assignment | v=exp1?exp2:exp3<br>                                               | Ternary operator for assigning variables.                                                                                                                                                                                                                                   |
| Fork                   | fork(stmt)                                                         | Spawns a child thread. Performs a **Deep Copy** of the Symbol Table stack to provide independent local scopes while maintaining reference to shared global memory.                                                                                                          |
| NewLock                | newLock(var)                                                       | Creates a new Lock and saves its address in the provided variable.                                                                                                                                                                                                          |
| Lock                   | lock(var)                                                          | An atomic operation that attempts to acquire the lock. If the lock is held by another thread, the current thread **yields** (pushes the statement back to the stack) until the lock is released.                                                                            |
| Unlock                 | unlock(var)                                                        | An atomic operation that releases the lock held by the variable.                                                                                                                                                                                                            |
| NewLatch               | newLatch(var,exp)                                                  | Creates a new CountDownLatch (with the number provided by evaluating 'exp') and saves its address in the provided variable.                                                                                                                                                 |
| CountDown              | countDown(var)                                                     | An atomic operation that decrements the counter of the provided latch.<br>Throws an error if the address in var does not point to a valid Latch.                                                                                                                            |
| LatchAwait             | latchAwait(var)                                                    | An atomic operation that checks if the Latch was opened. If it is, it does nothing, otherwise the current thread **yields** (pushes the statement back to the stack) until the counter reaches 0.<br>Throws an error if the address in var does not point to a valid Latch. |
| NewBarrier             | newBarrier(var, exp)                                               | Atomically creates a synchronization barrier for 'exp' threads.                                                                                                                                                                                                             |
| BarrAwait              | barrAwait(var)                                                     | Registers the current thread at the Barrier. Yields if the required number of threads has not yet arrived.<br>Throws an error if the address in var does not point to a valid Barrier.                                                                                      |
| Create Semaphore       | createSemaphore(var, exp)                                          | Atomically creates a synchronization Semaphore of size 'exp'.                                                                                                                                                                                                               |
| Acquire<br>Semaphore   | acquire(var)                                                       | Registers the current thread at the Semaphore. Yields if the semaphore is already full.<br>Throws an error if the address in var does not point to a valid Semaphore.                                                                                                       |
| Release<br>Semaphore   | release(var)                                                       | De-registers the current thread at the Semaphore.<br>Throws an error if the address in var does not point to a valid Semaphore.                                                                                                                                             |
| Call Procedure         | call \<proc name> \<list of parameters>                            | Calls the stored procedure with the provided parameters.<br>Throws an error if the procedure does not exist.                                                                                                                                                                |
| Return                 | return                                                             | Pops the Symbol Table stack to return to the caller's scope, effectively discarding local variables.                                                                                                                                                                        |

#### Expressions (based on Exp interface)

| Expression  | Syntax Example              |
| ----------- | --------------------------- |
| Value       | 10, true                    |
| Variable    | v, a                        |
| Arithmetic  | exp1 + exp2, exp1 * exp2    |
| Relational  | exp1 < exp2, exp1 == exp2   |
| Logical     | exp1 and exp2, exp1 or exp2 |
| Read Heap   | rH(variable), rH(value)     |

### 🚀 How to Run
Prerequisites:
- Java Development Kit (JDK) 11 or higher.
- Apache Maven.

Clone the repository:
```
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

Build the project using Maven:
This command will compile the code and package it into a runnable .jar file in the target/ directory.
code Bash

```
mvn clean package
```

Run the application:
```
java -jar target/ToyLanguage-1.0-SNAPSHOT.jar
```
      
You will be greeted with a text menu. Enter the number corresponding to the program you wish to execute.

### 📄 Logging Feature Explained

A key feature of this version is the ability to log the program's state. When you run a program, a log file (e.g., log1.txt) will be created or appended to in the project's root directory.

The log file provides a snapshot of the PrgState after each execution step, formatted as follows:
code Code

ExeStack:
[statement_on_top]
[next_statement]
...
SymTable:
var1 --> value1
var2 --> value2
...
Out:
output_value1
output_value2
...
FileTable:
"test.in" --> file_descriptor




