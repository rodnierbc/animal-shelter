package models;
import java.time.LocalDate;
import java.util.Objects;

public class Animal {
    private int id;
    private String name;
    private boolean gender;
    private String admittanceDate;
    private int typeId;
    private int breedId;

    public Animal(String name, boolean gender, String admittanceDate, int typeId, int breedId){
        this.name = name;
        this.gender = gender;
        this.admittanceDate = admittanceDate;
        this.typeId = typeId;
        this.breedId = breedId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAdmittanceDate() {
        return admittanceDate;
    }

    public void setAdmittanceDate(String admittanceDate) {
        this.admittanceDate = admittanceDate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;

        if(breedId != animal.breedId) return false;
        if(typeId != animal.typeId) return false;
        if(gender != animal.gender) return false;
        if(id != animal.id) return false;

        return name.equals(animal.name) &&
                admittanceDate.equals(animal.admittanceDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = admittanceDate.hashCode();
        result = 31 * (gender ? 0 : 1);
        result = 31 * result + id;
        result = 31 * result + typeId;
        result = 31 * result + breedId;
        return result;
    }
}
