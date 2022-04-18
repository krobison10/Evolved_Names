public class MyLinkedList<T extends Comparable<T>>
{
    private Node first, current, previous;
    private int size = 0;
    public long comparisons = 0;

    /**
     * Creates a new linked list given the elements or an array passed as arguments.
     * @param items to be added to the new list
     */
    @SafeVarargs
    public MyLinkedList(T... items)
    {
        for(T item : items)
        {
            addBefore(item);
        }
    }

    /**
     * Adds the item to the end of the list. Only works if the current pointer is null, otherwise it will
     * do nothing.
     * @param item the item to be added
     */
    public void add(T item)
    {
        if(current == null)
        {
            addBefore(item);
        }
    }

    /**
     * Adds the item before the current pointer. If the current pointer is null,
     * the item will be added to the end of the list.
     * @param item the item to be added
     */
    public void addBefore(T item)
    {
        if(previous != null) //Has node before
        {
            previous.next = new Node(item);
            previous = previous.next;
            if(current != null) //& After too
            {
                previous.next = current;
            }
        }
        else if(current != null) //Is the first node of the list
        {
            previous = new Node(item);
            previous.next = current;
            first = previous;
        }
        if(first == null) //Empty
        {
            first = new Node(item);
            previous = first;
        }
        size++;
    }

    /**
     * Adds the item after the current pointer. If the current pointer is null, the method will
     * do nothing.
     * @param item the item to be added
     */
    public void addAfter(T item)
    {
        if(current != null)
        {
            if(current.next != null)
            {
                var newNext = current.next;
                current.next = new Node(item);
                current.next.next = newNext;
            }
            else
            {
                current.next = new Node(item);
            }
            size++;
        }
    }

    /**
     * Adds the item to the front of the list regardless of the position of the current pointer.
     * @param item the item to be added
     */
    public void addToFront(T item)
    {
        if(first != null)
        {
            Node temp = new Node(item);
            temp.next = first;
            first = temp;
            size++;
        }
        else
        {
            addBefore(item);
        }
    }

    /**
     * Moves the current element to the front of the list
     */
    public void moveToFront()
    {
        if(current != null)
        {
            T item = remove();
            addToFront(item);
        }
    }

    /**
     * Swaps the current element with the element in the position before it.
     */
    public void swapWithPrevious()
    {
        if(current != first)
        {
            T temp = current.item;
            current.item = previous.item;
            previous.item = temp;
        }
    }

    /**
     * Swaps the current element with the element in the position after it.
     */
    private void swapWithNext()
    {
        if(current.next != null)
        {
            T temp = current.item;
            current.item = current.next.item;
            current.next.item = temp;
        }
    }
    /**
     * Overwrites the value of the current element, does nothing if current is null.
     * @param item the new item to overwrite the old
     */
    public void set(T item)
    {
        if(current != null)
        {
            current.item = item;
        }
    }

    /**
     * Removes the item at the current index.
     * @return the item
     */
    public T remove()
    {
        if(first == null || current == null)
        {
            return null;
        }
        Node temp = current;
        if(previous == null)
        {
            if(current.next != null)
            {
                current = current.next;
                first = current;
            }
            else
            {
                current = null;
                first = null;
            }
        }
        else
        {
            if(current.next != null)
            {
                current = current.next;
                previous.next = current;
            }
            else
            {
                current = current.next;
                previous.next = null;
            }
        }
        size--;
        return temp.item;
    }

    /**
     * @return the current item. Null if current doesn't point anywhere
     */
    public T current()
    {
        T item = null;
        if(current != null)
        {
            item = current.item;
        }
        return item;
    }

    /**
     * Sets current to be the first element in the list
     * @return the value of the first and now current node
     */
    public T first()
    {
        T item = null;
        if(first != null)
        {
            previous = null;
            current = first;
            item = current.item;
        }
        return item;
    }

    /**
     * Moves the current pointer to the next position in the list. If the current position is at the end
     * of the list when this is called, current will become null.
     * @return the value of the next node
     */
    public T next() {
        T item;
        if(current != null)
        {
            previous = current;
            current = current.next;
        }

        if(current != null)
        {
            item = current.item;
        }
        else
        {
            item = null;
        }
        return item;
    }

    /**
     * Returns the first element of the list.
     */
    public T getFirst()
    {
        T output = null;
        if(first != null)
        {
            output = first.item;
        }
        return output;
    }

    /**
     * Returns the last element of the list, returns null if current isn't null. Runs in constant time.
     */
    public T getLast()
    {
        T output = null;
        if(current == null && previous != null)
        {
            output = previous.item;
        }
        return output;
    }

    /**
     * Returns the element at the given index, null if the index is out of bounds. Runs in O(n) time, where
     * n is the index.
     */
    public T getAtIndex(int index)
    {
        Node cur = first;
        int i = 0;
        while(cur != null)
        {
            if(i == index)
            {
                return cur.item;
            }
            cur = cur.next;
            i++;
        }
        return null;
    }

    /**
     * Searches the list and returns true if the given element is found.
     * @param item the item to search for
     * @return true if found, false otherwise
     */
    public boolean contains(T item)
    {
        Node current = first;

        comparisons++;
        while(current != null)
        {
            comparisons++;
            if(current.item.compareTo(item) == 0)
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * @return the length of the list
     */
    public int size()
    {
        return size;
    }

    /**
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Sorts the list using merge sort. If the length is less than 10, the sort is done with bubble sort.
     */
    public void sort()
    {
        if(size < 10)
        {
            bubbleSort();
        }
        else
        {
            T[] array = (T[]) new Comparable[size];
            int i = 0;
            first();
            while(current != null)
            {
                array[i] = remove();
                i++;
            }
            sort(array, 0, array.length - 1);

            for(T element : array)
            {
                addBefore(element);
            }
        }
    }

    private void merge(T[] array, int start, int mid, int end)
    {
        //Find sizes of two arrays to merge
        int leftLength = mid - start + 1;
        int rightLength = end - mid;

        //Create temporary arrays
        T[] left = (T[]) new Comparable[leftLength];
        T[] right = (T[]) new Comparable[rightLength];

        //Copy data into temporary arrays
        for(int i = 0; i < leftLength; ++i)
        {
            left[i] = array[start + i];
        }
        for(int j = 0; j < rightLength; ++j)
        {
            right[j] = array[mid + 1 + j];
        }
        //Merge the temporary arrays
        int i = 0, j = 0;
        //Initial index of merged subarray array
        int k = start;
        while(i < leftLength && j < rightLength)
        {
            if(left[i].compareTo(right[j]) <= 0)
            {
                array[k] = left[i];
                i++;
            }
            else
            {
                array[k] = right[j];
                j++;
            }
            k++;
        }
        //Copy remaining elements
        while(i < leftLength)
        {
            array[k] = left[i];
            i++;
            k++;
        }
        while(j < rightLength)
        {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    private void sort(T[] array, int start, int end)
    {
        if(start < end)
        {
            //Find midpoint
            int middle = start + (end - start) / 2;

            //sort first and second halves
            sort(array, start, middle);
            sort(array, middle + 1, end);

            //Merge the sorted halves
            merge(array, start, middle, end);
        }
    }

    /**
     * Sorts the list using bubble sort
     */
    public void bubbleSort()
    {
        int n = size;
        first();
        for(int i = 0; i < n-1; i++)
        {
            for(int j = 0; j < n-i-1; j++)
            {
                if(current.item.compareTo(current.next.item) > 0)
                {
                    swapWithNext();
                }
                next();
            }
            first();
        }
        while(current != null)
        {
            next();
        }
    }

    @Override
    public String toString()
    {
        if(isEmpty())
        {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node cur = first;

        while(cur != null)
        {
            sb.append(cur).append(", ");
            cur = cur.next;
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    private class Node
    {
        public T item;
        public Node next = null;

        public Node(T item)
        {
            this.item = item;
        }
        public Node() {}
        public String toString()
        {
            return item.toString();
        }
    }
}

