#include <iostream>
#include <string>
using namespace std;

class BankAccount {
    string holder;
    int balance;
    int pin;
public:
    BankAccount(string name, int money, int secret)
        : holder(name), balance(money), pin(secret) {}

    void deposit(int amount) {
        if (amount > 0)
            balance += amount;
    }

    void showHolder() const {
        cout << "Account holder: " << holder << '\n';
    }

    friend class BankManager; // manager can access private data
};

class BankManager {
public:
    void showBalance(const BankAccount& account) const {
        account.showHolder();
        cout << "Balance: Rs. " << account.balance << '\n';
    }

    bool verifyPin(const BankAccount& account, int entered) const {
        return account.pin == entered;
    }

    void addBonus(BankAccount& account, int amount) const {
        if (amount > 0)
            account.balance += amount;
    }
};

int main() {
    BankAccount account("Ishan", 5000, 1234);
    BankManager manager;
    manager.showBalance(account);

    int enteredPin = 1234;
    if (manager.verifyPin(account, enteredPin)) {
        cout << "PIN verified\n";
        manager.addBonus(account, 500);
    } else {
        cout << "Incorrect PIN\n";
    }
    account.deposit(1000);
    cout << "\nUpdated account details:\n";
    manager.showBalance(account);
    return 0;
}
