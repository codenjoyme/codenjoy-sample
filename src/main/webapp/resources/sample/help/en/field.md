## Field example

Here's a sample line from the server:

<pre> board=☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼ $ ☼☼ ☼☼ $ ☼☼ $ ☼☼ $ ☼☼ ☼☼ ☻x ☼☼ ☺ ☼☼ $ x ☼☼ $ x ☼☼ ☼☼ $ x $☼☼         x ☼☼ x $ ☼☼ x ☼☼ $ ☼☼ ☼☼ $ ☼☼ ☼☼ ☼☼ ☼☼ $ ☼☼ ☼☼ ☼☼ ☼☼ $ ☼☼ ☼☼ $ ☼☼ $ ☼☼ ☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼ </pre>

The length of the string is equal to the area of the field `N*N`. If you insert a line break character
every `N=sqrt(length(string))` characters, then
you get a readable image of the field:

<pre>☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
☼          $                 ☼
☼                            ☼
☼   $              $         ☼
☼                       $    ☼
☼  $                         ☼
☼                            ☼
☼        ☻x                  ☼
☼               ☺            ☼
☼        $ x                 ☼
☼        $  x                ☼
☼                            ☼
☼ $         x               $☼
☼         x                  ☼
☼         x    $             ☼
☼         x                  ☼
☼    $                       ☼
☼                            ☼
☼                       $    ☼
☼                            ☼
☼                            ☼
☼                            ☼
☼            $               ☼
☼                            ☼
☼                            ☼
☼       $                $   ☼
☼                            ☼
☼                $           ☼
☼                            ☼
☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

The first character of the line corresponds to the cell located in the upper left corner and has the coordinate `[0, 28]`. 
In this example, the hero's position (symbol `☺`) is `[8, 16]`.

This is what you see on UI: