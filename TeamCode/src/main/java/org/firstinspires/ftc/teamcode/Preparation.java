package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class Preparation extends LinearOpMode {
    private Servo upperKicker = null;
    private Servo lowerKicker = null;
    private Servo Spindle = null;

    @Override
    public void runOpMode() throws InterruptedException {
        upperKicker = hardwareMap.get(Servo.class, "upperKicker");
        lowerKicker = hardwareMap.get(Servo.class, "lowerKicker");
        Spindle = hardwareMap.get(Servo.class, "Spindle");

        waitForStart();

        while(opModeIsActive()) {
            lowerKicker.setPosition(0.4);
            upperKicker.setPosition(0.25);
            Spindle.setPosition(1.0);
        }
    }

}
