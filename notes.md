# Retail App

## Planning Approach
- Translate spec into user stories / tasks
- Arrange tasks by MoSCoW

## Coding Approach
- start clean, rather than starting messy then refactoring
"We will evaluate architecture, code quality and consistency over feature completeness."
- "inline documentation" - write comments on public methods

## API
- GET a list of products
- no auth required
- products have images, at different url's
    * images have an id and a url
```
{
        "identifier": 237,
        "name": "Tasty cucumber",
        "brand": "Veggie World",
        "original_price": 1,
        "current_price": 0.79,
        "currency": "CHF",
        "image": {
            "id": 103,
            "url": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/ARS_cucumber.jpg/220px-ARS_cucumber.jpg"
        }
}
```

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
- image persistence / loading lib?
- SwipeRefreshLayout
testing
- JVM unit
- UI
