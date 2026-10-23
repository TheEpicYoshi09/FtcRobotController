package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@Autonomous
public class BlueAuto extends LinearOpMode {

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor Transfer = null;
    private DcMotor leftCatapult = null;
    private DcMotor rightCatapult = null;
    private Servo upperKicker = null;
    private Servo lowerKicker = null;
    private VoltageSensor voltageSensor = null;
    private Servo Spindle = null;

    public void delay(long delay){
        try {
            // Delay for 2000 milliseconds (2 seconds)
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // Restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    public void startForward(long tnthSeconds){
        frontLeft.setPower(0.25);
        frontRight.setPower(0.25);
        backLeft.setPower(0.25);
        backRight.setPower(0.25);
        delay(tnthSeconds * 100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void Forward(long tnthSeconds){
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        backLeft.setPower(0.5);
        backRight.setPower(0.5);
        delay(tnthSeconds * 100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void Backward(long tnthSeconds){
        frontLeft.setPower(-0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(-0.5);
        backRight.setPower(-0.5);
        delay(tnthSeconds * 100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void strafeLeft(long tnthSeconds){
        frontLeft.setPower(-0.5);
        frontRight.setPower(0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);
        delay(tnthSeconds * 100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void strafeRight(long tnthSeconds){
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(-0.5);
        backRight.setPower(0.5);
        delay(tnthSeconds * 100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void shootBothCatapults(){
        leftCatapult.setPower(1.0);
        rightCatapult.setPower(1.0);
        delay(40);
        rightCatapult.setPower(-0.5);
        leftCatapult.setPower(-0.5);
        delay(200);
        leftCatapult.setPower(0.0);
        rightCatapult.setPower(0.0);
        upperKicker.setPosition(0.6);
        lowerKicker.setPosition(0.58);
        delay(400);
        upperKicker.setPosition(0.25);
        lowerKicker.setPosition(0.32);
        delay(800);
        lowerKicker.setPosition(0.4);
        leftCatapult.setPower(1.0);
        rightCatapult.setPower(1.0);
        delay(40);
        leftCatapult.setPower(-0.5);
        rightCatapult.setPower(-0.5);
        delay(600);
        leftCatapult.setPower(0.0);
        rightCatapult.setPower(0.0);
    }

    public void primeSystems(){
        leftCatapult.setPower(-0.1);
        rightCatapult.setPower(-0.1);
        lowerKicker.setPosition(0.4);
        upperKicker.setPosition(0.25);
        delay(200);
        leftCatapult.setPower(0);
        rightCatapult.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Transfer = hardwareMap.get(DcMotor.class, "Transfer");
        leftCatapult = hardwareMap.get(DcMotor.class, "leftCatapult");
        rightCatapult = hardwareMap.get(DcMotor.class, "rightCatapult");
        upperKicker = hardwareMap.get(Servo.class, "upperKicker");
        lowerKicker = hardwareMap.get(Servo.class, "lowerKicker");
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        Spindle = hardwareMap.get(Servo.class, "Spindle");


        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        Transfer.setDirection(DcMotor.Direction.REVERSE);

        Spindle.setPosition(1.0);

        waitForStart();

        Spindle.setPosition(-1.0);

        while(opModeIsActive()){
            double currentVoltage = voltageSensor.getVoltage();
            int runFor = 0;
            if(currentVoltage < 13.2){
                runFor = 6;
            }else if(currentVoltage >= 13.2){
                runFor = 4;
            }
            startForward(runFor);
            primeSystems();
            delay(400);
            shootBothCatapults();
            break; //End Program
        }
    }
}
