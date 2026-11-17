#include <iostream>
#include <string>
using namespace std;

class Person {
protected:
    string name;
    int age;
public:
    Person(string n, int a) : name(n), age(a) {}
    void showPerson() const {
        cout << "Name: " << name << '\n';
        cout << "Age: " << age << '\n';
    }
    virtual ~Person() = default;
};

class Student : public Person {
    int roll;
    float cgpa;
public:
    Student(string n, int a, int r, float c)
        : Person(n, a), roll(r), cgpa(c) {}

    void showStudent() const {
        showPerson();
        cout << "Roll number: " << roll << '\n';
        cout << "CGPA: " << cgpa << '\n';
    }

    void updateCgpa(float value) {
        if (value >= 0 && value <= 10)
            cgpa = value;
    }

    bool isEligible() const {
        return cgpa >= 8.0;
    }
};

int main() {
    Student first("Neha", 20, 101, 8.4f);
    Student second("Kabir", 21, 102, 7.7f);
    Student* current = &first; // pointer is of the derived class

    cout << "First student:\n";
    current->showStudent();
    cout << "Eligible: " << (current->isEligible() ? "Yes" : "No") << '\n';

    current = &second;
    cout << "\nSecond student:\n";
    current->updateCgpa(8.2f);
    current->showStudent();
    cout << "Eligible: " << (current->isEligible() ? "Yes" : "No") << '\n';
    return 0;
}
