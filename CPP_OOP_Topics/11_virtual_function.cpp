#include <iostream>
#include <string>
using namespace std;

class Shape {
protected:
    string color;
public:
    explicit Shape(string c) : color(c) {}
    virtual double area() const = 0;

    virtual void display() const {
        cout << "Color: " << color << '\n';
        cout << "Area: " << area() << '\n';
    }

    virtual ~Shape() = default;
};

class Rectangle : public Shape {
    double length, width;
public:
    Rectangle(double l, double w)
        : Shape("Blue"), length(l), width(w) {}

    double area() const override { return length * width; }
    void display() const override {
        cout << "Rectangle\n";
        Shape::display();
    }
};

class Circle : public Shape {
    double radius;
public:
    explicit Circle(double r) : Shape("Red"), radius(r) {}
    double area() const override { return 3.14159 * radius * radius; }
    void display() const override {
        cout << "Circle\n";
        Shape::display();
    }
};

void showShape(const Shape& shape) {
    shape.display(); // runtime polymorphism
}

int main() {
    Rectangle rectangle(6, 4);
    Circle circle(3);
    Shape* shapes[] = {&rectangle, &circle};
    for (Shape* shape : shapes) {
        showShape(*shape);
        cout << "----------------\n";
    }
    return 0;
}
