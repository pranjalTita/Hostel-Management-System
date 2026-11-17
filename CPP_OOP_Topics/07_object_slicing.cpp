#include <iostream>
#include <string>
using namespace std;

class Employee {
protected:
    string name;
public:
    explicit Employee(string n) : name(n) {}

    virtual void show() const {
        cout << "Employee: " << name << '\n';
    }

    virtual double salary() const {
        return 20000;
    }

    virtual ~Employee() = default;
};

class Manager : public Employee {
    double bonus;
public:
    Manager(string n, double b) : Employee(n), bonus(b) {}

    void show() const override {
        cout << "Manager: " << name << '\n';
        cout << "Bonus: " << bonus << '\n';
    }

    double salary() const override {
        return 20000 + bonus;
    }
};

void showByValue(Employee e) {
    cout << "Passing by value (sliced):\n";
    e.show();
    cout << "Salary: " << e.salary() << '\n';
}

void showByReference(const Employee& e) {
    cout << "Passing by reference (not sliced):\n";
    e.show();
    cout << "Salary: " << e.salary() << '\n';
}

int main() {
    Manager m("Aditi", 8000);
    m.show();
    showByValue(m); // manager-only part gets sliced off
    cout << '\n';
    showByReference(m);
    Employee* p = &m;
    cout << "\nUsing base pointer:\n";
    p->show();
    return 0;
}
