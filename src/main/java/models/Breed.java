package models;

public class Breed {
    private int id;
    private String name;
    private String description;
    private int typeId;

    public Breed(String name, String description, int typeId){
        this.name = name;
        this.description = description;
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    public int getId(){
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        if (id != breed.id) return false;
        if (typeId != breed.typeId) return false;
        return name.equals(breed.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        result = 31 * result + typeId;
        return result;
    }
}
