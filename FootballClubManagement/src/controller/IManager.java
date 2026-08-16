package controller;

/**
 * Interface to demonstrate Abstraction and Polymorphism OOP principles.
 * Defines common operations for management classes.
 */
public interface IManager<T> {
    void displayAll();
    void add();
    void update(String id);
    T searchById(String id);
}
