
package org.usfirst.frc.team2200.robot;
import com.ni.vision.NIVision;



import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import com.ni.vision.NIVision.Image;
import com.kauailabs.navx.frc.*;



public class Robot extends SampleRobot {
	PinsClass pins = new PinsClass();
    CANTalon frontLeftMotor = new CANTalon(pins.frontLeftMotorPin);
	CANTalon rearLeftMotor = new CANTalon(pins.rearLeftMotorPin);
	CANTalon frontRightMotor = new CANTalon(pins.frontRightMotorPin);
	CANTalon rearRightMotor = new CANTalon(pins.rearRightMotorPin);
	Talon upperRoller = new Talon (pins.topRollerPin);
	Talon bottomRoller = new Talon (pins.botRollerPin);
    Talon liftMotor = new Talon (pins.teleArmMotor);
	RobotDrive myRobot = new RobotDrive(frontLeftMotor,rearLeftMotor,frontRightMotor,rearRightMotor);
    Joystick driverController = new Joystick(0);
    Joystick opController = new Joystick(1);
    SendableChooser chooser;
    DoubleSolenoid shifterSolenoid = new DoubleSolenoid(pins.driveSolenoidA,pins.driveSolenoidB);
    DoubleSolenoid intakeSolenoid = new DoubleSolenoid(pins.intakeArmSolenoidA,pins.intakeArmSolenoidB);
    DoubleSolenoid lockSolenoid = new DoubleSolenoid(pins.teleArmSolenoidA,pins.teleArmSolenoidB);
    DigitalInput liftLimit = new DigitalInput(pins.limitSwitchTeleArm);
    DigitalInput liftInfra = new DigitalInput(pins.teleArmInfrared);
	int session;
	Image frame;
	//AHRS ahrs;
    
    
    boolean liftLockState = true;
	

    public Robot() {
        myRobot.setExpiration(0.1);
    }
    
    public void liftLock (boolean locked){
    	if (locked){
    		lockSolenoid.set(DoubleSolenoid.Value.kReverse);
    		liftLockState = true;
    	}
    	else{
    		lockSolenoid.set(DoubleSolenoid.Value.kForward);
    		liftLockState = false;
    	}
    }
    
    public void autoNothing(){
    	
    }
    
    public void autoDriveTime(double time,double speed){
    	myRobot.tankDrive(speed, speed);
    	Timer.delay(time);
    	myRobot.tankDrive(0, 0);
    	
    }
    
    public void robotInit() {
//		try {
//			ahrs = new AHRS(SPI.Port.kMXP);
//		} catch (Exception e) {			
//		} 
    	
    	try{
        	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    		session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);NIVision.IMAQdxConfigureGrab(session);
    	}
    	catch(Exception e){
    	}
    	
        chooser = new SendableChooser();
        chooser.addDefault("Nothing", "nothing");
        chooser.addObject("Drive Forward", "driveForward");
        SmartDashboard.putData("Auto modes", chooser);
        
        liftLock(true);
    }
    
    


    public void autonomous() {
    	
		myRobot.setSafetyEnabled(false);

		frontRightMotor.enableBrakeMode(true);
		frontLeftMotor.enableBrakeMode(true);
		rearRightMotor.enableBrakeMode(true);
		rearLeftMotor.enableBrakeMode(true);
		
		String autoSelected = (String) chooser.getSelected();
		
		if (autoSelected == "nothing"){
			autoNothing();
		}
		else if(autoSelected == "driveForward"){
			autoDriveTime(1,0.7);
		}
		
    	

    	
    }

    public void highGear(boolean gear){
    	if (gear){
    		shifterSolenoid.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void intakeUp (boolean up){
    	if (up){
    		intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	else{
    		intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    		
    	}
    }
    
    public void roller (int mode){
    	if (mode == 0){
    		upperRoller.set(0);
    		bottomRoller.set(0);
    	}
    	else if (mode == 1){
    		upperRoller.set(1);
    		bottomRoller.set(-1);
    	}
    	else if (mode == 2){
    		upperRoller.set(-1);
    		bottomRoller.set(1);
    	}
    	else if (mode == 3){
    		upperRoller.set(-1);
    		bottomRoller.set(-1);
    	}
    }
    

    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        
		frontRightMotor.enableBrakeMode(false);
		frontLeftMotor.enableBrakeMode(false);
		rearRightMotor.enableBrakeMode(false);
		rearLeftMotor.enableBrakeMode(false)
		;
		double timer = 0;
        while (isOperatorControl() && isEnabled()) {
        	
        	try{
        		NIVision.IMAQdxGrab(session, frame, 1);
        	}
        	catch(Exception e){
        	}
        	//if (timer == 1){
        	try {
        		CameraServer.getInstance().setImage(frame);
        		} 
        	catch (Exception e) {
        		}
        		//timer = 0;
        	
        	//else{
        		//timer = timer+0.5;
        		//}
        	SmartDashboard.putBoolean("INFRARED TELEARM", liftInfra.get());
        	  																			//Driver controller controls
        	 
            myRobot.tankDrive(driverController.getY(), driverController.getThrottle());	//drive in tank drive
            
            if (driverController.getRawButton(7) || driverController.getRawButton(8)){ 	// if pressing button 7 or 8 
            	highGear(false);														//drives in low gear
            }
            else{
            	highGear(true);															//defaults to high gear
            }
            
            
            
            																			//Co Driver Controls
            if (opController.getRawButton(5)){ 											//if you press 5
            	intakeUp(true);															//intake go up
            }
            else if (opController.getRawButton(7)){										//if you press 7
            	intakeUp(false);														//intake go down
            }
            
            
            if (opController.getRawButton(2)){											//press 2
            	roller(1);																//suck in (intake)
            }
            else if(opController.getRawButton(3)){										//press 3
            	roller(2);																//ball out
            }
            else if (opController.getRawButton(1)){										//press 1
            	roller(3);																//door
            }
            else{
            	roller(0);																//default to off
            }
            
            if(opController.getRawButton(9) && opController.getRawButton(10)){			//press 9 AND 10
            	liftLock(false);														//unlock arm
            }
            else if (opController.getRawButton(4)){										//press 4
            	liftLock(true);															//lock arm
            }
            
            if (!liftLockState){														// if the arm is unlocked the motor can be extended or retracted
            	liftMotor.set(-1*opController.getY());
            }
            else{
            	liftMotor.set(0);														// Motor won't move
            }
            if (!opController.getRawButton(9) && !opController.getRawButton(10) && !liftLimit.get()){
            	liftMotor.set(0);
            	liftLock(true);
            }
            Timer.delay(0.005);															// wait for a motor update time
        }
        /*
        try {
			NIVision.IMAQdxStopAcquisition(session);
		} 
        catch (Exception e) {
		}
		*/
    }

   
    public void test() {
    }
}
