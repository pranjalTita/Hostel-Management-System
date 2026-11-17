#include <iostream>
using namespace std;

void printArray(const int* ptr, int size) {
    for (int i = 0; i < size; i++)
        cout << "Index " << i << ": " << *(ptr + i) << '\n';
}

int sumArray(const int* ptr, int size) {
    int sum = 0;
    const int* end = ptr + size;
    while (ptr < end) {
        sum += *ptr;
        ptr++; // moves to the next int
    }
    return sum;
}

int largest(const int* ptr, int size) {
    int best = *ptr;
    for (int i = 1; i < size; i++) {
        if (*(ptr + i) > best)
            best = *(ptr + i);
    }
    return best;
}

int main() {
    int marks[] = {65, 85, 72, 93, 79};
    const int size = sizeof(marks) / sizeof(marks[0]);
    int* ptr = marks;

    cout << "Array using pointer arithmetic:\n";
    printArray(ptr, size);
    cout << "\nFirst value: " << *ptr << '\n';
    cout << "Second value: " << *(ptr + 1) << '\n';

    ptr += 2;
    cout << "Value after moving 2 positions: " << *ptr << '\n';
    ptr--; // one position back
    cout << "Value after moving back: " << *ptr << '\n';

    cout << "\nSum: " << sumArray(marks, size) << '\n';
    cout << "Largest: " << largest(marks, size) << '\n';
    cout << "Number of elements: " << size << '\n';
    // Only move within the same array (or one past its end).
    return 0;
}
