#include <iostream>
using namespace std;

class Celsius {
    double value;
public:
    explicit Celsius(double c) : value(c) {}
    double getValue() const { return value; }
    void show() const { cout << value << " C\n"; }
};

class Fahrenheit {
    double value;
public:
    // A constructor in the target class performs conversion.
    Fahrenheit(const Celsius& c)
        : value(c.getValue() * 9.0 / 5.0 + 32) {}

    explicit Fahrenheit(double f) : value(f) {}
    double getValue() const { return value; }

    void show() const {
        cout << value << " F\n";
    }

    bool isHot() const {
        return value >= 86;
    }
};

void compare(const Fahrenheit& first, const Fahrenheit& second) {
    if (first.getValue() > second.getValue())
        cout << "First temperature is higher\n";
    else if (first.getValue() < second.getValue())
        cout << "Second temperature is higher\n";
    else
        cout << "Both temperatures are equal\n";
}

int main() {
    Celsius morning(25);
    Celsius afternoon(35);
    Fahrenheit morningF = morning; // class -> class
    Fahrenheit afternoonF = afternoon;
    cout << "Morning: "; morning.show();
    cout << "In Fahrenheit: "; morningF.show();
    cout << "\nAfternoon: "; afternoon.show();
    cout << "In Fahrenheit: "; afternoonF.show();
    compare(morningF, afternoonF);
    cout << "Hot afternoon: " << (afternoonF.isHot() ? "Yes" : "No") << '\n';
    return 0;
}
