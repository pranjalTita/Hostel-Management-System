#include <iostream>
#include <string>
using namespace std;

class Student {
    string name;
    int roll;
public:
    Student(string n, int r) : name(n), roll(r) {
        cout << "Created: " << name << '\n';
    }

    void show() const {
        cout << "Name: " << name << ", Roll: " << roll << '\n';
    }

    ~Student() {
        cout << "Destroyed: " << name << '\n';
    }
};

void classroomDemo() {
    cout << "\nInside classroomDemo:\n";
    Student first("Mira", 101);
    Student second("Arjun", 102);
    first.show();
    second.show();
    // Local objects are destroyed in reverse order.
}

void heapDemo() {
    cout << "\nInside heapDemo:\n";
    Student* first = new Student("Ravi", 103);
    Student* second = new Student("Nisha", 104);
    first->show();
    second->show();
    delete first; // destructor runs here
    delete second;
}

int main() {
    cout << "Starting main\n";
    Student mainStudent("Karan", 100);
    mainStudent.show();
    classroomDemo();
    cout << "Returned from classroomDemo\n";
    heapDemo();
    cout << "Returned from heapDemo\n";
    cout << "Leaving main\n";
    return 0;
}
