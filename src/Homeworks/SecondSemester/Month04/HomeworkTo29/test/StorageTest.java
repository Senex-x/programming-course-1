package Homeworks.SecondSemester.Month04.HomeworkTo29.test;

import Homeworks.SecondSemester.Month04.HomeworkTo29.task.Item;
import Homeworks.SecondSemester.Month04.HomeworkTo29.task.Storage;
import Homeworks.SecondSemester.Month04.HomeworkTo29.task.Type;
import org.junit.*;

import static org.junit.Assert.*;

public class StorageTest {
    private Storage storage;

    @Before
    public void prepare() {
        storage = new Storage();
        storage.addItem("Bread");
        storage.addItem("Milk");
        storage.addItem("Grape");
    }

    @Test
    public void addTest() {
        storage.addItem("Bread");

        assertEquals(3, storage.getTypesCount());
        assertEquals(2, storage.getTypes().get(0).getCount());
    }

    @Test
    public void getTest() {
        storage.addItem("Bread");

        Type itemType = storage.getTypes().get(0);
        assertEquals("Bread", itemType.getName());

        Item item = itemType.getItems().get(0);
        assertNotNull(item);
        assertEquals(item.getName(), itemType.getName());
    }

    @Test
    public void failureTest() {
        Item item = storage.getItem("&@1//#$3");
        assertNull(item);
    }

    @Test
    public void deleteProductTest() {
        storage.addItem("Bread");
        Type itemType = storage.getTypes().get(0);
        Item item = itemType.getItems().get(0);
        itemType.deleteItem(item.getId());
        assertEquals(1, itemType.getCount());
    }
}

