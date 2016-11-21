package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;

//-------------------------------------------------------------------------
/**
 * Example unit tests for the CircularLinkedList class.
 *
 * @author jackrg94
 * @version 2015.08.05
 */
public class CircularLinkedListTests extends student.TestCase
{
    //~ Fields ................................................................

    private CircularLinkedList<String> list;


    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Creates a brand new, empty CircularLinkedList for each test method.
     */
    public void setUp()
    {
        list = new CircularLinkedList<String>();
    }
    //-----------------------------------------------------------
    /**
     * Test method for next
     */
    @Test
    public void testNext() {
        list.next();
        assertEquals(list.size(), 0);
        list.add("k");
        list.add("l");
        list.next();
        assertEquals(list.getCurrent(), "k");

    }
    //-----------------------------------------------------------
    /**
     * Test method for previous
     */
    @Test
    public void testPrevious() {
        list.previous();
        assertEquals(list.size(), 0);
        list.add("k");
        list.add("l");
        list.previous();
        assertEquals(list.getCurrent(), "k");

    }
    //-----------------------------------------------------------
    /**
     * Test method for add
     */
    @Test
    public void testAdd() {
        assertEquals(list.size(), 0);
        list.add("k");
        assertEquals(list.size(), 1);
        list.add("l");
        list.add("m");
        list.add("n");
        assertEquals(list.size(), 4);
        list.previous();
        list.add("ok");
        assertEquals(list.getCurrent(), "ok");
        list.next();
        assertEquals(list.getCurrent(), "k");
    }
    //-----------------------------------------------------------
    /**
     * Test method for getCurrent
     */
    @Test
    public void testGetCurrent() {
        Exception thrown = null;
        try {
            list.getCurrent();
        }
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);
        list.add("hello");
        list.add("hi");
        assertEquals(list.getCurrent(), "hi");
        list.next();
        assertEquals(list.getCurrent(), "hello");
    }
    //-----------------------------------------------------------
    /**
     * Test method for size
     */
    @Test
    public void testSize() {
        assertEquals(list.size(), 0);
        list.add("k");
        assertEquals(list.size(), 1);
        list.add("l");
        list.add("m");
        assertEquals(list.size(), 3);
    }
    //-----------------------------------------------------------
    /**
     * Test method for removeCurrent
     */
    @Test
    public void testRemoveCurrent() {
        Exception thrown = null;
        try {
            list.removeCurrent();
        }
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);
        list.add("k");
        assertEquals(list.removeCurrent(), "k");
        assertEquals(list.size(), 0);
        list.add("l");
        list.add("m");
        assertEquals(list.removeCurrent(), "m");
        assertEquals(list.size(), 1);
        assertEquals(list.removeCurrent(), "l");
        assertEquals(list.size(), 0);
    }
    //-----------------------------------------------------------
    /**
     * Test method for clear
     */
    @Test
    public void testClear() {
        list.clear();
        assertEquals(list.size(), 0);
        list.add("k");
        list.add("l");
        list.add("m");
        assertEquals(list.size(), 3);
        list.clear();
        assertEquals(list.size(), 0);

    }
    //-----------------------------------------------------------
    /**
     * Test method for toString()
     */
    @Test
    public void testToString() {
        assertEquals(list.toString(), "[]");
        list.add("string");
        list.add("to");
        list.add("method");
        list.add("test");
        assertEquals(list.toString(), "[test, method, to, string]");
    }
    //-----------------------------------------------------------
    /**
     * test method for iterator's has next
     */
    @Test
    public void testHasNext()
    {
        Iterator<String> it = list.iterator();
        assertEquals(0, list.size());
        it.next();
        assertFalse(it.hasNext());

        list.add("a");
        list.add("b");
        Iterator<String> it2 = list.iterator();
        assertTrue(it2.hasNext());
        assertEquals("b", list.getCurrent());

        list.add("c");
        list.add("d");
        Iterator<String> it3 = list.iterator();
        list.removeCurrent();
        assertTrue(it3.hasNext());
        assertEquals("c", list.getCurrent());

        list.clear();
        list.add("a");
        Iterator<String> it4 = list.iterator();
        assertTrue(it4.hasNext());
        assertEquals("a", list.getCurrent());
    }
    //--------------------------------------------------------------
    /**
     * test method for iterator's next
     */
    @Test
    public void testNextIt()
    {
        assertNull(list.iterator().next());
        list.add("a");
        Iterator<String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(list.getCurrent(), it.next());
        list.add("b");
        list.add("c");
        assertEquals("a", it.next());

    }
    //--------------------------------------------------------------
    /**
     * test method for iterator's remove
     */
    @Test
    public void testRemove()
    {
        Iterator<String> it = list.iterator();
        Exception thrown = null;
        try {
            it.remove();
        }
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof UnsupportedOperationException);
    }

}
