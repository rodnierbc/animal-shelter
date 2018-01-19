package models;

import java.util.Objects;

public class Type {
    private int id;
    private String name;
    private String description;

    public Type(String name, String description){
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        if (id != type.id) return false;
        return name.equals(type.name);

    }

    @Override
    public int hashCode() {
         int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }
}
