package com.example.bakingapp.Pojos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;






import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientPojo implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public IngredientPojo() {
    }

    protected IngredientPojo(Parcel in) {
        this.quantity = (Double) in.readValue(Double.class.getClassLoader());
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<IngredientPojo> CREATOR = new Parcelable.Creator<IngredientPojo>() {
        @Override
        public IngredientPojo createFromParcel(Parcel source) {
            return new IngredientPojo(source);
        }

        @Override
        public IngredientPojo[] newArray(int size) {
            return new IngredientPojo[size];
        }
    };
}




///////////////////////////////////////////////////////////////////////////








//@Entity(tableName = "ingredients")
//public class IngredientsPojo {
//
//    //RecipesPojo is the parent
////    @Embedded
////    private RecipesPojo recipesPojo;
//
////
////    @Relation(parentColumn = "id", entityColumn = "recipes_id")
//
//    //id doesn't get serialized or deserialized in GSON
//    @NonNull
////    @Expose (serialize = false, deserialize = false)
//    @Ignore
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name="id")
////    private Object ingredients_id;
//    private int id;
//
//    @Expose
//    @SerializedName("quantity")
//    private Double quantity;
//    @Expose
//    @SerializedName("measure")
//    @Nullable
//    private Object measure;
//    @Expose
//    @SerializedName("ingredient")
//    @Nullable
//    private Object ingredient;
//
//
//    public IngredientsPojo(){
//    }
//
//    //Room needs constructor but we are autogenerating key so not needed in constructor
////    public IngredientsPojo(@Nullable Object quantity, @Nullable Object measure, @Nullable Object ingredient) {
////        this.quantity = quantity;
////        this.measure = measure;
////        this.ingredient = ingredient;
////    }
////
////    @Ignore
////    public IngredientsPojo(@NonNull int id, @Nullable Object quantity, @Nullable Object measure, @Nullable Object ingredient) {
////        this.id=id;
////        this.quantity = quantity;
////        this.measure = measure;
////        this.ingredient = ingredient;
////    }
//
//
//
////    @Ignore
////    public IngredientsPojo(int id, @Nullable Object quantity, @Nullable Object measure, @Nullable Object ingredient) {
////        this.id=id;
////        this.quantity = quantity;
////        this.measure = measure;
////        this.ingredient = ingredient;
////    }
//
//
//    @ForeignKey
//            (entity = IngredientsPojo.class,
//                    parentColumns = "id",
//                    childColumns = "recipesPojoIdFromIngredients")
//    private int recipesPojoIdFromIngredients;
//
//
//
////    public Object getId() {
////        return ingredients_id;
////    }
////
////    public void setId(Object ingredients_id) {
////        this.ingredients_id = ingredients_id;
////    }
//    public int getId() {
//    return id;
//}
//
//    public void setId(int ingredients_id) {
//        this.id = id;
//    }
//
//    public Double getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Double quantity) {
//        this.quantity = quantity;
//    }
//
//    public Object getMeasure() {
//        return measure;
//    }
//
//    public void setMeasure(Object measure) {
//        this.measure = measure;
//    }
//
//    public Object getIngredient() {
//        return ingredient;
//    }
//
//    public void setIngredient(Object ingredient) {
//        this.ingredient = ingredient;
//    }
//
//    public Object getRecipeId() {
//        return recipesPojoIdFromIngredients;
//    }
//
//    public void setRecipeId(int recipeId) {
//        this.recipesPojoIdFromIngredients = recipeId;
//    }
//
//    @Override
//    public String toString() {
//        return "IngredientsPojo{" +
//                "id=" + id +
//                ", quantity='" + quantity + '\'' +
//                ", measure='" + measure + '\'' +
//                ", ingredient=" + ingredient +
//                ", recipesPojoIdFromIngredients=" + recipesPojoIdFromIngredients +
//                '}';
//    }



    ///////////////////////////////////////////////////////////////////////////////////////////








//    @SerializedName("quantity")
//    @Expose
//    @Nullable
//    private int quantity;
//    @SerializedName("measure")
//    @Expose
//    @Nullable
//    private String measure;
//    @SerializedName("ingredient")
//    @Expose
//    @Nullable
//    private String ingredient;
//
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(int quantity) {
//            this.quantity = quantity;
//        }
//
//        public String getMeasure() {
//            return measure;
//        }
//
//        public void setMeasure(String measure) {
//            this.measure = measure;
//        }
//
//        public String getIngredient() {
//            return ingredient;
//        }
//
//        public void setIngredient(String ingredient) {
//            this.ingredient = ingredient;
//        }



