# classifier4j
Classifier4J is a Java library designed to do text classification. It comes with an implementation of a Bayesian classifier, and now has some other features, including a text summary facility.

Fork from http://classifier4j.sourceforge.net/ (Authored by Nick Lothian)

# License
The software stands under Apache 1.1 License and comes with NO WARRANTY.

# Changes
The build system has been switched to Gradle. Some (very minor) fixes have been made:
- The AbstractWordsDataSourceSupport test case was marked as abstract so that Junit doesn't run it.
- Files were restructured for Gradle build.

# How to use

Include the dependency.

### Gradle (build.gradle):
```
repositories { 
    maven { url "https://jitpack.io" }
}
dependencies {
     compile 'com.github.yaraju:Classifier4J:-SNAPSHOT'
}
```

### Maven (pom.xml):

First add the repo at "https://jitpack.io"
 
 ```xml
 <dependency>
    <groupId>com.github.yaraju</groupId>
    <artifactId>Classifier4J</artifactId>
    <version>-SNAPSHOT</version>
 </dependency>
 ```
