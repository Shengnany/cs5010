# Group_lucyxia_shengnanyou Assignment4 - Random Sentence Generator
CS5010 repo for Fall2021; Northeastern Seattle.

> 1.Entry point: RandomSentenceGenerator class -> main() function

> 2.Key class:

1. GenerateOutput.java - This class exports the result sentense that we want.

   - helper() funtion help deal with the recurision call needs when user choose "y" or "n".
   - isValidInteger() function make sure the user input is a Integer type number
   - getSentence() function calls GenerateSentence() funtions and find the potential sentense according to the usable grammer from input documents

2. ReadFile.java - This class checks and deals with the origin input grammer json documents
   - getFileMap() function deal with files and filter out avaliable json grammer documemts
   - getIndexMap() function sets the grammer title as the value and save the avaliable documents in a map. The index is used to arrange them and being called in later actions

> 3.Assumptions:

1. The root of the json files are valid
2. The user input is not case-sensitive, meaning that "q" and "Q" are both valid options, etc.
3. There will be failing generated sentences. For example, some non-terminal words do not exist in the file. In this situation, the program will prompt user for their next choice("Would you like another sentence in this grammar format? (y/n)").

> 4.Steps demo:
> input1: javac javac RandomSentenceGenerator src/main/resources

```java
Loading grammars...
The following grammars are available:
1. Insult Generator
2. Term Paper Generator
3. Poem Generator
Which grammer would you like to use? (q to quit)
```

input2: 1

```java
May a crazed rush limbaugh find your earlobes suddenly delectable.
Would you like another sentence in this grammar format? (y/n)
```

input3:y

```java
You moronic truckload of phlegm.
Would you like another sentence in this grammar format? (y/n)
```

step4: n

```java
The following grammars are available:
1. Insult Generator
2. Term Paper Generator
3. Poem Generator
Which grammer would you like to use? (q to quit)
```

step5: q

```java
See you ;)
```

> 5.How to run program from command line:

```java
Step1: javac RandomSentenceGenerator src/main/resources
Step2: 1
step3: y
step4: n
step5: 3
step6: y
step7: n
step8: q
```

