# Willy Wonka's Chocolate Factory
![a](https://media.licdn.com/media/AAEAAQAAAAAAAAduAAAAJDQzNWQ5ZWJjLTJkNDQtNDljYy05OWM5LTFhMTFmZjM2MTllMg.png)

## Dependencies used:

* RxJava -> Reactive Programming (https://github.com/ReactiveX/RxJava)
* Picasso -> Image loading and caching (https://github.com/square/picasso)
* Dagger 2 -> Inject dependencies (https://google.github.io/dagger/)
* Realm -> ORM Mobile database (https://realm.io/)
* Retrofit -> Request/Response API. (http://square.github.io/retrofit/)
* GSON -> Parse JSON to Java POJOs (https://github.com/google/gson)
* ButterKnife -> Bind views to java code easily and avoiding some code boilerplate (http://jakewharton.github.io/butterknife/)
* Espresso -> UI Testing (https://github.com/googlesamples/android-testing)
* Robolectric -> Usefull TestRunner as a SandBox (http://robolectric.org/)
* Mockito -> Mock/Stub/Spy classes at test files. (https://github.com/mockito/mockito)
* PowerMockito -> Supress initialization at test clases for Realm, for instance. (https://github.com/powermock/powermock)

## Functionalities implemented:

* Pagination
* Lazy Load
* Image Caching
* "Response caching" (application only ask once to API for each item if it is not present at db)
* Filter by Gender
* Search
* Detail view
* Offline Support (with pagination too)
* Reactive Programming as much as possible
* Base Architecture that allows the current application grow without any problem

![b](http://img1.looper.com/img/uploads/2017/03/What_the_Kids_From_the__Willy_Wonka__Movies_Look_Like_Now-1-780x438.jpg)
