package dao;

import models.Animal;
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


import static org.junit.Assert.*;

public class Sql2oAnimalDaoTest {

    private Sql2oTypeDao typeDao;
    private Sql2oBreedDao breedDao;
    private Sql2oAnimalDao animalDao;
    private Connection conn;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/animalShelterDB.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        typeDao = new Sql2oTypeDao(sql2o);
        breedDao =new Sql2oBreedDao(sql2o);
        animalDao = new Sql2oAnimalDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingAnimalSetsId() throws Exception {
        Animal animal = setupNewAnimal();
        int originalAnimalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(originalAnimalId, animal.getId());
    }

    @Test
    public void existingAnimalsCanBeFoundById() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal);
        Animal foundAnimal = animalDao.findById(animal.getId()); //retrieve
        assertEquals(animal, foundAnimal); //should be the same
    }
    @Test
    public void addedAnimalsAreReturnedFromgetAll() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal);
        assertEquals(1, animalDao.getAll().size());
    }
    @Test
    public void updateChangesAnimalContent() throws Exception {
        String initialName = "animal 1";
        boolean initialGender = true;
        String initialAdmittanceDate = "2000-01-01";
        int initialTypeId = 1;
        int initialBreedId = 1;
        Animal animal = new Animal (initialName, initialGender, initialAdmittanceDate, initialTypeId, initialBreedId);
        animalDao.add(animal);
        animalDao.update(animal.getId(),"animal 2",false, "2000-02-02", 2, 2);
        Animal updateAnimal = animalDao.findById(animal.getId());
        assertNotEquals(initialName, updateAnimal.getName());
        assertNotEquals(initialGender, updateAnimal.getGender());
        assertNotEquals(initialAdmittanceDate, updateAnimal.getAdmittanceDate());
        assertNotEquals(initialTypeId, updateAnimal.getTypeId());
        assertNotEquals(initialBreedId, updateAnimal.getBreedId());
    }
    @Test
    public void deleteByIdDeletesCorrectAnimal() throws Exception {
        Animal animal = setupNewAnimal();
        animalDao.add(animal);
        animalDao.deleteById(animal.getId());
        assertEquals(0, breedDao.getAll().size());
    }
    @Test
    public void clearAllAnimals() throws Exception {
        Animal animal1 = setupNewAnimal();
        Animal animal2 = setupNewAnimal();
        animalDao.add(animal1);
        animalDao.add(animal2);
        int daoSize = animalDao.getAll().size();
        animalDao.clearAllAnimals();
        assertTrue(daoSize > 0 && daoSize > animalDao.getAll().size());
    }


    private Animal setupNewAnimal() {
        return new Animal("animal",true, "2013-08-08", 1, 1);
    }


}