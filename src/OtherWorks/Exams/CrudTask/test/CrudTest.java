package OtherWorks.Exams.CrudTask.test;

import OtherWorks.Exams.CrudTask.crud.Crud;
import OtherWorks.Exams.CrudTask.crud.TestObject;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CrudTest {
    private Crud<TestObject> crud;

    private void fillCrud(int amount) {
        for (int i = 0; i < amount; i++) {
            crud.create(new TestObject(i, ""));
        }
    }

    @Before
    public void prepare() {
        crud = new Crud<>();
        fillCrud(10);
    }

    @Test
    public void createTest() {
        int expectedSize = crud.getCount() + 1;
        crud.create(new TestObject(crud.getCount(), ""));
        assertEquals(crud.getCount(), expectedSize);

        assertThrows(
                IllegalArgumentException.class,
                () -> crud.create(new TestObject(crud.getCount() - 1, "")));
    }

    @Test
    public void readTest() {
        assertEquals(crud.read(0).getValue(), 0);

        assertThrows(
                NoSuchElementException.class,
                () -> crud.read(crud.getCount()));
    }

    @Test
    public void updateTest() {
        crud.update(new TestObject(0, "meta"));
        assertEquals(crud.read(0).getMeta(), "meta");

        assertThrows(
                NoSuchElementException.class,
                () -> crud.update(new TestObject(crud.getCount(), "")));
    }

    @Test
    public void deleteTest() {
        int expectedSize = crud.getCount() - 1;
        crud.delete(new TestObject(crud.getCount() - 1, ""));
        assertEquals(crud.getCount(), expectedSize);

        assertThrows(
                NoSuchElementException.class,
                () -> crud.delete(new TestObject(crud.getCount(), "")));
    }
}
