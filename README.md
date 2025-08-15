# <img src="https://github.com/user-attachments/assets/13954c6c-ef15-4e97-ba6c-339fd30a4f25" alt="CineVerse Logo" width="50" /> CineVerse – Where Every Story Finds Its Star
![Image](https://github.com/user-attachments/assets/991d2735-8995-471b-a293-c967f5b30f3f)

## ![Image](https://github.com/user-attachments/assets/13954c6c-ef15-4e97-ba6c-339fd30a4f25) Download Cineverse APK

-- The App still in Development Process --
But you can see the last updated version here:

[⬇️ Download Cineverse APK ](https://github.com/Bilalazam26/CineVerse/releases/download/V1.0-alpha/CineVerse-V1.0-alpha.apk)

---

## 📸 Screen Shots
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/de3d7001-fad8-4a8c-a5dd-59429f30cbf5" width="200"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/676a8f5d-5a58-482d-b643-c88856a30284" width="200"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/f46b291c-f2aa-4751-923c-2067f22cb1cf" width="200"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/14d1df6c-ea56-4e6f-b1de-5185cce9e142" width="200"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/85301070-8f32-4f51-bba2-b53f52f1bd36" width="200"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/4cbec1b9-258e-4913-9904-fb44ac0a0ea7" width="200"/>
    </td>
  </tr>
</table>

---

## 🧠 Key Concepts

This app is a practical demonstration of:

- Jetpack Compose UI
- CI/CD
- Modularization
- Clean MVI Architecture
- OO A&D + SOLID Principles
- OnDevice Machine Learning
- Koin & Hilt & Dagger for Dependency Injection
- Ktor & Retrofit for Networking
- Room for Caching
- Coil
- Paging3 for Pagination

---

## 📱 Features

- onboarding screen to appear only the first time I launch the app.
- home screen displaying Collections of Movies and Series.
- Explore Screen displaying Collections of Movies and Series filtered by categories.
- Search Screen to enable user to search for his favourite Actors, Movies, Series ..etc.
- Voice Recognition to allow user to search by voice.
- Authentication
- New Library for Previewing Images and bluring Images that are not Suitable to the Islamic Culture using ML Models.

---

## 🛠️ Tech Stack

| Tech                    | Usage                         |
|-------------------------|-------------------------------|
| **Kotlin**              | Programming Language          |
| **Jetpack Compose**     | Declarative UI Framework      |
| **MVI**                 | Architecture Pattern          |
| **Koin**                | Dependency Injection          |
| **Hilt**                | Dependency Injection          |
| **Dagger**              | Dependency Injection          |
| **Ktor**                | Integrating with Backend      |
| **Retrofit**            | Integrating with Backend      |
| **Paging3**             | Pagination                    |
| **Room**                | Caching and local storage     |


---
## 🧩 Architecture
<pre>
├── api
│   ├── authentication
│   │   └── AuthenticationApi.kt
│   ├── details
│   │   └── DetailsApi.kt
│   ├── explore
│   │   └── ExploreApi.kt
│   ├── home
│   │   └── HomeApi.kt
│   ├── match
│   │   └── MatchApi.kt
│   └── profile
│       └── ProfileApi.kt
├── app
│   ├── release
│   └── src
│       └── main
│           ├── java
│              └── com
│                  └── giraffe
│                      └── cineverseapp
│                          ├── CineVerseApp.kt
│                          ├── data
│                          │   ├── database
│                          │   │   ├── CineVerseDatabase.kt
│                          │   │   └── converter
│                          │   │       └── Converters.kt
│                          │   ├── network
│                          │   │   ├── CreateRetrofitClient.kt
│                          │   │   └── SessionIdInterceptor.kt
│                          │   ├── preference
│                          │   │   └── DataStorePreferences.kt
│                          │   └── util
│                          │       └── Converters.kt
│                          ├── di
│                          │   ├── databaseModule.kt
│                          │   ├── featureApiModule.kt
│                          │   ├── localDataSourceModule.kt
│                          │   ├── networkModule.kt
│                          │   └── repositoryModule.kt
│                          ├── MainActivity.kt
│                          └── worker
│                              └── CacheCleanupWorker.kt
├── datasource
│   ├── local
│   │   ├── explore
│   │   ├── media
│   │   │   └── src
│   │   │       └── main
│   │   │           └── java
│   │   │               └── com
│   │   │                   └── giraffe
│   │   │                       └── media
│   │   │                           ├── explore
│   │   │                           │   ├── cleaner
│   │   │                           │   │   ├── KeywordsCacheCleanerImp.kt
│   │   │                           │   │   └── KeywordsCacheCleaner.kt
│   │   │                           │   ├── dao
│   │   │                           │   │   └── ExploreSearchKeywordDao.kt
│   │   │                           │   └── LocalExploreDataSourceImpl.kt
│   │   │                           ├── movie
│   │   │                           │   ├── cleaner
│   │   │                           │   │   ├── MovieCacheCleanerImp.kt
│   │   │                           │   │   └── MovieCacheCleaner.kt
│   │   │                           │   ├── dao
│   │   │                           │   │   └── MovieDao.kt
│   │   │                           │   └── MovieLocalDataSourceImp.kt
│   │   │                           ├── person
│   │   │                           │   ├── cleaner
│   │   │                           │   │   ├── PersonCacheCleanerImp.kt
│   │   │                           │   │   └── PersonCacheCleaner.kt
│   │   │                           │   ├── dao
│   │   │                           │   │   └── PersonDao.kt
│   │   │                           │   ├── PersonLocalDataSourceImp.kt
│   │   │                           │   └── relations
│   │   │                           │       ├── CrossReferenceConstants.kt
│   │   │                           │       └── CrossReferenceTables.kt
│   │   │                           ├── series
│   │   │                           │   ├── cleaner
│   │   │                           │   │   ├── SeriesCacheCleanerImp.kt
│   │   │                           │   │   └── SeriesCacheCleaner.kt
│   │   │                           │   ├── dao
│   │   │                           │   │   └── SeriesDao.kt
│   │   │                           │   └── SeriesRoomLocalDateSource.kt
│   │   │                           └── util
│   │   │                               └── SafeCall.kt
│   │   ├── movie
│   │   ├── person
│   │   ├── review
│   │   ├── series
│   │   └── user
│   │       └── src
│   │           └── main
│   │               └── java
│   │                   └── com
│   │                       └── giraffe
│   │                           └── user
│   │                               ├── AuthenticationLocalDataSourceImpl.kt
│   │                               ├── datastore
│   │                               │   ├── AuthenticationDatastore.kt
│   │                               │   └── OnboardingDatastore.kt
│   │                               ├── OnboardingLocalDataSourceImpl.kt
│   │                               └── util
│   │                                   └── SafeCall.kt
│   └── remote
│       ├── explore
│       │   └── src
│       │       └── main
│       │           └── java
│       │               └── com
│       │                   └── giraffe
│       │                       └── explore
│       │                           └── model
│       │                               └── SearchKeywordResponse.kt
│       ├── media
│       │   └── src
│       │       └── main
│       │           └── java
│       │               └── com
│       │                   └── giraffe
│       │                       └── media
│       │                           ├── explore
│       │                           │   ├── response
│       │                           │   │   └── SearchKeywordResponse.kt
│       │                           │   └── retrofit
│       │                           │       ├── ExploreApiServiceRetrofit.kt
│       │                           │       └── ExploreRemoteDataSourceImplRetrofit.kt
│       │                           ├── movie
│       │                           │   ├── response
│       │                           │   │   ├── GenreResponse.kt
│       │                           │   │   └── MoviesListResponse.kt
│       │                           │   └── retrofit
│       │                           │       ├── MoviesApiServiceRetrofit.kt
│       │                           │       └── MoviesRemoteDataSourceImplRetrofit.kt
│       │                           ├── person
│       │                           │   ├── response
│       │                           │   │   ├── PersonCreditsResponse.kt
│       │                           │   │   └── SearchPersonResponse.kt
│       │                           │   └── retrofit
│       │                           │       ├── PersonApiServiceRetrofit.kt
│       │                           │       └── PersonRemoteDataSourceImpRetrofit.kt
│       │                           ├── response
│       │                           │   ├── AllReviewsResponse.kt
│       │                           │   └── TrailerResponse.kt
│       │                           ├── series
│       │                           │   ├── response
│       │                           │   │   ├── GenresResponse.kt
│       │                           │   │   └── SeriesResponse.kt
│       │                           │   └── retrofit
│       │                           │       ├── SeriesApiServiceRetrofit.kt
│       │                           │       └── SeriesRemoteRetrofitDataSourceImp.kt
│       │                           └── util
│       │                               ├── ApiErrorResponse.kt
│       │                               ├── AuthInterceptor.kt
│       │                               ├── LanguageInterceptor.kt
│       │                               ├── NetworkConnectionInterceptor.kt
│       │                               ├── NetworkConstants.kt
│       │                               └── RetrofitRequestBuilder.kt
│       └── user
│           └── src
│               └── main
│                   └── java
│                       └── com
│                           └── giraffe
│                               └── user
│                                   ├── dto
│                                   │   ├── RequestTokenResponse.kt
│                                   │   ├── SessionRequestBody.kt
│                                   │   ├── SessionResponse.kt
│                                   │   └── TokenValidationBody.kt
│                                   ├── retrofit
│                                   │   ├── AuthenticationRemoteDataSourceImpRetrofit.kt
│                                   │   ├── UserApiServiceRetrofit.kt
│                                   │   └── UserRemoteDataSourceImplRetrofit.kt
│                                   └── util
│                                       └── RetrofitUserRequestBuilder.kt
├── designsystem
│   └── src
│       └── main
│           ├── java
│              └── com
│                  └── giraffe
│                      └── designsystem
│                          ├── color
│                          │   ├── CineVerseColors.kt
│                          │   ├── darkThemeColor.kt
│                          │   └── lightThemeColor.kt
│                          ├── composable
│                          │   ├── AppBar.kt
│                          │   ├── BaseBottomSheet.kt
│                          │   ├── BuildingBlock.kt
│                          │   ├── button_type
│                          │   │   ├── PrimaryButton.kt
│                          │   │   ├── SecondaryButton.kt
│                          │   │   └── TertiaryButton.kt
│                          │   ├── Chip.kt
│                          │   ├── CinePreview.kt
│                          │   ├── CircleImage.kt
│                          │   ├── CollectionItem.kt
│                          │   ├── CTACard.kt
│                          │   ├── custom
│                          │   │   ├── Button.kt
│                          │   │   ├── Card.kt
│                          │   │   ├── Icon.kt
│                          │   │   └── Text.kt
│                          │   ├── DefaultTextField.kt
│                          │   ├── FloatingActionButton.kt
│                          │   ├── InfoCard.kt
│                          │   ├── InfoSection.kt
│                          │   ├── MessageInfoBox.kt
│                          │   ├── navbar
│                          │   │   ├── BottomNavigationBar.kt
│                          │   │   └── NavigationBarItem.kt
│                          │   ├── NoInternetScreen.kt
│                          │   ├── PosterItemHorizontal.kt
│                          │   ├── PosterItemVertically.kt
│                          │   ├── PosterListSection.kt
│                          │   ├── progress.kt
│                          │   ├── Rating.kt
│                          │   ├── ReadMoreText.kt
│                          │   ├── SectionTitle.kt
│                          │   ├── Switch.kt
│                          │   ├── tabs.kt
│                          │   └── ViewToggle.kt
│                          ├── icon
│                          │   ├── Bold.kt
│                          │   ├── CineVerseIcons.kt
│                          │   ├── Colored.kt
│                          │   ├── DueTone.kt
│                          │   └── Outline.kt
│                          ├── modifier
│                          │   └── noHoverClickable.kt
│                          ├── radius
│                          │   └── CineVerseRadius.kt
│                          ├── text_style
│                          │   ├── CineVerseTextStyle.kt
│                          │   └── defaultTextStyle.kt
│                          ├── theme
│                          │   ├── CineVerseTheme.kt
│                          │   └── Theme.kt
│                          └── uimodel
│                              └── Poster.kt
├── domain
│   ├── media
│   │   └── src
│   │       └── main
│   │           └── java
│   │               └── com
│   │                   └── giraffe
│   │                       └── media
│   │                           ├── collections
│   │                           │   ├── entity
│   │                           │   │   └── Collection.kt
│   │                           │   ├── repository
│   │                           │   │   └── CollectionsRepository.kt
│   │                           │   └── usecase
│   │                           │       ├── AddCollectionUseCase.kt
│   │                           │       ├── AddMovieToCollectionUseCase.kt
│   │                           │       ├── AddSeriesToCollectionUseCase.kt
│   │                           │       ├── ClearCollectionUseCase.kt
│   │                           │       ├── GetCollectionDetailsUseCase.kt
│   │                           │       ├── GetCollectionMoviesUseCase.kt
│   │                           │       ├── GetCollectionSeriesUseCase.kt
│   │                           │       ├── GetCollectionsUseCase.kt
│   │                           │       ├── RemoveCollectionUseCase.kt
│   │                           │       ├── RemoveMovieFromCollectionUseCase.kt
│   │                           │       └── RemoveSeriesFromCollectionUseCase.kt
│   │                           ├── entity
│   │                           │   ├── Genre.kt
│   │                           │   └── Review.kt
│   │                           ├── exception
│   │                           │   └── MediaException.kt
│   │                           ├── explore
│   │                           │   ├── entity
│   │                           │   │   └── SearchKeyword.kt
│   │                           │   ├── repository
│   │                           │   │   └── ExploreRepository.kt
│   │                           │   └── usecase
│   │                           │       ├── AddSearchKeywordUseCase.kt
│   │                           │       ├── ClearSearchHistoryUseCase.kt
│   │                           │       ├── DeleteKeywordUseCase.kt
│   │                           │       └── GetSearchKeywordsUseCase.kt
│   │                           ├── home
│   │                           │   └── entity
│   │                           │       ├── FeaturedCollection.kt
│   │                           │       └── YourCollection.kt
│   │                           ├── movies
│   │                           │   ├── entity
│   │                           │   │   └── Movie.kt
│   │                           │   ├── repository
│   │                           │   │   └── MoviesRepository.kt
│   │                           │   └── usecase
│   │                           │       ├── AddGenresUseCase.kt
│   │                           │       ├── AddMovieRatingUseCase.kt
│   │                           │       ├── AddMoviesUseCase.kt
│   │                           │       ├── ClearCacheUseCase.kt
│   │                           │       ├── ClearRecentlyMoviesUseCase.kt
│   │                           │       ├── GetMovieDetailsUseCase.kt
│   │                           │       ├── GetMovieGenresUseCase.kt
│   │                           │       ├── GetMovieReviewsUseCase.kt
│   │                           │       ├── GetMoviesByGenresUseCase.kt
│   │                           │       ├── GetMoviesGenresUseCase.kt
│   │                           │       ├── GetPopularityMoviesUseCase.kt
│   │                           │       ├── GetRecentlyMoviesUseCase.kt
│   │                           │       ├── GetRecentlyReleasedMoviesUseCase.kt
│   │                           │       ├── GetRecommendedMovieUseCase.kt
│   │                           │       ├── GetUpcomingMoviesUseCase.kt
│   │                           │       ├── GetUserMovieRatingUseCase.kt
│   │                           │       ├── SearchMovieByNameUseCase.kt
│   │                           │       └── SetMovieRecentUseCase.kt
│   │                           ├── person
│   │                           │   ├── entity
│   │                           │   │   ├── PersonCredit.kt
│   │                           │   │   ├── Person.kt
│   │                           │   │   └── PersonSocialMediaLinks.kt
│   │                           │   ├── repository
│   │                           │   │   └── PersonRepository.kt
│   │                           │   └── usecase
│   │                           │       ├── AddRecentPersonUseCase.kt
│   │                           │       ├── ClearRecentPeopleUseCase.kt
│   │                           │       ├── GetPeopleByMovieIdUseCase.kt
│   │                           │       ├── GetPeopleBySeriesIdUseCase.kt
│   │                           │       ├── GetPeopleMediaCreditsUseCase.kt
│   │                           │       ├── GetPersonDetailsUseCase.kt
│   │                           │       ├── GetPersonImagesUseCase.kt
│   │                           │       ├── GetRecentPeopleUseCase.kt
│   │                           │       └── SearchPeopleByNameUseCase.kt
│   │                           └── series
│   │                               ├── entity
│   │                               │   ├── Season.kt
│   │                               │   └── Series.kt
│   │                               ├── repository
│   │                               │   └── SeriesRepository.kt
│   │                               └── usecase
│   │                                   ├── AddRecentSeriesUseCase.kt
│   │                                   ├── ClearRecentSeriesUseCase.kt
│   │                                   ├── GetLastSeasonsUseCase.kt
│   │                                   ├── GetPopularitySeriesUseCase.kt
│   │                                   ├── GetRecentlyReleasedSeriesUseCase.kt
│   │                                   ├── GetRecentSeriesUseCase.kt
│   │                                   ├── GetRecommendedSeriesUseCase.kt
│   │                                   ├── GetSeriesByGenresUseCase.kt
│   │                                   ├── GetSeriesDetailsUseCase.kt
│   │                                   ├── GetSeriesGenresByIdsUseCase.kt
│   │                                   ├── GetSeriesGenresUseCase.kt
│   │                                   ├── GetSeriesReviewsUseCase.kt
│   │                                   ├── GetTopRatedSeriesUseCase.kt
│   │                                   └── SearchSeriesByNameUseCase.kt
│   └── user
│       └── src
│           └── main
│               └── java
│                   └── com
│                       └── giraffe
│                           └── user
│                               ├── entity
│                               │   └── User.kt
│                               ├── exception
│                               │   └── UserException.kt
│                               ├── repository
│                               │   ├── AuthRepository.kt
│                               │   └── OnboardingRepository.kt
│                               ├── SessionManager.kt
│                               └── usecase
│                                   ├── IsLoggedInUseCase.kt
│                                   ├── IsOnBoardingFirstTimeUseCase.kt
│                                   ├── LoginUseCase.kt
│                                   └── SetOnBoardingFirstTimeUseCase.kt
├── imageviewer
│   └── src
│       └── main
│           ├── assets
│           │   ├── nsfw_model.tflite
│           │   └── nsfw_model_tmdb_data.tflite
│           ├── java
│              └── com
│                  └── giraffe
│                      └── imageviewer
│                          ├── blur
│                          │   ├── Blur.kt
│                          │   └── BlurTransformer.kt
│                          ├── component
│                          │   └── SafeIslamicImage.kt
│                          ├── mlmodel
│                          │   ├── SafeIslamicImageClassifierImpl.kt
│                          │   └── SafeIslamicImageClassifier.kt
│                          ├── model
│                          │   └── SafeIslamicImageHost.kt
│                          └── utils
│                              └── drawableToBitmap.kt
├── presentation
│   ├── authentication
│   │   └── src
│   │       └── main
│   │           ├── java
│   │              └── com
│   │                  └── giraffe
│   │                      └── authentication
│   │                          ├── base
│   │                          │   └── BaseViewModel.kt
│   │                          ├── composable
│   │                          │   ├── LoginForm.kt
│   │                          │   └── LogoSection.kt
│   │                          ├── login
│   │                          │   ├── AuthWebViewScreen.kt
│   │                          │   └── WebViewRoute.kt
│   │                          ├── nav
│   │                          │   ├── AuthenticationApiImp.kt
│   │                          │   ├── AuthenticationNavGraph.kt
│   │                          │   └── route
│   │                          │       └── HomeRoute.kt
│   │                          ├── resetpassword
│   │                          │   ├── ResetPasswordRoute.kt
│   │                          │   └── ResetPasswordWebViewScreen.kt
│   │                          └── screen
│   │                              ├── LoginEffect.kt
│   │                              ├── LoginInteractionListener.kt
│   │                              ├── LoginRoute.kt
│   │                              ├── LoginScreen.kt
│   │                              ├── LoginScreenState.kt
│   │                              └── LoginViewModel.kt
│   ├── details
│   │   └── src
│   │       └── main
│   │           ├── java
│   │              └── com
│   │                  └── giraffe
│   │                      └── details
│   │                          ├── base
│   │                          │   ├── BasePagingSource.kt
│   │                          │   └── BaseViewModel.kt
│   │                          ├── components
│   │                          │   ├── AddToCollectionContent.kt
│   │                          │   ├── BSRatingContent.kt
│   │                          │   ├── CollectionBottomSheetContent.kt
│   │                          │   ├── gallery
│   │                          │   │   ├── GalleryItem.kt
│   │                          │   │   ├── GalleryItemLayout.kt
│   │                          │   │   └── GallerySection.kt
│   │                          │   ├── IconTextBox.kt
│   │                          │   ├── LoadingView.kt
│   │                          │   ├── LoginBottomSheet.kt
│   │                          │   ├── MainDetails.kt
│   │                          │   ├── MainInfoCard.kt
│   │                          │   ├── MainMovieOrSeriesDetailsAnimatedContent.kt
│   │                          │   ├── MainMovieOrSeriesDetails.kt
│   │                          │   ├── MinimizedInfoRow.kt
│   │                          │   ├── PosterHorizontal.kt
│   │                          │   ├── PosterVertically.kt
│   │                          │   ├── RatingEmojiState.kt
│   │                          │   ├── RatingSection.kt
│   │                          │   ├── RatingSelector.kt
│   │                          │   ├── RatingStars.kt
│   │                          │   ├── ReviewCard.kt
│   │                          │   ├── SeasonCard.kt
│   │                          │   ├── StaffInfoSection.kt
│   │                          │   ├── StarCastSection.kt
│   │                          │   └── TransitionBetweenColumnAndVerticalGrid.kt
│   │                          ├── models
│   │                          │   ├── CastUi.kt
│   │                          │   ├── CrewUi.kt
│   │                          │   ├── MovieUi.kt
│   │                          │   ├── ReviewUi.kt
│   │                          │   ├── SeasonUi.kt
│   │                          │   └── SeriesUi.kt
│   │                          ├── nav
│   │                          │   ├── DetailsApiImp.kt
│   │                          │   ├── DetailsNavGraph.kt
│   │                          │   └── route
│   │                          │       └── LoginRoute.kt
│   │                          ├── screens
│   │                          │   ├── castCredit
│   │                          │   │   ├── CastCreditContent.kt
│   │                          │   │   ├── CastCreditEffect.kt
│   │                          │   │   ├── CastCreditInteractionListener.kt
│   │                          │   │   ├── CastCreditRoute.kt
│   │                          │   │   ├── CastCreditScreen.kt
│   │                          │   │   ├── CastCreditScreenState.kt
│   │                          │   │   ├── CastCreditViewModel.kt
│   │                          │   │   └── MediaType.kt
│   │                          │   ├── castDetails
│   │                          │   │   ├── CastDetailsEffect.kt
│   │                          │   │   ├── CastDetailsInteractionListener.kt
│   │                          │   │   ├── CastDetailsRoute.kt
│   │                          │   │   ├── CastDetailsScreen.kt
│   │                          │   │   ├── CastDetailsViewModel.kt
│   │                          │   │   └── state
│   │                          │   │       ├── CastDetailsUiState.kt
│   │                          │   │       └── SocialMediaUi.kt
│   │                          │   ├── gallery
│   │                          │   │   ├── GalleryRoute.kt
│   │                          │   │   ├── GalleryScreen.kt
│   │                          │   │   ├── GalleryUiState.kt
│   │                          │   │   └── GalleryViewModel.kt
│   │                          │   ├── moviedetails
│   │                          │   │   ├── MovieDetailsEffect.kt
│   │                          │   │   ├── MovieDetailsInteractionListener.kt
│   │                          │   │   ├── MovieDetailsScreenState.kt
│   │                          │   │   ├── MovieDetailsViewModel.kt
│   │                          │   │   └── screen
│   │                          │   │       ├── MovieDetailsRoute.kt
│   │                          │   │       └── MovieDetailsScreen.kt
│   │                          │   ├── recommended
│   │                          │   │   ├── movie
│   │                          │   │   │   ├── RecomendedContent.kt
│   │                          │   │   │   ├── RecommendedEffectMovie.kt
│   │                          │   │   │   ├── RecommendedInteractionListener.kt
│   │                          │   │   │   ├── RecommendedMovieRoute.kt
│   │                          │   │   │   ├── RecommendedMovieScreen.kt
│   │                          │   │   │   ├── RecommendedMoviesViewModel.kt
│   │                          │   │   │   └── TransitionLazyColumnToGridMovie.kt
│   │                          │   │   └── series
│   │                          │   │       ├── RecommendedInteractionListener.kt
│   │                          │   │       ├── RecommendedSeriesContent.kt
│   │                          │   │       ├── RecommendedSeriesEffect.kt
│   │                          │   │       ├── RecommendedSeriesRoute.kt
│   │                          │   │       ├── RecommendedSeriesScreen.kt
│   │                          │   │       ├── RecommendedSeriesViewModel.kt
│   │                          │   │       └── TransitionBetweenLazyColumnToLazyVerticalGridSeries.kt
│   │                          │   ├── reviewScreen
│   │                          │   │   ├── ReviewEffect.kt
│   │                          │   │   ├── ReviewRoute.kt
│   │                          │   │   ├── ReviewsScreen.kt
│   │                          │   │   └── ReviewsViewModel.kt
│   │                          │   ├── seasons
│   │                          │   │   ├── screen
│   │                          │   │   │   ├── SeasonRoute.kt
│   │                          │   │   │   ├── SeasonsContent.kt
│   │                          │   │   │   └── SeasonsScreen.kt
│   │                          │   │   ├── SeasonsEffect.kt
│   │                          │   │   ├── SeasonsScreenState.kt
│   │                          │   │   └── SeasonsViewModel.kt
│   │                          │   ├── seriesdetails
│   │                          │   │   ├── screen
│   │                          │   │   │   ├── SeriesDetailsContent.kt
│   │                          │   │   │   ├── SeriesDetailsRoute.kt
│   │                          │   │   │   └── SeriesDetailsScreen.kt
│   │                          │   │   ├── SeriesDetailsEffect.kt
│   │                          │   │   ├── SeriesDetailsInteractionListener.kt
│   │                          │   │   ├── SeriesDetailsScreenState.kt
│   │                          │   │   └── SeriesDetailsViewModel.kt
│   │                          │   ├── seriesReview
│   │                          │   │   ├── SeriesReviewRoute.kt
│   │                          │   │   ├── SeriesReviewScreen.kt
│   │                          │   │   └── SeriesReviewViewModel.kt
│   │                          │   └── videoPlayer
│   │                          │       ├── YouTubePlayerRoute.kt
│   │                          │       └── YouTubePlayerScreen.kt
│   │                          └── utils
│   │                              ├── ConvertFormatted.kt
│   │                              ├── EventListener.kt
│   │                              ├── getCurrentLocalDateTime.kt
│   │                              ├── Mapping.kt
│   │                              ├── ObserveScrollDirection.kt
│   │                              └── TypeOfScreen.kt
│   ├── explore
│   │   └── src
│   │       └── main
│   │           ├── java
│   │              └── com
│   │                  └── giraffe
│   │                      └── explore
│   │                          ├── base
│   │                          │   └── BaseViewModel.kt
│   │                          ├── components
│   │                          │   ├── CastItem.kt
│   │                          │   ├── ExploreHeader.kt
│   │                          │   ├── NoResult.kt
│   │                          │   ├── NothingFound.kt
│   │                          │   ├── PosterHorizontal.kt
│   │                          │   ├── PosterVertically.kt
│   │                          │   ├── SearchItem.kt
│   │                          │   ├── TransitionLazyColumnToGrid.kt
│   │                          │   └── VoiceRecordingOverlay.kt
│   │                          ├── nav
│   │                          ├── navigation
│   │                          │   ├── ExploreApiImp.kt
│   │                          │   └── ExploreNavGraph.kt
│   │                          ├── screen
│   │                          │   ├── discover
│   │                          │   │   ├── DiscoverInteractionListener.kt
│   │                          │   │   ├── DiscoverRoute.kt
│   │                          │   │   ├── DiscoverScreen.kt
│   │                          │   │   ├── DiscoverScreenState.kt
│   │                          │   │   └── DiscoverViewModel.kt
│   │                          │   ├── search
│   │                          │   │   ├── SearchInteractionListener.kt
│   │                          │   │   ├── SearchRoute.kt
│   │                          │   │   ├── SearchScreen.kt
│   │                          │   │   ├── SearchScreenState.kt
│   │                          │   │   └── SearchViewModel.kt
│   │                          │   └── searchresult
│   │                          │       ├── CastDetailsRoute.kt
│   │                          │       ├── MovieDetailsRoute.kt
│   │                          │       ├── SearchResultInteractionListener.kt
│   │                          │       ├── SearchResultRoute.kt
│   │                          │       ├── SearchResultScreen.kt
│   │                          │       ├── SearchResultScreenState.kt
│   │                          │       ├── SearchResultViewModel.kt
│   │                          │       └── SeriesDetailsRoute.kt
│   │                          └── util
│   │                              ├── Extention.kt
│   │                              ├── HasErrorMessage.kt
│   │                              ├── Mapper.kt
│   │                              ├── PagingSource.kt
│   │                              ├── retryIOWithSafeCall.kt
│   │                              └── VoiceSearchHelper.kt
│   ├── home
│   │   └── src
│   │       └── main
│   │           ├── java
│   │              └── com
│   │                  └── giraffe
│   │                      └── home
│   │                          ├── base
│   │                          │   └── BaseViewModel.kt
│   │                          ├── components
│   │                          │   ├── AdvertisementSection.kt
│   │                          │   ├── Carousel.kt
│   │                          │   ├── CollectionItem.kt
│   │                          │   ├── CollectionListSection.kt
│   │                          │   ├── HomeUiListSection.kt
│   │                          │   ├── ListTitleSection.kt
│   │                          │   ├── PosterHorizontal.kt
│   │                          │   ├── PosterVertically.kt
│   │                          │   ├── TopAppBar.kt
│   │                          │   ├── TransitionLazyColumnToGrid.kt
│   │                          │   ├── VODItem.kt
│   │                          │   └── YourCollectionsSections.kt
│   │                          ├── navigation
│   │                          │   ├── ExploreRoute.kt
│   │                          │   ├── HomeApiImp.kt
│   │                          │   ├── HomeNavGraph.kt
│   │                          │   ├── MatchRoute.kt
│   │                          │   ├── MovieDetailsRoute.kt
│   │                          │   ├── ProfileRoute.kt
│   │                          │   └── SeriesDetailsRoute.kt
│   │                          ├── screen
│   │                          │   ├── home
│   │                          │   │   ├── HomeEffect.kt
│   │                          │   │   ├── HomeInteractionListener.kt
│   │                          │   │   ├── HomeRoute.kt
│   │                          │   │   ├── HomeScreen.kt
│   │                          │   │   ├── HomeScreenUiState.kt
│   │                          │   │   ├── HomeUiModel.kt
│   │                          │   │   └── HomeViewModel.kt
│   │                          │   └── movies_list
│   │                          │       ├── MoviesListEffect.kt
│   │                          │       ├── MoviesListInteractionListener.kt
│   │                          │       ├── MoviesListRoute.kt
│   │                          │       ├── MoviesListScreen.kt
│   │                          │       ├── MoviesListUiState.kt
│   │                          │       └── MoviesListViewModel.kt
│   │                          └── utils
│   │                              └── Mapper.kt
│   ├── match
│   │   └── src
│   │       └── main
│   │           ├── java
│   │              └── com
│   │                  └── giraffe
│   │                      └── match
│   │                          ├── components
│   │                          │   ├── Carousel.kt
│   │                          │   ├── ProgressIndecator.kt
│   │                          │   └── SelectionItem.kt
│   │                          ├── model
│   │                          │   └── SelectionType.kt
│   │                          ├── navigation
│   │                          │   └── MatchApiImp.kt
│   │                          └── utils
│   │                              └── CalculateCenterItem.kt
│   ├── onboarding
│   │   └── src
│   │       └── main
│   │           └── java
│   │               └── com
│   │                   └── giraffe
│   │                       └── onboarding
│   │                           └── ImagePager.kt
│   └── profile
│       └── src
│           └── main
│               ├── java
│                  └── com
│                      └── giraffe
│                          └── profile
│                              ├── base
│                              │   └── BaseViewModel.kt
│                              ├── components
│                              │   ├── IconTextBox.kt
│                              │   ├── LanguageSelectorItem.kt
│                              │   ├── LanguageSelector.kt
│                              │   ├── MenuItem.kt
│                              │   ├── SettingsSection.kt
│                              │   └── UserProfileSection.kt
│                              ├── createcollection
│                              │   ├── CollectionsButtons.kt
│                              │   └── CreateCollectionContnet.kt
│                              ├── history
│                              │   └── composable
│                              │       └── RatedMovie.kt
│                              ├── navigation
│                              │   └── ProfileApiImp.kt
│                              ├── screens
│                              │   ├── collections
│                              │   ├── history
│                              │   ├── profile
│                              │   └── ratings
│                              └── utils
│                                  ├── EffectListener.kt
│                                  └── Language.kt
└── repository
    ├── explore
    ├── media
    │   └── src
    │       └── main
    │           └── java
    │               └── com
    │                   └── giraffe
    │                       └── media
    │                           ├── collections
    │                           │   ├── CollectionsRepositoryImp.kt
    │                           │   ├── datasource
    │                           │   │   ├── local
    │                           │   │   └── remote
    │                           │   │       ├── CollectionsRemoteDataSource.kt
    │                           │   │       └── dto
    │                           │   │           └── CollectionDto.kt
    │                           │   └── mapper
    │                           │       └── Mapper.kt
    │                           ├── dto
    │                           │   └── AllReviewsDto.kt
    │                           ├── exception
    │                           │   └── MediaDataException.kt
    │                           ├── explore
    │                           │   ├── datasource
    │                           │   │   ├── local
    │                           │   │   │   ├── cacheDto
    │                           │   │   │   │   └── SearchKeywordCacheDto.kt
    │                           │   │   │   └── LocalExploreDataSource.kt
    │                           │   │   └── remote
    │                           │   │       ├── dto
    │                           │   │       │   └── SearchKeywordDto.kt
    │                           │   │       └── ExploreRemoteDataSource.kt
    │                           │   ├── ExploreRepositoryImpl.kt
    │                           │   └── mapper
    │                           │       └── Mapper.kt
    │                           ├── mapper
    │                           │   └── ReviewMapper.kt
    │                           ├── movie
    │                           │   ├── datasource
    │                           │   │   ├── local
    │                           │   │   │   ├── cacheDto
    │                           │   │   │   │   ├── MovieCacheDto.kt
    │                           │   │   │   │   └── MovieGenreCacheDto.kt
    │                           │   │   │   └── MoviesLocalDataSource.kt
    │                           │   │   └── remote
    │                           │   │       ├── dto
    │                           │   │       │   ├── MovieDto.kt
    │                           │   │       │   ├── MovieGenreDto.kt
    │                           │   │       │   ├── RatedMovie.kt
    │                           │   │       │   └── RatingRequest.kt
    │                           │   │       └── MoviesRemoteDataSource.kt
    │                           │   ├── mapper
    │                           │   │   └── Mapper.kt
    │                           │   ├── MoviesRepositoryImpl.kt
    │                           │   └── utils
    │                           ├── person
    │                           │   ├── datasource
    │                           │   │   ├── local
    │                           │   │   │   ├── cacheDto
    │                           │   │   │   │   └── PersonCacheDto.kt
    │                           │   │   │   └── PersonLocalDataSource.kt
    │                           │   │   └── remote
    │                           │   │       ├── dto
    │                           │   │       │   ├── CreditsDto.kt
    │                           │   │       │   ├── PersonCreditDto.kt
    │                           │   │       │   ├── PersonDetailsDto.kt
    │                           │   │       │   ├── PersonDto.kt
    │                           │   │       │   ├── PersonProfileImageDto.kt
    │                           │   │       │   └── PersonSocialMediaDto.kt
    │                           │   │       └── PersonRemoteDataSource.kt
    │                           │   ├── mapper
    │                           │   │   └── Mapper.kt
    │                           │   └── PersonRepositoryImpl.kt
    │                           ├── series
    │                           │   ├── datasource
    │                           │   │   ├── local
    │                           │   │   │   ├── cacheDto
    │                           │   │   │   │   └── SeriesCacheDto.kt
    │                           │   │   │   └── SeriesLocalDateSource.kt
    │                           │   │   └── remote
    │                           │   │       ├── dto
    │                           │   │       │   ├── GenreDto.kt
    │                           │   │       │   ├── SeasonDto.kt
    │                           │   │       │   ├── SeriesDetailsDto.kt
    │                           │   │       │   └── SeriesDto.kt
    │                           │   │       └── SeriesRemoteDataSource.kt
    │                           │   ├── mapper
    │                           │   │   └── Mapper.kt
    │                           │   └── SeriesRepositoryImpl.kt
    │                           └── utils
    │                               ├── ConvertFormattedDate.kt
    │                               ├── DatabaseConstants.kt
    │                               ├── Extension.kt
    │                               ├── ParseStringToDateTime.kt
    │                               ├── PersonContentType.kt
    │                               ├── SafeCall.kt
    │                               └── UrlConstants.kt
    ├── movie
    ├── person
    ├── review
    ├── series
    └── user
        └── src
            └── main
                └── java
                    └── com
                        └── giraffe
                            └── repository
                                ├── AuthenticationRepositoryImpl.kt
                                ├── datasource
                                │   ├── local
                                │   │   ├── AuthenticationRemoteDataSource.kt
                                │   │   └── OnboardingLocalDataSource.kt
                                │   └── remote
                                │       ├── AuthenticationLocalDataSource.kt
                                │       └── UserRemoteDataSource.kt
                                ├── dto
                                │   └── GuestSessionResponse.kt
                                ├── exceptions
                                │   └── UserDataException.kt
                                ├── OnboardingRepositoryImpl.kt
                                └── utils
                                    ├── ImageUrl.kt
                                    └── safeCall.kt


</pre>

