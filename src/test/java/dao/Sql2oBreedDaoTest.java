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


public class Sql2oBreedDaoTest {
    private Sql2oBreedDao breedDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/animalShelterDB.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        breedDao = new Sql2oBreedDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingBreedSetsId() throws Exception {
        Breed breed = setupNewBreed();
        int originalBreedId = breed.getId();
        breedDao.add(breed);
        assertNotEquals(originalBreedId, breed.getId());
    }
    @Test
    public void existingBreedCanBeFoundById() throws Exception {
        Breed breed = setupNewBreed();
        breedDao.add(breed); //add to dao (takes care of saving)
        Breed foundBreed = breedDao.findById(breed.getId()); //retrieve
        assertEquals(breed, foundBreed); //should be the same
    }
    @Test
    public void addedBreedsAreReturnedFromgetAll() throws Exception {
        Breed breed = setupNewBreed();
        breedDao.add(breed);
        assertEquals(1, breedDao.getAllByTypeId(1).size());
    }
    @Test
    public void updateChangesBreedContent() throws Exception {
        String initialDescription = "test 1";
        String initialName = "test 1";
        Breed breed = new Breed (initialName, initialDescription, 1);
        breedDao.add(breed);
        breedDao.update(breed.getId(),"test 2","test 2");
        Breed updatedBreed = breedDao.findById(breed.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedBreed.getDescription());
        assertNotEquals(initialName, updatedBreed.getName());
    }
    @Test
    public void deleteByIdDeletesCorrectBreed() throws Exception {
        Breed breed = setupNewBreed();
        breedDao.add(breed);
        breedDao.deleteById(breed.getId());
        assertEquals(0, breedDao.getAllByTypeId(1).size());
    }
    @Test
    public void clearAllBreeds() throws Exception {
        Breed breed1 = setupNewBreed();
        Breed breed2 = setupNewBreed();
        breedDao.add(breed1);
        breedDao.add(breed2);
        int daoSize = breedDao.getAllByTypeId(1).size();
        breedDao.clearAllBreedsByTypeId(1);
        assertTrue(daoSize > 0 && daoSize > breedDao.getAllByTypeId(1).size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }



    public Breed setupNewBreed(){
        return new Breed("breed1","my description", 1);
    }
}