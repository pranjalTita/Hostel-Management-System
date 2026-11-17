# C++ OOP Topics — Practice Programs

This folder contains **17 standalone C++ programs** based on the topic list.
Each program is about 50 lines long, demonstrates the named concept using a small example,
and includes a few straightforward comments.

## How to run

Open a terminal in this folder, then compile any file using C++17:

```bash
g++ -std=c++17 01_const_functions.cpp -o program
./program
```

On Windows PowerShell, run the compiled program with `./program.exe` (or `.\program.exe`).
Each file has its **own main()**, so compile them separately, not all together.

## Topics

| File | Topic |
| --- | --- |
| `01_const_functions.cpp` | Const member functions |
| `02_dynamic_memory.cpp` | Explicit memory deallocation |
| `03_derived_pointer.cpp` | Derived-class pointers |
| `04_object_pointer.cpp` | Pointers to objects |
| `05_move_constructor.cpp` | Move constructor and move assignment |
| `06_mutable.cpp` | Mutable data members |
| `07_object_slicing.cpp` | Object slicing |
| `08_pointer_arithmetic.cpp` | Pointer arithmetic |
| `09_base_derived_pointer.cpp` | Base and derived pointers |
| `10_preprocessor.cpp` | Preprocessor directives |
| `11_virtual_function.cpp` | Virtual functions |
| `12_basic_to_class.cpp` | Basic to class conversion |
| `13_class_to_basic.cpp` | Class to basic conversion |
| `14_class_to_class.cpp` | Class to class conversion |
| `15_destructor_array.cpp` | Destructor and dynamic arrays |
| `16_destructor_objects.cpp` | Destructor with multiple objects |
| `17_friend_class.cpp` | Friend class |

## Notes

- `new[]` is matched with `delete[]`; `new` is matched with `delete`.
- The examples focus on one major topic each, but also use normal C++ features.
- These are learning examples, not production systems.
