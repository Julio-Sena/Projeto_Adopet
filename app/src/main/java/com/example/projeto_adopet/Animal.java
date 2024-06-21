package com.example.projeto_adopet;

public class Animal {
    private int adopet;
    private String name;
    private String age;
    private String breed;
    private String color; // Adicione este atributo

    public Animal(int adopet, String name, String age, String breed, String color) {
        this.adopet = adopet;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.color = color;
    }

    public Animal(int adopet, String bobby, String age, String srd) {
    }

    public Animal(String nome, String raca, String genero, String tamanho, String peso, String idade, String tipo) {

    }

    public int getAdopet() {
        return adopet;
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

