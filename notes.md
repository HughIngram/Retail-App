# Retail App

## This feature branch
### this commit

## Planning Approach
- Translate spec into user stories / tasks
- Arrange tasks by MoSCoW
- Try top-down design (as though this project were much larger)
    * i.e. plan from a high level first
    * build the structure of the app sooner rather than later
- Priorities backlog with Trello card-order

## Coding Approach
- start clean, rather than starting messy then refactoring
"We will evaluate architecture, code quality and consistency over feature completeness."
- "inline documentation" - write comments on public methods

## Techniques, libraries to demonstrate
patterns
- MVP pattern
- Repository pattern
- Single Activity
- Dependency Injection
    * Via service locator pattern
libs
- RxJava
- Room
- Navigation arch component
    * the full screen image can be its own fragment
- Glide
- SwipeRefreshLayout
- Retrofit
    * Rx Plugin
testing
- JVM unit
- UI
- Test Orchestrator

# Done
- [x] Get the list from the API, and print it to logcat
- [x] Show a super-basic product list in the UI
- [x] UI test the above
    * mock 3 items and verify they're all shown
    * copy over any relavant tests from EthScan
- Implement repository pattern with Room
- [x] get data from local and remote db
- [x] Use local database if api call fails 
    * Unit test
- [x] show the product list in order ascending by name
    * putting ordering logic in both the SQL query and the repository is not optimal
    * consolidate them to a single place
- [x] set up a grid layout for items
    - [x] use GridLayoutManager
- [x] handle long names in item layout
- [x] import Glide
- [x] show images of products in the list
- [x] loading / error state for images
