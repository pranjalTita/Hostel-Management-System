#include <iostream>
#include <string>
using namespace std;

class Book {
    string title;
    string author;
    int pages;
public:
    Book(string t, string a, int p)
        : title(t), author(a), pages(p) {}

    void display() const {
        cout << "Title: " << title << '\n';
        cout << "Author: " << author << '\n';
        cout << "Pages: " << pages << '\n';
    }

    void addPages(int extra) {
        if (extra > 0)
            pages += extra;
    }

    bool isLongBook() const {
        return pages >= 300;
    }
};

int main() {
    Book book1("C++ Basics", "Mr. Shah", 220);
    Book book2("Data Structures", "Ms. Rao", 350);
    Book* ptr = &book1; // address of an existing object

    cout << "First book:\n";
    ptr->display();
    ptr->addPages(100);
    cout << "After adding pages:\n";
    ptr->display();

    ptr = &book2; // same pointer, another object
    cout << "\nSecond book:\n";
    ptr->display();
    cout << "Long book: " << (ptr->isLongBook() ? "Yes" : "No") << '\n';

    Book* extraBook = new Book("OOP Notes", "Mr. Mehta", 150);
    cout << "\nDynamically created book:\n";
    extraBook->display();
    delete extraBook; // free the heap object
    extraBook = nullptr;
    return 0;
}
