# Some Common Refactoring Techniques

This document covers the refactoring techniques required for this assignment. For each technique, there is a short description
followed by a link to documentation for how to apply the technique in IntelliJ or a short explanation if
IntelliJ doesn't support the refactoring technique directly.

## `Extract Method`

This refactoring technique will likely feel very familiar to you from your first-year CS courses.
When developing code, you are generally advised to write helper methods (or functions) to help
break the code into smaller, more meaningful snippets. This can greatly help in terms of the understandability
and, potentially, the testability of your code. If the author of the code instead wrote all the logic inside the body
of a single method, then you will need to apply this refactoring technique to move the code corresponding to
a subtask into a separate helper method. Depending on what the extracted method is responsible for computing,
you may choose to either make the extracted method a private instance method or possibly a static method.

Once you apply the Extract Method technique, the intention of the code should be much clearer. An interested
programmer can still easily trace through to the implementation of the helper if they need to know the details,
but they are no longer forced to see them if they aren't important.

You may find that you need to apply this technique several times for complicated methods with several logical steps involved
in the computation.

See: https://www.jetbrains.com/help/idea/extract-method.html

* * *

## `Change Method Declaration`

This refactoring technique involves modifying how a method is defined, such as altering what its parameters are. When doing so,
one must be careful to update any client code, as well as the body of the method being altered.

In IntelliJ, `Change Signature` helps you perform such a refactoring.

See: https://www.jetbrains.com/help/idea/change-signature.html

* * *

## `Replace Temp with Query`

This refactoring involves removing local variables which are just there for convenience to store a value the
code keeps referring back to. Instead, we can replace each usage of that local variable with a call to a method
to obtain that value. That is, we are "querying" this method each time we need the value.

Applying this technique may make it easier to later apply techniques like `Extract Method` which will more dramatically
improve the understandability of the code than this refactoring directly would.

Note: This refactoring isn't directly supported in IntelliJ, but can be achieved through using `Inline Variable` (see link below)
combined with `Extract Method` if the right-hand side of the assignment statement is complex and so needs to be
pulled into a helper method first.

See: https://www.jetbrains.com/help/idea/inline.html#inline_variable

See also https://stackoverflow.com/questions/320946/replace-temp-with-query for a nice discussion about the usefulness of this refactoring technique.

* * *

## `Encapsulate Fields`

In Java, the convention is to make instance variables as private as possible. Transitioning from Python programming, where
we tend to default to public attributes, you may not yet be in the habit of declaring your data to be private. This refactoring
involves changing the access modifiers on your instance variables, introducing getters and setters as needed to provide appropriate
access to your data, and updating any client or implementation code to use the getters and setters where the data was
directly accessed previously.

Note: Declaring an instance variable to be public will be flagged by SonarLint. You can read more about the issue at
https://rules.sonarsource.com/java/RSPEC-1104/ along with ways to fix it, with one of the ways being to encapsulate fields.

See: https://www.jetbrains.com/help/idea/encapsulate-fields.html

* * *

## `Split Loop`

Sometimes we lump together several computations within a single block of iterative code. When we do this, it can become
difficult to extract related blocks of functionality from our code through refactoring techniques like `Extract Method`.

As the name suggests, this technique involves splitting a loop out into separate loops: with each loop performing independent computations.
For example, think of a time you looped over a list of values and had two accumulator variables being populated in a loop.
Applying this refactoring, you would instead perform two iterations over the list: one to accumulate the first variable and one
to accumulate the second variable. Once these loops are split, it takes us one step closer to being able to apply `Extract Method` to then
hide the existence of the loops entirely from the reader of the code, which can enhance understandability of your code. Of course,
assuming we are still defining our accumulator variables above the first loop, we still don't have the code completely
split. This is where the next refactoring technique will help us out.

Note: this refactoring is not directly supported by IntelliJ, but is straightforward to implement using copy+paste followed by
deleting the duplicated parts of the loop bodies.

* * *

## `Slide Statements`

This technique simply refers to moving lines of code around to help group together code in more meaningful ways. For example,
continuing with the example from above, we may want to move the declaration of each accumulator variable so that they are defined
immediately before each of their corresponding accumulator loops. Once this is done, it would then be possible to apply our
favourite `Extract Method` technique.

This can be achieved using cut+paste, but it turns out IntelliJ has shortcuts which allow you to move lines of code.

See: https://www.jetbrains.com/guide/java/tutorials/rearranging-code/moving-statements-around/

* * *

## `Replace Constructor with Factory Method`

This technique involves refactoring what the code looks like which returns an instance of a class. The idea is to introduce
a static method which is responsible for returning the instance of the class rather than having the code directly call
the constructor. This can enhance understandability and hiding the constructor call effectively abstracts
away the detail of how where the instance of the class actually comes from.

Note: this is directly supported in IntelliJ, but in the assignment, since you don't actually have the constructor,
you could explicitly go through an equivalent process where you directly introduce the factory method into the code.

See: https://www.jetbrains.com/help/idea/replace-constructor-with-factory-method.html

See also: Static Factory methods are discussed in more detail in Effective Java by Joshua Bloch.

* * *

## `Move Method`

As the name suggests, this refactoring technique simply moves a method. This is useful when we are splitting out functionality
across several classes, which requires us to move methods from the original class to other classes. Of course,
moving methods may also require you to move other instance variables too, although syntax errors flagged by your
IDE will usually help resolve any issues which arise if you cut+paste a method from one class to another.

See: https://www.jetbrains.com/help/idea/move-refactorings.html#move_instance

* * *

## Additional Reading:

Refactoring: second edition by Martin Fowler
Free first chapter: https://www.thoughtworks.com/content/dam/thoughtworks/documents/books/bk_Refactoring2-free-chapter_en.pdf

Overview of refactoring which highlights some of these and other refactoring techniques supported by IntelliJ:
https://www.jetbrains.com/help/idea/tutorial-introduction-to-refactoring.html

Old but still interesting overview of refactoring in IntelliJ (exact features differ now of course)
https://objectcomputing.com/resources/publications/sett/february-2002-refactoring-with-idea

* * *

## Helpful links:

JetBrains blog post about the Extracting and Inlining, Change Signature, and Renaming (includes a video):
https://blog.jetbrains.com/idea/2020/12/3-ways-to-refactor-your-code-in-intellij-idea/

Related to the above, JetBrains has a GitHub repo with examples used in various resources they provide:
Repo: https://github.com/JetBrains/intellij-samples

Gregor Riegler has a video doing a similar refactoring for the `Split Phase` part of the assignment, but note that
his code is a simplified version of the code in this assignment, and he doesn't necessarily follow the exact steps outlined
in the instructions. You may find it interesting in any case though: either as you work or after you finish.
Video: https://www.youtube.com/watch?v=sIceCgI6QO0
Repo: https://github.com/gregorriegler/refactoring-split-phase

Assignment is based on the code from https://github.com/emilybache/Theatrical-Players-Refactoring-Kata
and the Refactoring: second edition textbook by Martin Fowler.
