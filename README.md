# BakingApp

## Overview

App allows users to select a recipe whose data is obtained requesting a JSON file using Retrofit and logging interceptor. The main activity has a fragment that shows
a RecyclerView of recipes. is clicked and a new activity displays one fragment if it's a cell phone or two fragments if it's a tablet. One fragment's recyclerview 
contains the ingredients. The same fragment has another recyclerview that displays the recipe's steps. If a recipe step is clicked an activity is opened which
contains an Exoplayer fragment, which shows the step. Next and Prevous buttons can also control which step is shown. Screen rotations are supported as well as different 
screen sizes. A widget is shown to show chosen recipe's ingredients.

## Libraries
- Exoplayer
- Picasso
- Timber
- HTTP Logging Interceptor
- ViewModel
- Lifecycle
- Gson
- Material Design
- Espresso libraries

![](app/src/main/java/com/example/bakingapp/ScreenshotsForGithub/cellphone_portrait_recipes.png) | ![](app/src/main/java/com/example/bakingapp/ScreenshotsForGithub/cellphone_portrait_ingredients.png) | ![](app/src/main/java/com/example/bakingapp/ScreenshotsForGithub/cellphone_portrait_exoplayer.png) 

![](app/src/main/java/com/example/bakingapp/ScreenshotsForGithub/tablet_landscape_recipes.png) | ![](app/src/main/java/com/example/bakingapp/ScreenshotsForGithub/tablet_landscape_ingredients_exoplayer.png)
