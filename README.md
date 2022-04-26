# MainProject
---------------------------------------
A school project where we were asked to create an API that supports creating two-dimensional logical puzzle game.
---------------------------------------
**Note to anyone studying this course**
If you are looking to find insight into the subject by reading our code, I suspect that what you instead will find roughly six thousand lines of pain confusion and suffering. You can copy a solution from stackoverflow and it will work but if you try to use someone elses design it most likely won't. 
If this was you while reading this section :hear_no_evil:, go ahead and try.

**Project instructions:**

**Framework**
In short, your task is to design a framework for constructing computer games. There are very many different kinds of games, but the first basic framework that we have in mind is one to support logic puzzle games that are played on areas consisting of squares / tiles where the player (or many players) can rearrange items, or put items on it. One example of such a game is Sokoban, see https://en.wikipedia.org/wiki/Sokoban, another one would be 2-player tic-tac-toe (though this one is really too simple), or the memory game (also 2-player, see https://www.khanacademy.org/computing/computer-programming/programming-games-visualizations/memory-game/a/intro-to-memory, this one may get you ideas about some of the implementation challenges too). Your framework should provide facilities to display the game graphics, process user input (through mouse and/or keyboard), and interact with a game object that the concrete application defines. 

The puzzle game framework sufficient to implement Sokoban is the minimal requirement for the project to get the minimal grade (along with the minimal Sokoban implementation, see below). However, you are not limited to either this particular framework, or this minimal functionality. For a higher grade you can extend this framework to include sounds, simple animations, or timing aspects of the game (time based occurring events, etc.). You can also choose to construct a framework for different kinds of games, as long as it is at least as elaborate as the puzzle games one. Some examples to think about: 3D puzzle games, 2D shooting games, board game replicas, etc. Whatever you choose, keep in mind that the main purpose of this project is the object-orientation aspects of software design, and constructing a framework. So it is not about providing highly efficient graphical engines with bells and whistles, simple implementations of graphics will do.

**Application**
he minimal application that you have to be able to implement with your framework is the Sokoban game mentioned above. If you choose a framework for another kind of games, provide an application that shows the capabilities of your framework. Also, if you go beyond the minimal framework and provide some extensions, your application should be able to show how this works (for example, if you include sounds in your game framework, extend the Sokoban game to include sound events). 
To help you with the Sokoban game implementation, attached to this section is the zip file with icon pictures of the different elements in the Sokoban game from the Wikipedia link we provided above.

**Project code maintenance - version control**
As an exercise in code management in real software development and also to facilitate he division of work within your project groups during the pandemic, we ask you to maintain your code through one of the publicly available version control systems, for example GIThub or Bitbucket. To do this, set up an account for each project member (unless you have one already and want to use it), create a private (this is important, we do not want the other groups to be peeking into your developments) repository for your project. You should also shortly describe your experiences with version control in the project report.

**Project code (and also report) submission**
The final project code and report is to be submitted through the Blackboard submission system, a suitable entry will be created soon and available on the top of this page. When submitting, consider the following:
1.  Code should be properly indented and formatted.
2.  Code should be documented, explain the role of the parameters to methods and constructors, as well as the general code architecture.
3.  Code should not include debugging statements.

---------------------------------------
