
# NineIntelligence

Smart Tryout application designed to make it easy for students to challenge their skills in preparation for HighSchool Exam.

## What's NineIntelligence has to offer

- Integration with payment Features 
- Discussion Features from previous work 
- Bank Exam for practicing quiz and Tryout Exam
- Ability to download and read subject matter 
- Scrambled question features with different kinds of variety



## Authors

- [@ferdiallen](https://www.github.com/ferdiallen)


## Demo

Currently NineIntelligence is in alpha state (0.7) and are not ready yet for production mode. But you can download demo available and install it yourself. Note that some features may changes during production mode. 


## Usage/Examples

NineIntelligence are built from ground up using Kotlin Programming language with Jetpack compose. Ktor and Koin are used as part of kotlin environment development. Here is an example of Jetpack Compose UI used to build one of items in NineIntelligence App.

```kotlin
@Composable
private fun TopBarMain(onBackPress: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackPress.invoke()
        }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        CustomText(text = "Back", fontSize = 16.sp, color = MainBlueColor)
    }
}
```
In the above code we are creating custom top bar with lambda function to give callback to the parent Composable.
