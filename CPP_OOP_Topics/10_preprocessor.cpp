#include <iostream>
#include <string>
using namespace std;

#define COLLEGE_NAME "Engineering College"
#define MAX_MARKS 100
#define SQUARE(x) ((x) * (x))
#define PASS_MARKS 40

#ifndef ENABLE_DETAILS
#define ENABLE_DETAILS 1
#endif

class Result {
    string name;
    int marks;
public:
    Result(string n, int m) : name(n), marks(m) {}

    bool passed() const {
        return marks >= PASS_MARKS;
    }

    void print() const {
        cout << "Student: " << name << '\n';
        cout << "Marks: " << marks << '/' << MAX_MARKS << '\n';
        cout << "Status: " << (passed() ? "Pass" : "Fail") << '\n';
    }
};

int main() {
    cout << "College: " << COLLEGE_NAME << '\n';
    cout << "Maximum marks: " << MAX_MARKS << '\n';

    Result first("Rohit", 76);
    Result second("Meera", 35);

#if ENABLE_DETAILS
    cout << "\nFirst result:\n";
    first.print();
    cout << "\nSecond result:\n";
    second.print();
#else
    cout << "Detailed output is disabled\n";
#endif

    int number = 5;
    cout << "\nSquare of " << number << " = " << SQUARE(number) << '\n';
    // Parentheses in macros help avoid precedence errors.
    return 0;
}
