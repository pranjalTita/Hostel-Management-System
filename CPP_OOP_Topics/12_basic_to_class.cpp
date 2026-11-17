#include <iostream>
using namespace std;

class Distance {
    int meters;
public:
    // This converting constructor takes an int.
    Distance(int m) : meters(m) {}

    int getMeters() const { return meters; }

    void show() const {
        cout << meters << " meters\n";
    }

    Distance add(const Distance& other) const {
        return Distance(meters + other.meters);
    }

    bool isLongerThan(const Distance& other) const {
        return meters > other.meters;
    }
};

void printDistance(const Distance& d) {
    d.show();
}

int main() {
    int firstValue = 120;
    int secondValue = 80;

    Distance first = firstValue; // int -> Distance
    Distance second(secondValue);
    cout << "First distance: ";
    first.show();
    cout << "Second distance: ";
    second.show();

    Distance total = first.add(second);
    cout << "Total distance: ";
    total.show();

    cout << "Is first longer? "
         << (first.isLongerThan(second) ? "Yes" : "No") << '\n';

    cout << "Direct call using an integer: ";
    printDistance(50); // conversion happens automatically
    cout << "Meters in total: " << total.getMeters() << '\n';
    return 0;
}
