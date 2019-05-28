/*
 * File:    DumboController.java
 * Created: 17 September 2002, 00:34
 * Author:  Stephen Jarvis
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1
{

    public void controlRobot(IRobot robot) {

	int randno;
	int direction;
  String dir;
  String place;
  int walls = 0;


  //Counting walls around
    if(robot.look(IRobot.AHEAD) == IRobot.WALL)
    walls++;
    if(robot.look(IRobot.BEHIND) == IRobot.WALL)
    walls++;
    if(robot.look(IRobot.RIGHT) == IRobot.WALL)
    walls++;
    if(robot.look(IRobot.LEFT) == IRobot.WALL)
    walls++;

  //Understanding where is the robot
    if (walls == 0) {
      place = " at a crossroad";}

    else  if (walls == 1)
      {place = " at a junction";}

    else if  (walls == 2)
      {place = " down a corridor";}

    else {
      place = " at a dead-end";}


do
{
	// Select a random number
	randno = (int) Math.round(Math.random()*3);

	// Convert this to a direction
	if (randno == 0)
	    {direction = IRobot.LEFT;
        dir = "left";}

	else if (randno == 1)
	    {direction = IRobot.RIGHT;
        dir = "right";}

	else if (randno == 2)
	    {direction = IRobot.BEHIND;
        dir = "backwards";}

	else
	    {direction = IRobot.AHEAD;
        dir = "forward";}


} while (robot.look(direction) == IRobot.WALL);

	robot.face(direction);  /* Face the robot in this direction */
System.out.println("Im going " + dir + place);
  }
}
