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

















////////////////////////////////////////////////////////


//@Entity(tableName = "recipes",
//foreignKeys = {@ForeignKey(entity = IngredientsPojo.class, parentColumns = {"id"}, childColumns = {"ingredients_id"}),
//        @ForeignKey(entity= StepsPojo.class, parentColumns = {"id"}, childColumns = {"steps_id"})},
//        indices = {@Index("ingredients_id"),@Index("steps_id")})
//@Entity(tableName = "recipes")
//public final class RecipesPojo {
//
////    @Expose
////    @SerializedName("id")
////    @PrimaryKey
////    @Nullable
//    @PrimaryKey
//    private int id;
////    @Expose
////    @SerializedName("name")
////    @Nullable
//    @Expose
//    private Object name;
////    @Expose
////    @SerializedName("ingredients")
//    @Nullable
//    @Ignore
//    private List<Object> ingredients=null;
////    @Expose
////    @SerializedName("steps")
//    @Nullable
//    @Ignore
//    private List<Object> steps=null;
////    @Expose
////    @SerializedName("servings")
//    @Nullable
//    private Object servings;
////    @Expose
////    @SerializedName("image")
//    @Nullable
//    private Object image;
//
////    public RecipesPojo(){
////    }
//
////    @Ignore
////    public RecipesPojo(@NonNull int id, @Nullable Object name, @Nullable List<Object> ingredients, @Nullable List<Object> steps, @Nullable Object servings, @Nullable Object image) {
////        this.id = id;
////        this.name = name;
////        this.ingredients = ingredients;
////        this.steps = steps;
////        this.servings = servings;
////        this.image = image;
////    }
////
////
////    public RecipesPojo(@Nullable int id, @Nullable Object name, @Nullable Object servings, @Nullable Object image) {
////        this.id = id;
////        this.name = name;
////        this.servings = servings;
////        this.image = image;
////    }
////
////    public RecipesPojo(RecipesDetailsPojo recipesDetailsPojo) {
////        this.id = recipesDetailsPojo.getRecipePojo().getId();
////        this.name = recipesDetailsPojo.getRecipePojo().getName();
////        this.ingredients = new ArrayList<Object>(recipesDetailsPojo.getIngredientsPojos());
////        this.steps = new ArrayList<Object>(recipesDetailsPojo.getStepsPojos());
////        this.servings = this.getServings();
////        this.image=this.getImage();
////    }
//
//
//
////    private List<IngredientsPojo> getIngredients(List<AuthorBookDetails> authorBookDetails) {
////        for (AuthorBookDetails details : authorBookDetails) {
////            Author author = details.getAuthor();
////            author.setBooks(details.getBooks());
////            this.setAuthor(details.getAuthor());
////        }
////        return this.authors;
////    }
//
////    @SerializedName("id")
////    @Expose
////    @Nullable
////    private Object id;
//
//
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Object getName() {
//        return name;
//    }
//
//    public void setName(Object name) {
//        this.name = name;
//    }
//
//    public List<Object> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(List<Object> ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public List<Object> getSteps() {
//        return steps;
//    }
//
//    public void setSteps(List<Object> steps) {
//        this.steps = steps;
//    }
//
//    public Object getServings() {
//        return servings;
//    }
//
//    public void setServings(Object servings) {
//        this.servings = servings;
//    }
//
//    public Object getImage() {
//        return image;
//    }
//
//    public void setImage(Object image) {
//        this.image = image;
//    }
//
//    @Override
//    public String toString() {
//        return "RecipesPojo{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", ingredients='" + ingredients + '\'' +
//                ", steps=" + steps +
//                ", servings=" + servings +
//                ", image=" + image +
//                '}';
//    }





//////////////////////////////////////////////////////////////////////////////////////





//    @SerializedName("id")
//    @Expose
//    @Nullable
//    private int id;
//    @SerializedName("name")
//    @Expose
//    @Nullable
//    private String name;
//    @SerializedName("ingredients")
//    @Expose
//    @Nullable
//    private List<IngredientsPojo> ingredients = null;
//    @SerializedName("steps")
//    @Expose
//    @Nullable
//    private List<StepsPojo> steps = null;
//    @SerializedName("servings")
//    @Expose
//    @Nullable
//    private int servings;
//    @SerializedName("image")
//    @Expose
//    @Nullable
//    private String image;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<IngredientsPojo> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(List<IngredientsPojo> ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public List<StepsPojo> getSteps() {
//        return steps;
//    }
//
//    public void setSteps(List<StepsPojo> steps) {
//        this.steps = steps;
//    }
//
//    public int getServings() {
//        return servings;
//    }
//
//    public void setServings(int servings) {
//        this.servings = servings;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }


