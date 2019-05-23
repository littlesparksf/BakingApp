package com.example.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    @SerializedName("steps")
    private ArrayList<Step> steps = new ArrayList<Step>();

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;

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

    // Parceling part

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        if(in.readByte () == 0x01) {
            ingredients = new ArrayList<>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        }
        if(in.readByte() == 0x01) {
            steps = new ArrayList<>();
            in.readList(steps, Step.class.getClassLoader());
        }
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        }else{
            dest.writeByte((byte)(0x00));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        }else{
            dest.writeByte((byte)(0x00));
            dest.writeList(steps);
        }
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }
}