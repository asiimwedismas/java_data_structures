package datastructures;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class DynamicArray<T> /*implements Iterable<T>*/ {

    private T[] elements;
    private int size; // count of elements in array
    private int capacity; // total capacity or slots in the array


    public DynamicArray() {
        this(10);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Initial capacity must be non-negative");

        this.elements = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        if (!isIndexValid(index)) throw new IndexOutOfBoundsException();

        return elements[index];
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < size();
    }


    public void set(int index, T elem) {
        if (!isIndexValid(index)) throw new IndexOutOfBoundsException();

        elements[index] = elem;
    }

    public void clear() {
        this.elements = (T[]) new Object[0];
        this.capacity = 0;
    }

    public void add(T elem) {
        if (size == capacity) {
            if (this.capacity == 0) this.capacity = 10;
            else this.capacity *= 2;

            T[] newArray = (T[]) new Object[this.capacity];

            for (int i = 0; i < this.size; i++) newArray[i] = this.elements[i];

            this.elements = newArray;
        }

        this.elements[size++] = elem;
    }

    // Removes an element at the specified index in this array.
    public T removeAt(int rm_index) {
        if (!isIndexValid(rm_index)) throw new IndexOutOfBoundsException();

        T elem = this.elements[rm_index];

        for (int i = rm_index; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }

        this.size--;

        return elem;
    }

    public boolean remove(Object obj) {
        int indexOfObject = indexOf(obj);
        if(indexOfObject == -1) return false;

        removeAt(indexOfObject);
        return true;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < this.size; i++) {
            if (obj == null) {
                if (elements[i] == null) return i;
            } else {
                if (obj.equals(elements[i])) return i;
            }
        }

        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }
//
//    // Iterator is still fast but not as fast as iterative for loop
//    @Override
//    public Iterator<T> iterator() {
//
//    }
//
//    @Override
//    public String toString() {
//
//    }
}