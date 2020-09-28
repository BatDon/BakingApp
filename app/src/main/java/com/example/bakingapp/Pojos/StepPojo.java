package com.example.bakingapp.Pojos;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepPojo implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public StepPojo() {
    }

    protected StepPojo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<StepPojo> CREATOR = new Parcelable.Creator<StepPojo>() {
        @Override
        public StepPojo createFromParcel(Parcel source) {
            return new StepPojo(source);
        }

        @Override
        public StepPojo[] newArray(int size) {
            return new StepPojo[size];
        }
    };
}




















/////////////////////////////////////////////////////////////////////////////





//@Entity(tableName = "steps")
//public class StepsPojo {
//
//    //serializedName is as found in GSON conversion as JSON
//
//    //can't be null because reference from parent in this case RecipesPojo
//
//    //name is as referenced in room database
////    private Object steps_id;
//    @SerializedName("id")
//    @ColumnInfo(name="id")
//    @PrimaryKey
//    @Nullable
//    @Expose
//    private Object steps_id;
//
//
//    @Expose
//    @SerializedName("shortDescription")
//    @Nullable
//    private Object shortDescription;
//    @Expose
//    @SerializedName("description")
//    @Nullable
//    private Object description;
//    @Expose
//    @SerializedName("videoURL")
//    @Nullable
//    private Object videoURL;
//    @Expose
//    @SerializedName("thumbnailURL")
//    @Nullable
//    private Object thumbnailURL;
//
////    public StepsPojo(){
////    }
////
////    public StepsPojo( @Nullable Object shortDescription, @Nullable Object description, @Nullable Object videoURL, @Nullable Object thumbnailURL) {
////        this.shortDescription = shortDescription;
////        this.description = description;
////        this.videoURL = videoURL;
////        this.thumbnailURL = thumbnailURL;
////    }
////
////    public StepsPojo(@Nullable Object steps_id, @Nullable Object shortDescription, @Nullable Object description, @Nullable Object videoURL, @Nullable Object thumbnailURL) {
////        this.steps_id = steps_id;
////        this.shortDescription = shortDescription;
////        this.description = description;
////        this.videoURL = videoURL;
////        this.thumbnailURL = thumbnailURL;
////    }
//
//    @ForeignKey
//            (entity = IngredientsPojo.class,
//                    parentColumns = "id",
//                    childColumns = "recipesPojoIdFromSteps")
//    private int recipesPojoIdFromSteps;
//
//    public Object getId() {
//        return steps_id;
//    }
//
//    public void setId(Object steps_id) {
//        this.steps_id = steps_id;
//    }
//
//    public Object getShortDescription() {
//        return shortDescription;
//    }
//
//    public void setShortDescription(Object shortDescription) {
//        this.shortDescription = shortDescription;
//    }
//
//    public Object getDescription() {
//        return description;
//    }
//
//    public void setDescription(Object description) {
//        this.description = description;
//    }
//
//    public Object getVideoURL() {
//        return videoURL;
//    }
//
//    public void setVideoURL(Object videoURL) {
//        this.videoURL = videoURL;
//    }
//
//    public Object getThumbnailURL() {
//        return thumbnailURL;
//    }
//
//    public void setThumbnailURL(Object thumbnailURL) {
//        this.thumbnailURL = thumbnailURL;
//    }
//
//    public int getRecipesPojoIdFromSteps() {
//        return recipesPojoIdFromSteps;
//    }
//
//    public void setRecipesPojoIdFromSteps(int recipesPojoIdFromSteps) {
//        this.recipesPojoIdFromSteps = recipesPojoIdFromSteps;
//    }
//
//    @Override
//    public String toString() {
//        return "StepsPojo{" +
//                "id=" + steps_id +
//                ", shortDescription='" + shortDescription + '\'' +
//                ", description='" + description + '\'' +
//                ", videoUrl=" + videoURL +
//                ", thumbnailUrl=" + thumbnailURL +
//                ", recipesPojoIdFromSteps=" + recipesPojoIdFromSteps +
//                '}';
//    }




    ////////////////////////////////////////////////////////////////////////////



//    @SerializedName("id")
//    @Expose
//    @Nullable
//    private int id;
//    @SerializedName("shortDescription")
//    @Expose
//    @Nullable
//    private String shortDescription;
//    @SerializedName("description")
//    @Expose
//    @Nullable
//    private String description;
//    @SerializedName("videoURL")
//    @Expose
//    @Nullable
//    private String videoURL;
//    @SerializedName("thumbnailURL")
//    @Expose
//    @Nullable
//    private String thumbnailURL;
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
//    public String getShortDescription() {
//        return shortDescription;
//    }
//
//    public void setShortDescription(String shortDescription) {
//        this.shortDescription = shortDescription;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getVideoURL() {
//        return videoURL;
//    }
//
//    public void setVideoURL(String videoURL) {
//        this.videoURL = videoURL;
//    }
//
//    public String getThumbnailURL() {
//        return thumbnailURL;
//    }
//
//    public void setThumbnailURL(String thumbnailURL) {
//        this.thumbnailURL = thumbnailURL;
//    }


