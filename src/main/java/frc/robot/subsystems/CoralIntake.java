// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OuttakeConstants;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class CoralIntake extends SubsystemBase {
  /** Creates a new CoralIntake. */
  final PWMSparkMax neoMotorsPwmSparkMax;
  public CoralIntake() {
    neoMotorsPwmSparkMax = new PWMSparkMax(IntakeConstants.inttakeMotorID);

  }

  public Command Go() {
        return run(
      ()->{neoMotorsPwmSparkMax.set(IntakeConstants.inttakeSpeed);
    });
  }

  public Command Stop() {
          return run(
        ()->{neoMotorsPwmSparkMax.stopMotor();;
      });

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
