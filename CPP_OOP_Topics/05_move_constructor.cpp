#include <iostream>
#include <utility>
using namespace std;

class Scores {
    int* data;
    int size;
public:
    explicit Scores(int n) : data(new int[n]{}), size(n) {
        cout << "Normal constructor\n";
    }

    Scores(const Scores& other) : data(new int[other.size]), size(other.size) {
        for (int i = 0; i < size; i++) data[i] = other.data[i];
        cout << "Copy constructor\n";
    }

    Scores(Scores&& other) noexcept : data(other.data), size(other.size) {
        other.data = nullptr; // source no longer owns the array
        other.size = 0;
        cout << "Move constructor\n";
    }

    Scores& operator=(Scores&& other) noexcept {
        if (this != &other) {
            delete[] data;
            data = other.data;
            size = other.size;
            other.data = nullptr;
            other.size = 0;
        }
        cout << "Move assignment\n";
        return *this;
    }

    Scores& operator=(const Scores&) = delete;

    void set(int i, int value) {
        if (i >= 0 && i < size) data[i] = value;
    }

    void show() const {
        for (int i = 0; i < size; i++) cout << data[i] << ' ';
        cout << '\n';
    }

    ~Scores() { delete[] data; }
};

int main() {
    Scores first(3);
    first.set(0, 70);
    first.set(1, 80);
    first.set(2, 90);
    Scores copied(first);
    Scores moved(std::move(first));
    Scores result(1);
    result = std::move(copied);
    cout << "Moved data: "; moved.show();
    cout << "Assigned data: "; result.show();
    return 0;
}
