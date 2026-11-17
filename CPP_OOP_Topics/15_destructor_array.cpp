#include <iostream>
#include <string>
using namespace std;

class Attendance {
    string* names;
    int count;
public:
    explicit Attendance(int n) : count(n) {
        names = new string[count];
        cout << "Allocated space for " << count << " names\n";
    }

    Attendance(const Attendance& other) : count(other.count) {
        names = new string[count];
        for (int i = 0; i < count; i++)
            names[i] = other.names[i];
    }

    Attendance& operator=(const Attendance&) = delete;

    void setName(int index, string name) {
        if (index >= 0 && index < count)
            names[index] = name;
    }

    void show() const {
        cout << "Attendance list:\n";
        for (int i = 0; i < count; i++)
            cout << i + 1 << ". " << names[i] << '\n';
    }

    ~Attendance() {
        delete[] names; // matches new[] used above
        cout << "Attendance array deleted\n";
    }
};

void createTemporaryList() {
    Attendance temp(2);
    temp.setName(0, "Aman");
    temp.setName(1, "Riya");
    temp.show();
} // temp is destroyed when this function ends

int main() {
    createTemporaryList();
    cout << "\nBack inside main\n";
    Attendance semester(3);
    semester.setName(0, "Neha");
    semester.setName(1, "Raj");
    semester.setName(2, "Dev");
    semester.show();
    return 0;
}
