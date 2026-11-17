#include <iostream>
#include <string>
using namespace std;

class LibraryBook {
    string title;
    string author;
    int availableCopies;
    mutable int viewCount;
public:
    LibraryBook(string t, string a, int copies)
        : title(t), author(a), availableCopies(copies), viewCount(0) {}

    void showDetails() const {
        viewCount++; // allowed even inside a const function
        cout << "Title: " << title << '\n';
        cout << "Author: " << author << '\n';
        cout << "Available copies: " << availableCopies << '\n';
    }

    int getViews() const {
        return viewCount;
    }

    void borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            cout << "Book borrowed successfully\n";
        } else {
            cout << "No copies available\n";
        }
    }

    void returnBook() {
        availableCopies++;
    }
};

int main() {
    LibraryBook b1("Programming in C++", "A. Kumar", 2);
    cout << "Book information:\n";
    b1.showDetails();
    b1.borrowBook();
    b1.showDetails();
    cout << "Views so far: " << b1.getViews() << '\n';

    const LibraryBook b2("Algorithms", "S. Patel", 4);
    cout << "\nConst book:\n";
    b2.showDetails();
    b2.showDetails();
    cout << "Views: " << b2.getViews() << '\n';
    // b2.borrowBook() would not compile.
    return 0;
}
