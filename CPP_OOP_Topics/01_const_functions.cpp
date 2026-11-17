#include <iostream>
#include <string>
using namespace std;

class Student {
    string name;
    int roll;
    int marks[3];
public:
    Student(string n, int r, int a, int b, int c)
        : name(n), roll(r) {
        marks[0] = a;
        marks[1] = b;
        marks[2] = c;
    }

    int total() const {
        int sum = 0;
        for (int i = 0; i < 3; i++)
            sum += marks[i];
        return sum;
    }

    double average() const {
        return total() / 3.0;
    }

    void display() const {
        cout << "Name: " << name << "\nRoll: " << roll << '\n';
        for (int i = 0; i < 3; i++)
            cout << "Subject " << i + 1 << ": " << marks[i] << '\n';
        cout << "Total: " << total() << '\n';
        cout << "Average: " << average() << '\n';
    }

    void updateMarks(int subject, int value) {
        if (subject >= 1 && subject <= 3 && value >= 0 && value <= 100)
            marks[subject - 1] = value;
    }
};

int main() {
    Student s1("Aman", 12, 78, 83, 91);
    cout << "Before updating:\n";
    s1.display();
    s1.updateMarks(2, 88); // updates are done in a non-const function
    cout << "\nAfter updating:\n";
    s1.display();

    const Student s2("Riya", 15, 80, 86, 90);
    cout << "\nDetails of const object:\n";
    s2.display(); // const objects can call const functions
    return 0;
}
