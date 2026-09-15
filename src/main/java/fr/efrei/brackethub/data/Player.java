package fr.efrei.brackethub.data;
import jakarta.persistence.*;

import java.lang.reflect.GenericDeclaration;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    @ManyToMany(mappedBy = "players")
    private List<Tournament> tournaments=new ArrayList<>();

    public Player(){
    }
    public Player(String first_name,String last_name,int age) {
        this.first_name = first_name;
        this.last_name=last_name;
        this.age= age;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
