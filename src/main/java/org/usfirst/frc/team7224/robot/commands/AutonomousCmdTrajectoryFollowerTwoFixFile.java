package org.usfirst.frc.team7224.robot.commands;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team7224.robot.Robot;
import org.usfirst.frc.team7224.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// ********************************************************************************************************
// ********  In addition to Trajectory tracking this command also determine the path that needs to be taken 
// *********************************************************************************************************


public class AutonomousCmdTrajectoryFollowerTwoFixFile extends Command {
	 
        edu.wpi.first.wpilibj.Timer timeout;
        Timer t;
        int gyrowait; 
        double lasttime;
        double timelapse;
        public double maxtimeout = 8;
        EncoderFollower left;
        EncoderFollower right;
        public int autofile = 0;
        public int Position; 
        //This has a max size of three
        Waypoint[] waypoints = new Waypoint[2];
        
        public AutonomousCmdTrajectoryFollowerTwoFixFile(int position) { // 0 Straight,  1 left, 2 center, 3 right
            requires(Robot.chassis);
            Position = position; 
            }  
  

        @Override
        protected void initialize() {
        	  timeout = new edu.wpi.first.wpilibj.Timer();
              timeout.start();
              String fileString;
              RobotConstants.isTrajectory = true;
              Robot.chassis.setupDrive();
              RobotConstants.TrajectorySegments = 0; 
           // ***********************************************   
           // FRC 2018 First Power up game decision
           // Determine Switch, Scale or far Switch
           // *********************************************** 
          //  RobotConstants.kMaxSpeed_a = RobotConstants.kSlowArm_a; // slow down arm 
          //  Robot.arm.armSetup();
          //    if(RobotConstants.gameData.length() > 0) {
          /*        switch (Position) { 
                  case 0:  // Drive straight
          	    	       fileString = "/home/lvuser/driveStraight.traj"; 
           	    	      // RobotConstants.targetPositionRotations_a = RobotConstants.kArm_FieldHT_a; 
           	    	       maxtimeout = 10;
                  case 1:  // Position left  
                	       if (RobotConstants.gameData.charAt(1) == 'L') { // Go for scale
                	       fileString = "/home/lvuser/leftToScale.traj"; 
                	       RobotConstants.targetPositionRotations_a = RobotConstants.kArm_ScaleHT_a;
                	       maxtimeout = 12;
                	       }else if (RobotConstants.gameData.charAt(0) == 'L') { // Go to switch
                	    	fileString = "/home/lvuser/leftToSwitch.traj"; 
                   	       RobotConstants.targetPositionRotations_a = RobotConstants.kArm_SwitchHT_a;
                   	       maxtimeout = 10;
                	       }else { // Position 1 is not left it must be right - go to far switch
                	    //	fileString = "/home/lvuser/leftToFarSwitch.traj"; 
                	    	fileString = "/home/lvuser/driveStraight.traj"; 
                     //	    RobotConstants.targetPositionRotations = RobotConstants.kArm_SwitchHT;
                            RobotConstants.targetPositionRotations_a = RobotConstants.kArm_FieldHT_a; 
                      	    maxtimeout = 10;
                      	    } // end left processing
                           break;
                  case 2:  // Position Center 
           	               if (RobotConstants.gameData.charAt(0) == 'L') { // Go to left switch
           	            	  RobotConstants.targetPositionRotations_a = RobotConstants.kArm_SwitchHT_a;  
           	    	        fileString = "/home/lvuser/CenterToLeftSwitch.traj"; 
           	    	   	        maxtimeout = 10;
           	               }else  { // Go to right switch
           	            	 RobotConstants.targetPositionRotations_a = RobotConstants.kArm_SwitchHT_a;   
           	                fileString = "/home/lvuser/CenterToRightSwitch.traj"; 
              	    	     maxtimeout = 10;
           	               } // end center processing
                           break;
                  case 3:  // Position right  
           	               if (RobotConstants.gameData.charAt(1) == 'R') { // Go for scale
           	                fileString = "/home/lvuser/rightToScale.traj"; 
                	        RobotConstants.targetPositionRotations_a = RobotConstants.kArm_ScaleHT_a;
                	        maxtimeout = 12;
           	               }else if (RobotConstants.gameData.charAt(0) == 'R') { // Go to switch
           	                fileString = "/home/lvuser/rightToSwitch.traj"; 
              	           RobotConstants.targetPositionRotations_a = RobotConstants.kArm_SwitchHT_a;
                	    	maxtimeout = 10;
           	               }else { // Position 1 is not left it must be left - go to far switch
           	    	//      fileString = "/home/lvuser/rightToFarSwitch.traj"; 
           	                fileString = "/home/lvuser/driveStraight.traj";    
                 	  //    RobotConstants.targetPositionRotations = RobotConstants.kArm_SwitchHT;
                 	       RobotConstants.targetPositionRotations_a = RobotConstants.kArm_FieldHT_a;	
                   	        maxtimeout = 10;
           	               } // end right processing
                           break;         
                  default: // Default drive straight 
                           fileString = "/home/lvuser/driveStraight.traj"; 
  	    	             //  RobotConstants.targetPositionRotations_a = RobotConstants.kArm_FieldHT_a; 
  	      	               maxtimeout = 10;
                          break;         
                  } // end of switch
                  
                  */
                  fileString = "/home/lvuser/driveStraight.traj"; 
            //  Robot.arm.armControl();
            //  SmartDashboard.putNumber("Target Arm Position", RobotConstants.targetPositionRotations_a);
            //  } else {
                
            //	fileString = "/home/lvuser/driveStraight.traj"; 
	          //  RobotConstants.targetPositionRotations_a = RobotConstants.kArm_SwitchHT_a; 
            //    } // End of iff
            
                  
                SmartDashboard.putString("GameData", RobotConstants.gameData);            
           //     SmartDashboard.putString("FileName", fileString);
        
                  // Raise arm to height set in switch 
          	  //    Robot.arm.armControl();
                  File myFile = new File (fileString);
                  Trajectory trajectory = Pathfinder.readFromFile(myFile);
                   

                 double wheelbase_width =  .66; // MG updated

                   // Create the Modifier Object
                  TankModifier modifier = new TankModifier(trajectory).modify(wheelbase_width);

                      
                  left = new EncoderFollower(modifier.getLeftTrajectory());
                  right = new EncoderFollower(modifier.getRightTrajectory());

                // Encoder Position is the current, cumulative position of your encoder. 
                //  If you're using an SRX, this will be the
                // 'getEncPosition' function.
                // 1440 is the amount of encoder ticks per full revolution
                // 1440 ticks per rev * 3:1 gear ratio *4x = 1920
                // Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
                // 6" * .0254 = .1524
                  

                  left.configureEncoder(Robot.chassis.getLeftEncoderPosition(), 365, 0.1524);
                  right.configureEncoder(Robot.chassis.getRightEncoderPosition(), 365, 0.1525);
                     
                   
                  
                // The first argument is the proportional gain. Usually this will be quite high
                // The second argument is the integral gain. This is unused for motion profiling
                // The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
                // The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
                // trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
                // The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
                   left.configurePIDVA(0.7, 0.0, 0.00, 0.6, 0);
                   right.configurePIDVA(0.7, 0.0, 0.00, 0.6, 0);
                           
              
                   t = new Timer();
                   Robot.chassis.resetEncoders();
                   Robot.chassis.resetEncoders();
                   Robot.chassis.brakemode(true);
                   t.schedule(new TimerTask() {
                 // Sample setup  	
                 //  	double l = left.calculate(encoder_position_left);
                 //  	double r = right.calculate(encoder_position_right);
                 // 
                 //  	double gyro_heading = ... your gyro code here ...    // Assuming the gyro is giving a value in degrees
                 //  	double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees
                 //
                 //  	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
                 //  	double turn = 0.8 * (-1.0/80.0) * angleDifference;
                 //
                 //  	setLeftMotors(l + turn);
                 //  	setRightMotors(r - turn);
                 //////   End sample setup  	
                       
                       double l = 0;
                       double r = 0;
                       double gyro_heading = 0;
                       public void run() {
                           if  (!left.isFinished() || !right.isFinished()) {                     
                    	          l = left.calculate(Robot.chassis.getLeftEncoderPosition()) ;
                                  r = right.calculate(Robot.chassis.getRightEncoderPosition()) ;
                                  gyro_heading = - Robot.chassis.getGyroAngle();   // Assuming the gyro is giving a value in degrees
                                  double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees
                     	          double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
                                  double turn = 0.8 * (-1.0/80.0) * angleDifference;
                                  Robot.chassis.tankDrive((l+turn),(r - turn));
                               	SmartDashboard.putNumber("tra r ",r );
                                  	SmartDashboard.putNumber("tra l ",l);
                                    SmartDashboard.putNumber("tra head", desired_heading);
                                    SmartDashboard.putNumber("tra angle Difference", angleDifference);
                                    SmartDashboard.putNumber("tra gyro ", Robot.chassis.getGyroAngle());
                                    SmartDashboard.putNumber("tra encoder right", Robot.chassis.getRightEncoderPosition());
                                    SmartDashboard.putNumber("tra encodeer left", Robot.chassis.getLeftEncoderPosition()); 
      
                            } else {
                        	   Robot.chassis.tankDrive(0,0);
                        	    left.reset();
                                right.reset();
                                t.cancel();
                            }
                    	   }
                                              	 
                   }, 0, 50); // end timed task  0 delay, execute every 50 mSec
                  left.reset();
                  right.reset();
                  Robot.chassis.resetEncoders();
                  Robot.chassis.displayChasisData();
                //  RobotConstants.kMaxSpeed_a = RobotConstants.kNormalArm_a; // Normal arm 
              
        }
        @Override
        protected void execute() {
         }
        

        @Override
        protected boolean isFinished() {
        	 if (left.isFinished() || right.isFinished() || (timeout.get() > maxtimeout)) {
        		Robot.chassis.resetEncoders();	 
        		t.cancel();
        	  return true;
            }
            return false;
        }

        @Override
        protected void end() {
            timeout.stop();
            timeout.reset();
            t.cancel();
            RobotConstants.isTrajectory = false;
            Robot.chassis.resetEncoders();
            Robot.chassis.brakemode(false);
           // RobotConstants.kMaxSpeed_a = RobotConstants.kNormalArm_a; // Normal arm 
        }

        @Override
        protected void interrupted() {
            end();
           
        }
        
    }
