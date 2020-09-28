package com.example.bakingapp.ViewModels;

//import com.example.bakingapp.Pojos.CompleteRecipes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipeViewModel extends AndroidViewModel {
    public MutableLiveData<Integer> recipeNumberLiveData;
    public MutableLiveData<Integer> stepNumberLiveData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        recipeNumberLiveData.setValue(0);
        stepNumberLiveData.setValue(0);

//        context=getApplication();
//        Result result=new Result("releaseData");
//        List<Result> resultList=new ArrayList<Result>();
//        resultList.add(result);
//        liveDataResultList.setValue(resultList);

    }

    public MutableLiveData<Integer> getRecipeNumberLiveData() {
        return recipeNumberLiveData;
    }

    public void setRecipeNumberLiveData(int recipeNumberLiveData) {
        this.recipeNumberLiveData.postValue(recipeNumberLiveData);
    }

    public MutableLiveData<Integer> getStepNumberLiveData() {
        return stepNumberLiveData;
    }

    public void setStepNumberLiveData(int stepNumberLiveData) {
        this.stepNumberLiveData.postValue(stepNumberLiveData);
    }
}


//public class RecipeViewModel extends ViewModel {
//    public LiveData<List<RecipesPojo>> recipes;
//    public LiveData<List<CompleteRecipes>> completeRecipes;
//
//    RecipeViewModel(@NonNull RecipeRepository repository) {
//        this.recipes = repository.getRecipes();
//        this.completeRecipes = Transformations.map(repository.getCompleteRecipes(), items -> {
//            //List<CompleteRecipes> removeItems = new ArrayList<>();
//
//
//            for (int i = 0; i < items.size(); i++) {
//
//            }
//            for (CompleteRecipes item:items) {
//                List<RecipesPojo> recipesPojos=item.getRecipesPojos();
//                recipesPojos.get(0)
//                if (item.getRecipesPojos().isEmpty()) {
//                    //removeItems.add(item);
//                }
//            }
////            items.removeAll(removeItems);
////            return items;
//        });
//    }
//}
