/*
 * File:    Broken	.java
 * Created: 7 September 2001
 * Author:  Stephen Jarvis
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;


public class Ex3
{
  //Function to find the target (is it North?)
  private byte isTargetNorth(IRobot robot) {
    // returning 1 for ‘yes’, -1 for ‘no’ and 0 for ‘same latitude’
      byte resultN;
      if(robot.getLocation().y > robot.getTargetLocation().y) resultN=1;
      else if(robot.getLocation().y < robot.getTargetLocation().y) resultN=-1;
      else resultN=0;
      //System.out.println(resultN);
      return resultN; }

  //Function to find the target (is it East?)
  private byte isTargetEast(IRobot robot) {
    // returning 1 for ‘yes’, -1 for ‘no’ and 0 for ‘same latitude’
      byte resultE;
      if(robot.getLocation().x < robot.getTargetLocation().x) resultE=1;
      else if(robot.getLocation().x > robot.getTargetLocation().x) resultE=-1;
      else resultE=0;
      //System.out.println(resultE);
      return resultE; }

  //Function to sense the environment
  private int lookHeading (IRobot robot, int heading) {
       int check = robot.getHeading();
       robot.setHeading(heading);
       int result = robot.look(IRobot.AHEAD);
       robot.setHeading(check);
       return result;
       }
  // Function to choose a random direction
  private int randomHeading (IRobot robot) {
    int randno;
    int randhead;
    do {
       randno = (int) Math.round(Math.random()*3);
       if ( randno == 0)
          randhead = IRobot.NORTH;
       else if (randno == 1)
          randhead = IRobot.EAST;
       else if (randno == 2)
          randhead = IRobot.SOUTH;
       else
          randhead = IRobot.WEST;
    } while (lookHeading(robot, randhead)==IRobot.WALL);

    return randhead;
  }

 //Function to sense if there are walls towards the wanted directions
  private int targetedHeading(boolean wall1, int head1, boolean wall2, int head2,IRobot robot){
    int randno;
    if( !wall1 && !wall2 ){
      randno = (int) Math.round(Math.random());
      if(randno == 0) return head1;
      else return head2;
    }
    else if( !wall1 ) {
        return head1;
    }
    else if( !wall2 ) {
        return head2;
      }
    else {
      return randomHeading(robot);
    }
  }

  //Heading controller for choosing direction towards the target
  private int headingController(IRobot robot) {

       int N = isTargetNorth(robot);
       int E = isTargetEast(robot);

       boolean wallN = lookHeading(robot, IRobot.NORTH) == IRobot.WALL;
       boolean wallE = lookHeading(robot, IRobot.EAST) == IRobot.WALL;
       boolean wallS = lookHeading(robot, IRobot.SOUTH) == IRobot.WALL;
       boolean wallW = lookHeading(robot, IRobot.WEST) == IRobot.WALL;

       if(N == 1) {
         if(E == 1) {
           return targetedHeading(wallN,IRobot.NORTH,wallE,IRobot.EAST,robot);
         } else if (E == -1) { //North and West
           return targetedHeading(wallN,IRobot.NORTH,wallW,IRobot.WEST,robot);
         } else { //Just North
           return targetedHeading(wallN,IRobot.NORTH,wallN,IRobot.NORTH,robot);
         }
        }

       else if(N == -1) {
         // East and South
         if(E == 1) {
           return targetedHeading(wallS,IRobot.SOUTH,wallE,IRobot.EAST,robot);
         }
         // West and South
         else if (E == -1) {
           return targetedHeading(wallS,IRobot.SOUTH,wallW,IRobot.WEST,robot);
         }
         // Only South
         else {
           return targetedHeading(wallS,IRobot.SOUTH,wallS,IRobot.SOUTH,robot);
         }
        }

       else {
         //Only East
         if(E == 1) {
           return targetedHeading(wallE,IRobot.EAST,wallE,IRobot.EAST,robot);
         }
         //Only West or on Target
         else  {
           return targetedHeading(wallW,IRobot.WEST,wallW,IRobot.WEST,robot);
         }
       }

     }

  // The Main part for moving the robot and testing if the moves are correct
  public void controlRobot(IRobot robot) {

    int heading;
    heading = headingController(robot);
    ControlTest.test(heading, robot);
    robot.setHeading(heading);

    isTargetNorth(robot);
    isTargetEast(robot);
    lookHeading (robot, heading);
    headingController (robot);
  }
  public void reset() {
    ControlTest.printResults();
  }
}
