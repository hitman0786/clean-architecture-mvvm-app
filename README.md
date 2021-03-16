# clean-architecture-mvvm-app
Clean Architecture - mvvm


This has 3 layers Domain, Data and Presenter. Each layer has its own responsibility. 
Domain  has business logic and it has only kotlin code and does not have any android specific code.  
Data Layer has networking and local database code . Here Retrofit and Room is used.
Presenter has all android specific code with mvvm design.

This project has unit test cases for each layer separately with Junit, Mockito and Robolectric

To gernerate APK file with GITHUB Action
need to add tag and perform push action such as

git tag v0.1 -a -m "Release v0.1"
git push --follow-tags

