#include <iostream>
#include <string>
using namespace std;

class Vehicle {
protected:
    string brand;
public:
    explicit Vehicle(string b) : brand(b) {}

    virtual void show() const {
        cout << "Vehicle brand: " << brand << '\n';
    }

    virtual int wheels() const {
        return 0;
    }

    virtual ~Vehicle() = default;
};

class Car : public Vehicle {
    int seats;
public:
    Car(string b, int s) : Vehicle(b), seats(s) {}

    void show() const override {
        cout << "Car brand: " << brand << '\n';
        cout << "Seats: " << seats << '\n';
    }

    int wheels() const override { return 4; }
    void openBoot() const { cout << "Boot opened\n"; }
};

class Bike : public Vehicle {
public:
    explicit Bike(string b) : Vehicle(b) {}
    void show() const override { cout << "Bike brand: " << brand << '\n'; }
    int wheels() const override { return 2; }
};

int main() {
    Car car("Toyota", 5);
    Bike bike("Honda");
    Vehicle* basePtr = &car;
    basePtr->show(); // virtual function uses Car's version
    cout << "Wheels: " << basePtr->wheels() << '\n';
    Car* carPtr = &car;
    carPtr->openBoot(); // available through derived pointer

    basePtr = &bike;
    cout << "\nAnother vehicle:\n";
    basePtr->show();
    cout << "Wheels: " << basePtr->wheels() << '\n';
    return 0;
}
