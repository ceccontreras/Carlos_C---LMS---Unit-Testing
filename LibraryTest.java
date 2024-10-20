import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        // Adding some books to the library for testing purposes
        library.addBook(new Book(1, "Book One", "Author A", "111"));
        library.addBook(new Book(2, "Book Two", "Author B", "222"));
        library.addBook(new Book(3, "Book Three", "Author C", "333"));
    }

    @Test
    void addBooksFromFile() throws IOException {
        int initialSize = library.listBooks();
        // Assuming the file contains valid book entries in the format: ID, Title, Author, Barcode
        String filePath = "path/to/your/testfile.txt";
        library.addBooksFromFile(filePath);
        assertTrue(library.listBooks() > initialSize, "Books should be added from the file.");
    }

    @Test
    void removeBookById() {
        assertTrue(library.removeBookById(001), "Book with ID 001 should be removed.");
        assertFalse(library.removeBookById(999), "Non-existent book with ID 999 should not be found.");
    }

    @Test
    void removeBookByTitle() {
        assertTrue(library.removeBookByTitle("Book Two"), "Book titled 'Book Two' should be removed.");
        assertFalse(library.removeBookByTitle("Non-existent Book"), "Book titled 'Non-existent Book' should not be found.");
    }

    @Test
    void removeBookByBarcode() {
        assertTrue(library.removeBookByBarcode("111"), "Book with barcode '111' should be removed.");
        assertFalse(library.removeBookByBarcode("999"), "Non-existent book with barcode '999' should not be found.");
    }

    @Test
    void checkOutBook() {
        assertTrue(library.checkOutBook("Book Three"), "Book titled 'Book Three' should be checked out.");
        Book book = library.getBookByTitle("Book Three");
        assertEquals("checked out", book.getStatus(), "Book status should be 'checked out'.");
        assertNotNull(book.getDueDate(), "Due date should not be null after checking out.");
    }

    @Test
    void checkInBook() {
        library.checkOutBook("Book One"); // First check out the book
        assertTrue(library.checkInBook("Book One"), "Book titled 'Book One' should be checked in.");
        Book book = library.getBookByTitle("Book One");
        assertEquals("checked in", book.getStatus(), "Book status should be 'checked in'.");
        assertNull(book.getDueDate(), "Due date should be null after checking in.");
    }

    @Test
    void listBooks() {
        // Assuming listBooks() prints the list of books to the console
        assertEquals(3, library.listBooks(), "Library should initially contain 3 books.");
    }
}
