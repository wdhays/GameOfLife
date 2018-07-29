# GameOfLife

Conway's Game of Life written in Java using JavaFX

### Author

William Daniel Hays

### About Project

This is a small project that I decided to work on in order to strengthen my understanding for Java, JavaFX,
FXML, and the MVC pattern. I enjoyed working on this project and am quite happy with the result(v1.0). I have begun brainstorming some improvements that will be the focus of future versions. A list of current and future features can be found at the bottom of this document.

### Example

<img src="http://www.wdhays.com/public/GOL.gif"/>

### Instructions

##### Left Side of Window

The left side of the window contains the GOL game board(the grid) and a small info bar below that displays information about the current state of the program. The user can left click any cell to toggle its state E.g alive->dead or dead->alive. The user can also click and hold the left mouse button then drag to highlight a rectangular region on the grid(the game is automatically paused during this operation if running), any cell that is highlighted will have its state toggled when the user releases the mouse button, upon button release the game will return to its original running state(running if it was originally running before the drag operation).

##### Right Side of Window

The right side of the window contains the control panel. <br/>
Controls listed from top to bottom, the user can:
- play/pause the game as well as clear the grid(resets generation count).
- adjust the games running speed using a slide, from V. Slow(0.8 second generations) to V. Fast(0.05 second generations).
- choose one of ten different GOL rule sets using a drop-down menu(affects are immediate).
- add a predefined pattern to the grid using a drop-down menu and then clicking the "Add" button, a preview of the pattern is displayed below the drop-down and is updated when the drop-down value is changed.
- save or load a .gol file, when saving or loading a pop-up window file chooser will appear.
- generate a random grid of living cells by entering a a probability value in the text field and then clicking the "Go" button(a value from [0.0-1.0] is required, anything outside of this range will just default to the min or max).
- toggle the use of cell age colors using a check box(cells will be displayed as different colors based on the age of the cell).

#### Features

###### Current Features(v1.0):

- [x] click to kill/resurrect cell
- [x] wrap around game board
- [x] save/load buttons
- [x] play/pause buttons
- [x] speed up /slow down slider
- [x] ability to use different GOL rules
- [x] drag to kill/resurrect a rectangular patch of cells
- [x] generate random pattern on grid
- [x] cell age, different cell color based on age of cell
- [x] preset patterns
- [x] check box for toggling cell age colors on/off

###### Future Features(v1.x):

- [ ] change current patterns to stamps(can be placed anywhere on grid)
- [ ] add multiple stamps to a single grid
- [ ] rotate stamps before placement
- [ ] user ability to resize/scale game board grid
- [ ] integrate existing GOL pattern libraries(instead of me doing them by hand)
- [ ] optimize current nextGeneration method for speed
- [ ] add more keyboard features(E.g hold shift to turn off/on all cells under a drag, instead of toggled based on current state)
