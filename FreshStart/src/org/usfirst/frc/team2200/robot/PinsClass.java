package org.usfirst.frc.team2200.robot;

public class PinsClass {
	public int frontLeftMotorPin;
	public int middleLeftMotorPin;
	public int rearLeftMotorPin;
	public int frontRightMotorPin;
	public int middleRightMotorPin;
	public int rearRightMotorPin;
	public int topRollerPin;
	public int botRollerPin;
	public int intakeArmSolenoidA;
	public int intakeArmSolenoidB;
	public int teleArmSolenoidA;
	public int teleArmSolenoidB;
	public int teleArmMotor;
	public int driveSolenoidA;
	public int driveSolenoidB;
	public int leftLineSensor;
	public int rightLineSensor;
	public int leftEncPinA;
	public int leftEncPinB;
	public int rightEncPinA;
	public int rightEncPinB;
	public int ultraLeftInput;
	public int ultraLeftOutput;
	public int ultraRightInput;
	public int ultraRightOutput;
	public int limitSwitchTeleArm;
	public int teleArmInfrared;

	//
	
	public PinsClass(){
		//PWM Pins
		 topRollerPin = 1;
		 botRollerPin = 2;
		 teleArmMotor = 0;
		 
		 //DIO
		//leftLineSensor = 4;
		 //rightLineSensor = 5;
		 
		 leftEncPinA = 4;
		 leftEncPinB = 5;
		 
		 rightEncPinA = 6;
		 rightEncPinB = 7;
		 
		 ultraLeftInput = 2;
		 ultraLeftOutput = 3;
		 
		 ultraRightInput = 0;
		 ultraRightOutput = 1;
		 
		 teleArmInfrared = 10;
		 limitSwitchTeleArm = 11;
		 
		 //CANTalon Addresses
		 frontLeftMotorPin = 11;
		// middleLeftMotorPin = 12;
		 rearLeftMotorPin = 12;
		 
		 frontRightMotorPin = 14;
		// middleRightMotorPin = 15;
		 rearRightMotorPin = 15;
		 
		 //Solenoid Labels
		 //one
		 intakeArmSolenoidA = 6;
		 intakeArmSolenoidB = 1;
		 //three
		 teleArmSolenoidA = 4;
		 teleArmSolenoidB = 3;
		 //two
		 driveSolenoidA = 2;
		 driveSolenoidB = 5;
		 

		
	}

}
