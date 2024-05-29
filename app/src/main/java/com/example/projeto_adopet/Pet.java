package com.example.projeto_adopet;

public class Pet {
    private int imageResId;
    private String name;
    private String age;
    private String breed;
    private String color; // Adicione este atributo

    public Pet(int imageResId, String name, String age, String breed, String color) {
        this.imageResId = imageResId;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.color = color;
    }

    public Pet(int icPetDog2, String bobby, String age, String srd) {
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }
}

