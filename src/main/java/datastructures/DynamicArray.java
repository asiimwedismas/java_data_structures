package datastructures;

@SuppressWarnings("unchecked")
public class DynamicArray<T> {

    private T[] elements;
    private int length; // count of elements in array
    private int capacity; // total capacity or slots in the array


    public DynamicArray() {
        this(10);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Initial initialCapacity must be non-negative");

        elements = (T[]) new Object[initialCapacity];
        capacity = initialCapacity;
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public T get(int index) {
        if (!isIndexValid(index)) throw new IndexOutOfBoundsException();

        return elements[index];
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < length;
    }


    public void set(int index, T elem) {
        if (!isIndexValid(index)) throw new IndexOutOfBoundsException();

        elements[index] = elem;
    }

    public void clear() {
        elements = (T[]) new Object[0];
        capacity = length = 0;
    }

    public void add(T elem) {
        if (length == capacity) {
            if (capacity == 0) capacity = 10;
            else capacity *= 2;

            T[] newArray = (T[]) new Object[capacity];

            for (int i = 0; i < length; i++) newArray[i] = elements[i];

            elements = newArray;
        }

        elements[length++] = elem;
    }

    // Removes an element at the specified index in this array.
    public T removeAt(int rm_index) {
        if (!isIndexValid(rm_index)) throw new IndexOutOfBoundsException();

        T elem = elements[rm_index];

        for (int i = rm_index; i < length; i++) {
            elements[i] = elements[i + 1];
        }

        length--;

        return elem;
    }

    public boolean remove(Object obj) {
        int indexOfObject = indexOf(obj);
        if (indexOfObject == -1) return false;

        removeAt(indexOfObject);
        return true;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < length; i++) {
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
}