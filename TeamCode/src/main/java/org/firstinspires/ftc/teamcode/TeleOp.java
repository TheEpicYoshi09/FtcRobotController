package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor Transfer = null;
    private DcMotor leftCatapult = null;
    private DcMotor rightCatapult = null;
    private Servo upperKicker = null;
    private Servo lowerKicker = null;
    private ElapsedTime timer = new ElapsedTime();
    private CRServo Intake = null;
    private DigitalChannel Counter = null;
    private int Count = 0;
    //private Servo pollenClaw = null;

    // Tune these values for your specific robot mechanism
    private double p = 0.01;
    private double i = 0.0;
    private double d = 0.0001;

    private double integralSum = 0;
    private double lastError = 0;

    private boolean intakeOn = false;
    private boolean transferOn = false;

    public void delay(long delay){
        try {
            // Delay for 2000 milliseconds (2 seconds)
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // Restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    public double pidControl(double reference, double state) {
        double error = reference - state;
        double currentTime = timer.seconds();
        // For strict accuracy, compute delta time (dt); here approximated per loop or constant
        double errorChange = error - lastError;

        integralSum += error * 0.02; // Assuming ~20ms loop interval or use explicit dt
        double derivative = errorChange / 0.02;

        lastError = error;

        return (error * p) + (integralSum * i) + (derivative * d);
    }

    @Override
    public void runOpMode(){

        waitForStart();

            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            Intake = hardwareMap.get(CRServo.class, "Intake");
            Transfer = hardwareMap.get(DcMotor.class, "Transfer");
            leftCatapult = hardwareMap.get(DcMotor.class, "leftCatapult");
            rightCatapult = hardwareMap.get(DcMotor.class, "rightCatapult");
            upperKicker = hardwareMap.get(Servo.class, "upperKicker");
            lowerKicker = hardwareMap.get(Servo.class, "lowerKicker");
            Counter = hardwareMap.get(DigitalChannel.class, "Counter");

            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection(DcMotor.Direction.REVERSE);
            backRight.setDirection(DcMotor.Direction.FORWARD);
            Transfer.setDirection(DcMotor.Direction.REVERSE);
            Intake.setDirection(CRServo.Direction.FORWARD);
            Counter.setMode(DigitalChannel.Mode.INPUT);

            waitForStart();

            while (opModeIsActive()) {
                double max;


                double axial   = -gamepad1.left_stick_y;
                double lateral = -gamepad1.right_stick_x;
                double yaw     =  gamepad1.left_stick_x;

                double frontLeftPower  = axial + lateral + yaw;
                double frontRightPower = axial - lateral - yaw;
                double backLeftPower   = axial - lateral + yaw;
                double backRightPower  = axial + lateral - yaw;

                max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
                max = Math.max(max, Math.abs(backLeftPower));
                max = Math.max(max, Math.abs(backRightPower));

                if (max > 1.0){
                    frontLeftPower  /= max;
                    frontRightPower /= max;
                    backLeftPower   /= max;
                    backRightPower  /= max;
                }

                frontLeft.setPower(frontLeftPower/2);
                frontRight.setPower(frontRightPower/2);
                backLeft.setPower(backLeftPower/2);
                backRight.setPower(backRightPower/2);

                if(!Counter.getState()){
                    Count++;
                }

                if(Count == 4){
                    intakeOn = false;
                }else{
                    intakeOn = true;
                }

                if(gamepad1.right_bumper){
                    Count++;
                    delay(500);
                }
                if(gamepad1.left_bumper){
                    transferOn = !transferOn;
                    delay(500);
                }

                if(intakeOn){
                    Intake.setPower(1.0);
                }else{
                    Intake.setPower(0.0);
                }

                if(transferOn){
                    Transfer.setPower(1.0);
                }else{
                    Transfer.setPower(0.0);
                }

                /*if(gamepad1.x){
                    pollenClaw.setPosition(1.0);
                    delay(100);
                    pollenClaw.setPosition(0.5);
                    delay(100);
                    pollenClaw.setPosition(1.0);
                    delay(100);
                    pollenClaw.setPosition(0.5);
                    delay(100);
                    pollenClaw.setPosition(0.5);
                }*/

                if(gamepad1.left_trigger >= 0.5){
                    lowerKicker.setPosition(0.58);
                    delay(400);
                    lowerKicker.setPosition(0.32);
                    delay(500);
                    lowerKicker.setPosition(0.4);
                    delay(200);
                    //double power = pidControl(435, leftCatapult.getCurrentPosition());
                    rightCatapult.setPower(1.0);
                    delay(50);
                    Count--;
                    rightCatapult.setPower(-0.5);
                    delay(1000);
                    rightCatapult.setPower(0.0);
                }

                if(gamepad1.right_trigger >= 0.5){
                    upperKicker.setPosition(0.6);
                    delay(400);
                    upperKicker.setPosition(0.25);
                    delay(800);
                    //double power = pidControl(435, leftCatapult.getCurrentPosition());
                    leftCatapult.setPower(1.0);
                    delay(50);
                    Count--;
                    leftCatapult.setPower(-0.5);
                    delay(1000);
                    leftCatapult.setPower(0.0);
                }

                telemetry.addData("Count = ",Count);
                telemetry.update();
            }
    }

}
