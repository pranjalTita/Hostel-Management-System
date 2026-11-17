#include <iostream>
#include <string>
using namespace std;

class Wallet {
    string owner;
    int balance;
public:
    Wallet(string name, int money)
        : owner(name), balance(money) {}

    void deposit(int amount) {
        if (amount > 0)
            balance += amount;
    }

    bool withdraw(int amount) {
        if (amount <= 0 || amount > balance)
            return false;
        balance -= amount;
        return true;
    }

    // Convert an object into its integer balance.
    explicit operator int() const {
        return balance;
    }

    void display() const {
        cout << "Owner: " << owner << '\n';
        cout << "Balance: Rs. " << balance << '\n';
    }
};

int main() {
    Wallet wallet("Priya", 1500);
    cout << "Initial wallet:\n";
    wallet.display();

    wallet.deposit(750);
    if (wallet.withdraw(400))
        cout << "Withdrawal successful\n";
    else
        cout << "Withdrawal failed\n";

    int amount = static_cast<int>(wallet); // Wallet -> int
    cout << "\nConverted integer: " << amount << '\n';
    cout << "After transactions:\n";
    wallet.display();

    if (amount >= 1000)
        cout << "Enough money for shopping\n";
    return 0;
}
