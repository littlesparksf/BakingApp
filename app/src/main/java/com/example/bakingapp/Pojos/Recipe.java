package com.example.bakingapp.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        ingredients = new ArrayList<>();
        in.readList(ingredients, Ingredient.class.getClassLoader());
        steps = new ArrayList<>();
        in.readList(steps, Step.class.getClassLoader());
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    private int id;

    private String name;

    private ArrayList<Ingredient> ingredients;

    private ArrayList<Step> steps;

    private int servings;

    private String image;


    /** No args constructor for use in serialization */
    public Recipe(){
    }

    /** Constructor */

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    /** Getters and Setters here */

    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public String getName() {return name;}
    public void setName (String name) {this.name = name;}

    public ArrayList<Ingredient> getIngredients() {return ingredients;}
    public void setIngredients (ArrayList<Ingredient> ingredients) {this.ingredients = ingredients;}

    public ArrayList<Step> getSteps() {return steps;}
    public void setSteps (ArrayList<Step> steps) {this.steps = steps;}

    public int getServings() {return servings;}
    public void setServings(int servings){this.servings = servings;}

    public String getImage() {return image;}
    public void setImage (String image) {this.image = image;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }
}