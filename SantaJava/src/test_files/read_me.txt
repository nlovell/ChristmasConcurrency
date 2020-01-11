Note: As the assignment brief was ambiguous regarding the presents, I have assumed the following
      format for my program

    PRESENTS 1          1 represents the ID of the hopper it belongs to (eg: hopper 1)
    2                   2 represents the number of presents
    4-10                4 is the min age, 10 is the max age
    5-13                5 is the min age, 13 is the max age

    PRESENTS 2          2 represents the ID of the hopper it belongs to (eg: hopper 2)
    1                   1 represents the number of presents
    4-10                4 is the min age, 10 is the max age

      While I believe this is correct, it might not be - thus I included this explanation
      of my interpretation in the test_file_descriptors document.

Note: In case the ASCII isn't enough, graphical versions of these machine configurations can be found at
      https://nlovell.net/uclan/santajava/example_files

        Example 1: The example text provided on page 6 of the assignment brief
        Example 2: A textual representation of the visual example provided on page 3 of the assignment brief
        Example 3: A circle of turntables with a one-way offshoot leading to a sack

                                H      H
                            T ----> T -> T
                            ↑       ↓    ↓
                          S T <---- T -> S
                                    S

        Example 4: A hopper feeding a belt with one turntable, with every output populated

                                    S
                               H -> T S
                                    S

        Example 5: A hopper feeding a single belt, but with 3 turntables and 3 sacks connected at the end of the belt

                               H ------>
                                 T  T  T
                                 S  S  S