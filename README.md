# RecipeSearch
Searching recipes based on Ingredients and ranking based on healthiness

## What each file does

LoadingSolr -> loading the recipe into the solr core.  
SpringDemo ->  The backend for the search engine to get the requests.  

1\. Introduction

The number of recipes on the Internet is on the rise. Many of the recipes are in different languages and not in English. Hence searching for a particular recipe is becoming increasingly difficult. For instance "hong shao rou" is a famous Chinese dish which is also called Shanghai-Style Braised Pork Belly in English. This recipe cannot be searched be food.com unless you know the exact name of the recipe. But some ingredients used to recognize this recipe are "pork sugar sweet Chinese". This paper aims to create a search system where the user can search for recipes based on certain keywords associated with the recipe. The second part of this paper is to create a ranking system which boosts the healthiest recipe to the top. This includes defining the term "healthy". This paper also aims to also present an alternate re-ranking model to sort the recipes based on multiple parameters, namely the number of steps, the time required to prepare the recipe, the dietary score and the protein content in the recipe to keep the results relevant and also sorted. The third part of the paper proposes a real-time search feature for searching the recipe so the user can have immediate feedback and change the keyword input accordingly.
![](https://lh3.googleusercontent.com/LYn_fXet1YGJdiFO86CbZ0moTZfiS7ZYaZYMWXS9YghQRFznWxolzW2kNHjopvSQr5Norymo-KGhZpLpAF1ZGkkj7AR7M7arqtjRl7gsgjjnf9e9PRCO3TNvVxbwuabTSIVwQXe3)
