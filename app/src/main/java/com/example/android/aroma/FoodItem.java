package com.example.android.aroma;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {

    private String itemName;
    private int itemId;
    private int servings;

    private String ingredientQuantity;
    private String measure, ingredientName;

    private String stepId;
    private String shortDescription, description, videoUrl;
    private String thumbnailUrl;

    public FoodItem(String itemName, int itemId, int servings) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.servings = servings;
    }

    public FoodItem(String ingredientQuantity, String measure, String ingredientName) {
        this.ingredientQuantity = ingredientQuantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public FoodItem(String stepId, String shortDescription, String description, String videUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected FoodItem(Parcel in) {
        itemName = in.readString();
        itemId = in.readInt();
        servings = in.readInt();
        ingredientQuantity = in.readString();
        measure = in.readString();
        ingredientName = in.readString();
        stepId = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(String ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeInt(itemId);
        parcel.writeInt(servings);
        parcel.writeString(ingredientQuantity);
        parcel.writeString(measure);
        parcel.writeString(ingredientName);
        parcel.writeString(stepId);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
