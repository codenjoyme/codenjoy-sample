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