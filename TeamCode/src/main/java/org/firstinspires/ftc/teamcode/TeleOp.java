package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {

    // Declare hardware variables
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor Intake = null;
    private DcMotor Transfer = null;
    private DcMotor leftFlywheel = null;
    private DcMotor rightFlywheel = null;

    private double leftPower = 0.0;
    private double rightPower = 0.0;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Transfer = hardwareMap.get(DcMotor.class, "Transfer");
        leftFlywheel = hardwareMap.get(DcMotor.class, "leftFlywheel");
        rightFlywheel = hardwareMap.get(DcMotor.class, "rightFlywheel");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        Intake.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            leftPower = -gamepad1.left_stick_y/1.2;
            rightPower = -gamepad1.right_stick_y/1.2;

            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);

            if(gamepad1.left_trigger >= 0.5){
                Intake.setPower(1.0);
            }else{
                Intake.setPower(0.0);
            }

            if(gamepad1.right_trigger == 1.0){
                Transfer.setPower(1.0);
            }else if(gamepad1.right_trigger != 1.0) {
                Transfer.setPower(0.0);
            }


            if(gamepad1.right_trigger >= 0.5) {
                rightFlywheel.setPower(1.0);
                leftFlywheel.setPower(-1.0);
            }else{
                rightFlywheel.setPower(0.0);
                leftFlywheel.setPower(0.0);
            }
        }
    }
}
