## How to play?

The game is turn-based, every second the server sends your client the state of the updated field for the current moment and waits for the response of the command to the hero. In the next second the player has to manage to give a command to the hero. If he doesn't, the hero stands still.

Your goal is to make the hero move according to your algorithm.
The hero on the field must be able to score as many points as he can.
The main goal of the game is to beat all opponents by points.

#### Commands

There are several commands:
* `UP`, `DOWN`, `LEFT`, `RIGHT` - lead to the movement of the hero in a given direction by 1 cell.
* `ACT` - leave the bomb. 
    Movement commands can be combined with the command `ACT`, 
    separating them with a comma - which means that for one tick of the game will be left bomb, and then the movement `LEFT, ACT` or vice versa `ACT, LEFT`.

### Settings.

The properties will change[*](#ask) as the game progresses.
are shown in the table below:

| Event | Title | Points |
|---------|----------|------|
| WIN_SCORE | 30[*](#ask)
| LOSE_PENALTY | -20[*](#ask)
| WIN_ROUND_SCORE | 100[*](#ask)

#### Cases