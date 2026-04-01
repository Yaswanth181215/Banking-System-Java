import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

// Account class
class Account {
    String name;
    int pin;
    double balance;
    ArrayList<String> history = new ArrayList<>();

    Account(String name, int pin) {
        this.name = name;
        this.pin = pin;
        this.balance = 0;
    }

    String deposit(double amt) {
        if (amt <= 0) return "Invalid amount!";
        balance += amt;
        history.add("Deposited: " + amt);
        return "Deposited: " + amt;
    }

    String withdraw(double amt) {
        if (amt <= 0) return "Invalid amount!";
        if (amt <= balance) {
            balance -= amt;
            history.add("Withdrawn: " + amt);
            return "Withdrawn: " + amt;
        } else {
            return "Insufficient balance!";
        }
    }

    double getBalance() {
        return balance;
    }

    String getHistory() {
        return history.toString();
    }
}

// Main GUI
public class BankFinalGUI {

    ArrayList<Account> accounts = new ArrayList<>();
    Account currentUser = null;

    JFrame f = new JFrame("Banking System");

    JTextField nameField = new JTextField();
    JPasswordField pinField = new JPasswordField();
    JLabel message = new JLabel("");

    JTextField amountField = new JTextField();

    BankFinalGUI() {
        showLogin();

        f.setSize(400, 350);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    // LOGIN SCREEN
    void showLogin() {
        f.getContentPane().removeAll();

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(50, 50, 100, 30);
        nameField.setBounds(150, 50, 150, 30);

        JLabel l2 = new JLabel("PIN:");
        l2.setBounds(50, 90, 100, 30);
        pinField.setBounds(150, 90, 150, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 140, 120, 30);

        JButton signupBtn = new JButton("Signup");
        signupBtn.setBounds(200, 140, 120, 30);

        message.setBounds(50, 180, 300, 30);

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> signup());

        f.add(l1); f.add(nameField);
        f.add(l2); f.add(pinField);
        f.add(loginBtn); f.add(signupBtn);
        f.add(message);

        f.revalidate();
        f.repaint();
    }

    // SIGNUP
    void signup() {
        try {
            String name = nameField.getText();
            int pin = Integer.parseInt(new String(pinField.getPassword()));

            accounts.add(new Account(name, pin));
            message.setText("Account Created!");

        } catch (Exception e) {
            message.setText("Invalid input!");
        }
    }

    // LOGIN
    void login() {
        String name = nameField.getText();
        int pin;

        try {
            pin = Integer.parseInt(new String(pinField.getPassword()));
        } catch (Exception e) {
            message.setText("Invalid PIN!");
            return;
        }

        for (Account acc : accounts) {
            if (acc.name.equals(name) && acc.pin == pin) {
                currentUser = acc;
                showDashboard();
                return;
            }
        }

        message.setText("Login Failed!");
    }

    // DASHBOARD
    void showDashboard() {
        f.getContentPane().removeAll();

        JLabel welcome = new JLabel("Welcome " + currentUser.name);
        welcome.setBounds(100, 20, 200, 30);

        amountField.setBounds(120, 60, 150, 30);

        JButton depBtn = new JButton("Deposit");
        depBtn.setBounds(30, 100, 100, 30);

        JButton wdBtn = new JButton("Withdraw");
        wdBtn.setBounds(140, 100, 100, 30);

        JButton balBtn = new JButton("Balance");
        balBtn.setBounds(250, 100, 100, 30);

        JButton histBtn = new JButton("History");
        histBtn.setBounds(80, 150, 100, 30);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(200, 150, 100, 30);

        JLabel result = new JLabel("");
        result.setBounds(50, 200, 300, 30);

        depBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                result.setText(currentUser.deposit(amt));
            } catch (Exception ex) {
                result.setText("Invalid input!");
            }
        });

        wdBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                result.setText(currentUser.withdraw(amt));
            } catch (Exception ex) {
                result.setText("Invalid input!");
            }
        });

        balBtn.addActionListener(e -> {
            result.setText("Balance: " + currentUser.getBalance());
        });

        histBtn.addActionListener(e -> {
            result.setText(currentUser.getHistory());
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showLogin();
        });

        f.add(welcome);
        f.add(amountField);
        f.add(depBtn); f.add(wdBtn); f.add(balBtn);
        f.add(histBtn); f.add(logoutBtn);
        f.add(result);

        f.revalidate();
        f.repaint();
    }

    public static void main(String[] args) {
        new BankFinalGUI();
    }
}