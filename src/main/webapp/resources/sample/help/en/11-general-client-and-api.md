## Client and API

The organizers provide players with prepared clients in the source
code in several languages. Each of these clients already knows how to communicate
with the server, receive and parse messages from the server (usually called a board)
and send commands to the server.

The client code doesn't give too much of a head start to the players because this code
still needs to be sorted out, but there is some logic to communicate with the server +
some high-level API to work with the board (which is already nice).

All languages in one way or another have a similar set of methods:

* `Solver`.
  An empty class with one method - you have to fill it with clever logic.
* `Direcion`.
  Possible directions of movement for this game.
* `Point`
  `x`, `y` coordinates.
* `Element`.
  Type of element on the board.
* `Board`.
  Contains logic for easy search and manipulation of elements on the board.
  You can find the following methods in the Board class:
* `int boardSize();`
  Board size.
* `boolean isAt(Point point, Element element);`
  Is the given element at the point position?
* `boolean isAt(Point point, Collection<Element>elements);`
  Is there anything from the given set at the point position?
* `boolean isNear(Point point, Element element);`
  Is there a given element around the cell with the point coordinate?
* `int countNear(Point point, Element element);`
  How many elements of the given type are there around the cell with point?
* `Element getAt(Point point);`
  Element in the current cell.
* and so on...