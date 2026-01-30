// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// I love programming

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MaeherElevatorC;

public class MaeherElevator extends SubsystemBase {
  /** Creates a new Elevator. */
  private final TalonFX mainMotor;
  private final TalonFX followMotor;
  private final DigitalInput limitSwitch;
  private final TalonFXConfiguration config;
  private final MotionMagicVoltage mm; 
  //private final DigitalInput debouncer;
  
  public MaeherElevator() {
    mainMotor= new TalonFX(MaeherElevatorC.elevatorMotorID);
    followMotor = new TalonFX(MaeherElevatorC.followerMotorID);
    limitSwitch = new DigitalInput(MaeherElevatorC.limitSwitchID);
    mm = new MotionMagicVoltage(0);
    //debouncer = new DigitalInput(ElevatorC.debounceID);

    config = new TalonFXConfiguration();

    config.Slot0 = MaeherElevatorC.config;
    config.MotionMagic = MaeherElevatorC.motion;

    mainMotor.getConfigurator().apply(config);
    //followMotor.setControl(new Follower(mainMotor.getDeviceID(), true));

  }

   public boolean limitReached() {
    return !limitSwitch.get();
  }

  public Command up() {
  return run(()->{
    mainMotor.set(MaeherElevatorC.upSpeed); 
    //followMotor.set(ElevatorC.upSpeed);
    }).withName("Elevator Up");
  }

  public Command down() {
    return run( ()->{
      mainMotor.set(MaeherElevatorC.downSpeed); 
    //  followMotor.set(ElevatorC.downSpeed);
    })
    .until(()-> limitReached())
    .withName("Elevator Down");
  }

  public Command stop(){
    return runOnce(()->{
      mainMotor.set(0);
     followMotor.set(0);
  })
  .until(()-> limitReached())
  .withName("Elevator Stop");
}

/*ONLY COMMAND THAT IS TIED TO MOTION MAGIC. DO NOT RUN OTHER COMMANDS */
  public Command level1(){
    return run(()->{
      mainMotor.setControl(mm.withPosition(MaeherElevatorC.firstPosition));
      followMotor.setControl(mm.withPosition(MaeherElevatorC.firstPosition));
    })
    .until(()->mainMotor.getPosition().getValueAsDouble() - MaeherElevatorC.firstPosition>100 )
    .withName("Elevator to first level");
  }
  public Command level2(){
    return run(()->{
      mainMotor.setControl(mm.withPosition(MaeherElevatorC.secondPosition));
      followMotor.setControl(mm.withPosition(MaeherElevatorC.secondPosition));
      
    })
    .withName("Elevator to second level");
  }

  public Command level3(){
    return run(()->{
      mainMotor.setControl(mm.withPosition(MaeherElevatorC.thirdPosition));
     followMotor.setControl(mm.withPosition(MaeherElevatorC.thirdPosition));
    }).withName("Elevator to third level");

  }
  public Command level4(){
    return run(()->{
      mainMotor.setControl(mm.withPosition(MaeherElevatorC.fourthPosition));
     followMotor.setControl(mm.withPosition(MaeherElevatorC.fourthPosition));
    }).withName("Elevator to fourth level");
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
