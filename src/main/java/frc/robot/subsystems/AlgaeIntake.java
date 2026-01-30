// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.algaeIntakeC;
import au.grapplerobotics.LaserCan;
//import com.revrobotics.RelativeEncoder;


import com.ctre.phoenix6.hardware.TalonFX;

public class AlgaeIntake extends SubsystemBase {
  private final TalonFX motor;
  private final LaserCan laser;
  public boolean getCan() {
    LaserCan.Measurement measurement = laser.getMeasurement();
    return measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT;
}

  public AlgaeIntake() {
    laser = new LaserCan(algaeIntakeC.laserID);
    motor = new TalonFX(algaeIntakeC.intakeID);
    motor.getConfigurator().apply(Constants.algaeIntakeC.config);
   // motor.getConfigurator().apply(Constants.algaeIntakeC.motion);
    
  }

  public Command runIntake(){
    return run(()-> motor.set(algaeIntakeC.maxSpeed))
    .until(() -> motor.getRotorPosition().getValueAsDouble() >= algaeIntakeC.angle)
    .finallyDo(()->
      motor.set(0)
    )
    .withName("Intake running!");
    
  }


  public Command outtake(){
    return run(()-> motor.set(algaeIntakeC.outTakeSpeed))
    .withName("Outtake running");
  }
  public Command slowIntake(){
    return run(()-> motor.set(algaeIntakeC.slowSpeed))
    .withName("Slow intake");
  }
  public Command stopIntake(){
    return runOnce(()-> motor.stopMotor())
    .withName("Stopped motor");
  }

  public double getPosition() {
    return motor.getRotorPosition().getValueAsDouble(); // For TalonFX in Phoenix6 API
}

@Override
public void periodic() {
    LaserCan.Measurement measurement = laser.getMeasurement();
    if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT) {
        SmartDashboard.putNumber("Algae Distance (mm)", measurement.distance_mm);
    } else {
        SmartDashboard.putBoolean("Algae Sensor Valid", false);
    }
}


}
