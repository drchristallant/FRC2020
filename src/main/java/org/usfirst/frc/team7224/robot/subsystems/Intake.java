package org.usfirst.frc.team7224.robot.subsystems;

import org.usfirst.frc.team7224.robot.Robot;
import org.usfirst.frc.team7224.robot.RobotConstants;
import org.usfirst.frc.team7224.robot.RobotMap;
import org.usfirst.frc.team7224.robot.commands.IntakeAction;

//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
*
*/
public class Intake extends Subsystem {


   private final WPI_TalonSRX intakemotor1 = RobotMap.intakeTalonSRX9;
  


   public void initDefaultCommand() {
       setDefaultCommand(new IntakeAction());
   }

   public void setupIntake() {
       intakemotor1.set(ControlMode.PercentOutput,0);
     
   }

   /**
    * sets the ball motor speed  -1 to +1
    */
   public void setIntakeMotor(double ispeed) {
       intakemotor1.set(ControlMode.PercentOutput,ispeed);
       Robot.shoot.setturnSpeed(RobotConstants.kturnspeed);
       
		  }
   

          


}