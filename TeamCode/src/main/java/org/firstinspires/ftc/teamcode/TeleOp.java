package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor Intake = null;
    private DcMotor Transfer = null;
    private DcMotor leftCatapult = null;
    private DcMotor rightCatapult = null;
    private Servo upperKicker = null;
    private Servo lowerKicker = null;
    private ElapsedTime timer = new ElapsedTime();

    // Tune these values for your specific robot mechanism
    private double p = 0.01;
    private double i = 0.0;
    private double d = 0.0001;

    private double integralSum = 0;
    private double lastError = 0;

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
            Intake = hardwareMap.get(DcMotor.class, "Intake");
            Transfer = hardwareMap.get(DcMotor.class, "Transfer");
            leftCatapult = hardwareMap.get(DcMotor.class, "leftCatapult");
            rightCatapult = hardwareMap.get(DcMotor.class, "rightCatapult");
            upperKicker = hardwareMap.get(Servo.class, "upperKicker");
            lowerKicker = hardwareMap.get(Servo.class, "lowerKicker");

            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.FORWARD);

            waitForStart();

            while (opModeIsActive()) {
                double max;


                double axial   = -gamepad1.left_stick_y;
                double lateral =  gamepad1.left_stick_x;
                double yaw     =  gamepad1.right_stick_x;

                double frontLeftPower  = axial + lateral + yaw;
                double frontRightPower = axial - lateral - yaw;
                double backLeftPower   = axial - lateral + yaw;
                double backRightPower  = axial + lateral - yaw;

                max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
                max = Math.max(max, Math.abs(backLeftPower));
                max = Math.max(max, Math.abs(backRightPower));

                if (max > 1.0) {
                    frontLeftPower  /= max;
                    frontRightPower /= max;
                    backLeftPower   /= max;
                    backRightPower  /= max;
                }

                frontLeft.setPower(frontLeftPower);
                frontRight.setPower(frontRightPower);
                backLeft.setPower(backLeftPower);
                backRight.setPower(backRightPower);

                if(gamepad1.a){
                    Intake.setPower(0.8);
                    Transfer.setPower(0.8);
                }else{
                    Intake.setPower(0.0);
                    Transfer.setPower(0.0);
                }

                if(gamepad1.left_trigger >= 0.5){
                    lowerKicker.setPosition(0.0);
                    delay(500);
                    lowerKicker.setPosition(0.5);
                    delay(1500);
                    //double power = pidControl(435, leftCatapult.getCurrentPosition());
                    leftCatapult.setPower(1.0);
                    delay(75);
                    leftCatapult.setPower(-0.5);
                    delay(200);
                    leftCatapult.setPower(0.0);
                }

                if(gamepad1.right_trigger >= 0.5){
                    upperKicker.setPosition(0.0);
                    delay(500);
                    upperKicker.setPosition(0.5);
                    delay(1500);
                    //double power = pidControl(435, leftCatapult.getCurrentPosition());
                    rightCatapult.setPower(1.0);
                    delay(75);
                    rightCatapult.setPower(-0.5);
                    delay(200);
                    rightCatapult.setPower(0.0);
                }
            }
    }

}
