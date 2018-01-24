package dao;

import models.Breed;
import models.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.*;

public class Sql2oTypeDaoTest {

    private Sql2oTypeDao typeDao;
    private Sql2oBreedDao breedDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/animalShelterDB.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        typeDao = new Sql2oTypeDao(sql2o);
        breedDao =new Sql2oBreedDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingTypeSetsId() throws Exception {
        Type type = setupNewType();
        int originalTypeId = type.getId();
        typeDao.add(type);
        assertNotEquals(originalTypeId, type.getId());
    }
    @Test
    public void existingTypesCanBeFoundById() throws Exception {
        Type type = setupNewType();
        typeDao.add(type);
        Type foundType = typeDao.findById(type.getId());
        assertEquals(type, foundType);
    }
    @Test
    public void addedTypesAreReturnedFromgetAll() throws Exception {
        Type type = setupNewType();
        typeDao.add(type);
        assertEquals(1, typeDao.getAll().size());
    }
    @Test
    public void noTypesReturnsEmptyList() throws Exception {
        assertEquals(0, typeDao.getAll().size());
    }
    @Test
    public void updateChangesTypeContent() throws Exception {
        String initialName = "dog";
        String initialDescription = "description";
        Type type = new Type (initialName, "my description");
        typeDao.add(type);

        typeDao.update(type.getId(),"cat", "new description");
        Type updatedType = typeDao.findById(type.getId());
        assertNotEquals(initialName, updatedType.getName());
        assertNotEquals(initialDescription, updatedType.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectTypeAndBreed() throws Exception {
        Type type = setupNewType();
        Breed breed = new Breed("breed","breed",1);
        typeDao.add(type);
        breedDao.add(breed);
        typeDao.deleteById(type.getId());
        assertEquals(0, typeDao.getAll().size());
        assertEquals(0, breedDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll(){
        Type type1 = setupNewType();
        Type type2 = setupNewType();
        typeDao.add(type1);
        typeDao.add(type2);
        typeDao.clearAllTypes();
        assertEquals(0,typeDao.getAll().size());
    }

    public Type setupNewType(){
        return new Type("dog","my description");
    }
}