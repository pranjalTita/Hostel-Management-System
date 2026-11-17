#include <iostream>
#include <string>
using namespace std;

class MarksList {
    int* marks;
    int count;
public:
    explicit MarksList(int n) : count(n) {
        marks = new int[count];
        for (int i = 0; i < count; i++)
            marks[i] = 0;
    }

    MarksList(const MarksList& other) : count(other.count) {
        marks = new int[count];
        for (int i = 0; i < count; i++)
            marks[i] = other.marks[i];
    }

    void setMark(int index, int value) {
        if (index >= 0 && index < count)
            marks[index] = value;
    }

    void show() const {
        for (int i = 0; i < count; i++)
            cout << "Student " << i + 1 << ": " << marks[i] << '\n';
    }

    double average() const {
        if (count == 0) return 0;
        int sum = 0;
        for (int i = 0; i < count; i++)
            sum += marks[i];
        return static_cast<double>(sum) / count;
    }

    ~MarksList() {
        // Release the array created using new[].
        delete[] marks;
        cout << "Memory released\n";
    }
};

int main() {
    MarksList original(3);
    original.setMark(0, 75);
    original.setMark(1, 84);
    original.setMark(2, 92);
    MarksList copied = original;
    copied.setMark(0, 99);
    cout << "Original list:\n";
    original.show();
    cout << "Average: " << original.average() << '\n';
    cout << "\nCopied list:\n";
    copied.show();
    return 0;
}
