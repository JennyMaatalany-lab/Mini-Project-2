# Mini-Project-2
USEK - IT Workshop 1 - Mini Project 2

A small business app for a company called Cedarline Supply Co., built with JavaFX, FXML and the MVC structure from Lecture 4. Everything is stored in memory with ObservableList stores, there is no database.

## How the app works

**Login:** a small window where each team member signs in with their own account (listed below). The username is not case sensitive, the password is.

**Home:** four cards (HR, Clients, Inventory, Orders). After logging in, only the card of your own department is active, the other three are greyed out. There is also a Log out button.

**Department windows:** each one has a form on the left, a TableView on the right, and Insert / Update / Delete / Clear buttons. Clicking a row fills the form so it can be edited. The forms check the input (numbers, dates, email, phone) before saving.

## Accounts

| Department | Username | Password |
|------------|----------|----------|
| HR         | HR2000   | 2000HR   |
| Orders     | OD3000   | 3000OD   |
| Inventory  | INV1000  | 1000INV  |
| Clients    | CL4000   | 4000CL   |

## Who did what

| Part | Done by |
|------|---------|
| Login, Main app, Home screen, HR department (team leader) | Jenny |
| Orders department | Georgio |
| Inventory department | Reymondo |
| Clients department | Giovanni |

Each department has its own model, store, controller and FXML view, so everyone worked on separate files.

## How to run it

**What you need installed first**
- JDK 17 or newer (we used 21). Check with `java -version`
- Apache Maven. Check with `mvn -v`
- Git
- VS Code with the "Extension Pack for Java"

**Steps**
1. Clone the project:
   ```
   git clone https://github.com/JennyMaatalany-lab/Mini-Project-2.git
   ```
2. Open the `Mini-Project-2` folder in VS Code (File > Open Folder).
3. Open the terminal inside VS Code (Terminal > New Terminal).
4. Type:
   ```
   mvn javafx:run
   ```
   The first time it takes a bit longer because Maven downloads JavaFX.
5. The login window opens. Sign in with one of the accounts above, then click your department's card on the home screen.
