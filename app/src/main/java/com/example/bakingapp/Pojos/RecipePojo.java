package com.example.bakingapp.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



public class RecipePojo implements Parcelable {

    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<IngredientPojo> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<StepPojo> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientPojo> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientPojo> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepPojo> getSteps() {
        return steps;
    }

    public void setSteps(List<StepPojo> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    public RecipePojo() {
    }

    protected RecipePojo(Parcel parcel) {
        this.id = (Integer) parcel.readValue(Integer.class.getClassLoader());
        this.name = parcel.readString();
        this.ingredients = new ArrayList<>();
        parcel.readList(this.ingredients, IngredientPojo.class.getClassLoader());
        this.steps = new ArrayList<>();
        parcel.readList(this.steps, StepPojo.class.getClassLoader());
        this.servings = (Integer) parcel.readValue(Integer.class.getClassLoader());
        this.image = parcel.readString();
    }

    //Create a parcelable object in order to avoid Room, since Room can't store lists
    public static final Parcelable.Creator<RecipePojo> CREATOR = new Parcelable.Creator<RecipePojo>() {
        @Override
        public RecipePojo createFromParcel(Parcel source) {
            return new RecipePojo(source);
        }

        @Override
        public RecipePojo[] newArray(int size) {
            return new RecipePojo[size];
        }
    };
}




